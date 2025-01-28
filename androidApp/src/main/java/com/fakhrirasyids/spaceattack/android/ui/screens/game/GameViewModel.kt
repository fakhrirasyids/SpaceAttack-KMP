package com.fakhrirasyids.spaceattack.android.ui.screens.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhrirasyids.spaceattack.android.data.models.Enemy
import com.fakhrirasyids.spaceattack.android.data.models.PlayerResult
import com.fakhrirasyids.spaceattack.android.utils.enums.GameDifficulty
import com.fakhrirasyids.spaceattack.android.utils.helpers.DensityConverter.asPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    val playerName: String,
    val difficulty: String,
) : ViewModel() {

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _isButtonPaused = MutableStateFlow(false)
    val isButtonPaused: StateFlow<Boolean> = _isButtonPaused.asStateFlow()

    private val _isGameEnded = MutableStateFlow(false)
    val isGameEnded: StateFlow<Boolean> = _isGameEnded.asStateFlow()

    private val _playerResult = MutableStateFlow(PlayerResult())
    val playerResult: StateFlow<PlayerResult> = _playerResult.asStateFlow()

    private val _playerPosition = MutableStateFlow(0)
    val playerPosition: StateFlow<Int> = _playerPosition.asStateFlow()

    private val _bullets = MutableStateFlow<List<Offset>>(emptyList())
    val bullets: StateFlow<List<Offset>> = _bullets.asStateFlow()

    private val _enemies = MutableStateFlow<List<Enemy>>(emptyList())
    val enemies: StateFlow<List<Enemy>> = _enemies.asStateFlow()

    private val _enemyBullets = MutableStateFlow<List<Offset>>(emptyList())
    val enemyBullets: StateFlow<List<Offset>> = _enemyBullets.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _lives = MutableStateFlow(3)
    val lives: StateFlow<Int> = _lives.asStateFlow()

    val playerWidthDp = 40f
    val playerHeightDp = 20f

    val enemyWidthDp = 40f
    val enemyHeightDp = 28f

    val bulletSizeDp = 15f

    private var leftPaddingCollisionTolerance = 20

    private val paddingToDrawPlayerX = 200

    var screenHeight = MutableStateFlow(0)
    var screenWidth = MutableStateFlow(0)

    private val playerBulletSpeed = 300L
    private var enemyBulletSpeed = 1000L
    private var enemySpawnSpeed = 1000L

    private var gameStarted = false

    init {
        if (difficulty == GameDifficulty.Hard.name) {
            enemyBulletSpeed = (enemyBulletSpeed.toFloat() / 1.5).toLong()
            enemySpawnSpeed /= 2
        }

        startGameLoops()
    }

    fun movePlayer(delta: Int, screenWidth: Int, playerWidth: Int) {
        _playerPosition.value = (_playerPosition.value + delta)
            .coerceIn(0, screenWidth - playerWidth)
    }

    fun startGameLoops() {
        if (gameStarted || screenWidth.value == 0 || screenHeight.value == 0) return
        gameStarted = true

        // Player shooting logic
        viewModelScope.launch {
            while (true) {
                if (!(_isPaused.value || _isButtonPaused.value || _isGameEnded.value)) {
                    _bullets.update { currentBullets ->
                        currentBullets +
                                Offset(
                                    (_playerPosition.value + (playerWidthDp.asPx / 2)) - (bulletSizeDp.asPx.toFloat() / 2),
                                    (screenHeight.value - paddingToDrawPlayerX).toFloat()
                                )
                    }
                }
                delay(playerBulletSpeed)
            }
        }

        // Enemy spawning logic
        viewModelScope.launch {
            while (true) {
                if (!(_isPaused.value || _isButtonPaused.value || _isGameEnded.value)) {
                    _enemies.update { currentEnemies ->
                        currentEnemies +
                                Enemy(
                                    position = Offset(
                                        x = (0..screenWidth.value - enemyWidthDp.asPx).random()
                                            .toFloat(),
                                        y = 0f
                                    ),
                                    isRed = difficulty == GameDifficulty.Hard.name
                                )
                    }
                }
                delay(enemySpawnSpeed)
            }
        }

        // Bullet and enemy movement logic
        viewModelScope.launch {
            while (true) {
                if (!(_isPaused.value || _isButtonPaused.value || _isGameEnded.value)) {
                    updateBullets()
                    updateEnemyBullets()
                    updateEnemies()
                    updateEnemyShooting()
                    checkPlayerBulletCollisions()
                    checkEnemyBulletCollisions()
                    checkPlayerWithEnemyCollisions()
                }
                delay(10L)
            }
        }
    }

    private fun updateBullets() {
        _bullets.update { currentBullets ->
            currentBullets.map { it.copy(y = it.y - 20) }
                .filter { it.y >= 0 }
        }
    }

    private fun updateEnemyBullets() {
        _enemyBullets.update { currentBullets ->
            currentBullets.map { it.copy(y = it.y + 10) }
                .filter { it.y <= screenHeight.value }
        }
    }

    private fun updateEnemyShooting() {
        val currentTime = System.currentTimeMillis()
        _enemies.value.forEach { enemy ->
            if (currentTime - enemy.lastShotTime >= enemyBulletSpeed) {
                _enemyBullets.update { currentBullets ->
                    currentBullets + Offset(
                        (enemy.position.x + (enemyWidthDp.asPx / 2)) - (bulletSizeDp.asPx / 2),
                        enemy.position.y + enemyWidthDp.asPx
                    )
                }
                enemy.lastShotTime = currentTime
            }
        }
    }

    private fun updateEnemies() {
        _enemies.update { currentEnemies ->
            currentEnemies.map { it.copy(position = it.position.copy(y = it.position.y + 5)) }
                .filter { it.position.y < screenHeight.value }
        }
    }

    private fun checkPlayerBulletCollisions() {
        _bullets.update { currentBullets ->
            currentBullets.filterNot { bullet ->
                _enemies.value.any { enemy ->
                    val enemyRect = Rect(
                        enemy.position.x - leftPaddingCollisionTolerance,
                        enemy.position.y,
                        enemy.position.x + enemyWidthDp.asPx,
                        enemy.position.y + enemyHeightDp.asPx
                    )
                    val hit = enemyRect.contains(bullet)
                    if (hit) {
                        increaseScore()
                        _enemies.update { it - enemy }
                    }
                    hit
                }
            }
        }
    }


    private fun checkEnemyBulletCollisions() {
        _enemyBullets.update { currentBullets ->
            currentBullets.filterNot { bullet ->
                val playerRect = Rect(
                    _playerPosition.value.toFloat() - leftPaddingCollisionTolerance,
                    screenHeight.value.toFloat() - paddingToDrawPlayerX,
                    _playerPosition.value + playerWidthDp.asPx.toFloat(),
                    screenHeight.value - paddingToDrawPlayerX + playerHeightDp.asPx.toFloat()
                )
                val hit = playerRect.contains(bullet)
                if (hit) {
                    decreaseLife()
                }
                hit
            }
        }
    }

    private fun checkPlayerWithEnemyCollisions() {
        _enemies.update { currentEnemies ->
            currentEnemies.filterNot { enemy ->
                val playerRect = Rect(
                    _playerPosition.value.toFloat(),
                    screenHeight.value.toFloat() - paddingToDrawPlayerX,
                    _playerPosition.value + playerWidthDp.asPx.toFloat(),
                    screenHeight.value - paddingToDrawPlayerX + playerHeightDp.asPx.toFloat()
                )
                val enemyRect = Rect(
                    enemy.position.x,
                    enemy.position.y,
                    enemy.position.x + enemyWidthDp.asPx.toFloat(),
                    enemy.position.y + enemyHeightDp.asPx.toFloat()
                )
                val hit = playerRect.overlaps(enemyRect)
                if (hit) {
                    decreaseLife()
                }
                hit
            }
        }
    }

    fun setButtonPause() {
        _isButtonPaused.value = true
    }

    fun setButtonResume() {
        _isButtonPaused.value = false
    }

    fun onPause() {
        if (!_isButtonPaused.value) {
            _isPaused.value = true
        }
    }

    fun onResume() {
        if (!_isButtonPaused.value) {
            _isPaused.value = false
        }
    }

    private fun increaseScore() {
        if (_score.value <= 999999) {
            _score.value += 1
        }
    }

    private fun decreaseLife() {
        if (_lives.value == 1) {
            stopGame()
        } else {
            _lives.value -= 1
        }
    }

    fun stopGame() {
        _playerResult.value = PlayerResult(playerName, _score.value)
        _isGameEnded.value = true
    }
}

