//
//  GameViewModel.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Combine

class GameViewModel: ObservableObject {
    @Published var isPaused: Bool = false
    @Published var playerPosition: CGFloat = 0
    @Published var bullets: [CGPoint] = []
    @Published var enemies: [Enemy] = []
    @Published var enemyBullets: [CGPoint] = []
    @Published var score: Int = 0
    @Published var lives: Int = 3
    @Published var screenWidth: CGFloat = 0
    @Published var screenHeight: CGFloat = 0

    let playerWidth: CGFloat = 40
    let playerHeight: CGFloat = 20
    let enemyWidth: CGFloat = 40
    let enemyHeight: CGFloat = 28
    let bulletWidth: CGFloat = 2
    let bulletHeight: CGFloat = 15

    let paddingToDrawPlayerX: CGFloat = 40
    private var leftPaddingCollisionTolerance: CGFloat = 20

    private let playerBulletSpeed: TimeInterval = 0.3
    private let enemyBulletSpeed: TimeInterval = 1.0
    private let enemySpawnSpeed: TimeInterval = 1.0
    private var gameStarted: Bool = false

    private var cancellables = Set<AnyCancellable>()

    init() {
        startGameLoops()
    }

    func movePlayer(delta: CGFloat) {
        playerPosition = (playerPosition + delta)
            .clamped(to: 0...(screenWidth - playerWidth))
    }

    func startGameLoops() {
        guard !gameStarted, screenWidth > 0, screenHeight > 0 else { return }
        gameStarted = true

        // Player shooting logic
        Timer.publish(every: playerBulletSpeed, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self, !self.isPaused else { return }
                self.bullets.append(
                    CGPoint(
                        x: (self.playerPosition + (self.playerWidth / 2)),
                        y: self.screenHeight - self.paddingToDrawPlayerX
                    )
                )
            }
            .store(in: &cancellables)

        // Enemy spawning logic
        Timer.publish(every: enemySpawnSpeed, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self, !self.isPaused else { return }
                self.enemies.append(
                    Enemy(
                        position: CGPoint(
                            x: CGFloat.random(in: 0...(self.screenWidth - self.enemyWidth)),
                            y: 0
                        ),
                        isRed: Bool.random()
                    )
                )
            }
            .store(in: &cancellables)

        // Game update loop
        Timer.publish(every: 0.01, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self, !self.isPaused else { return }
                self.updateBullets()
                self.updateEnemies()
                self.updateEnemyBullets()
                self.updateEnemyShooting()
                self.checkCollisions()
            }
            .store(in: &cancellables)
    }

    private func updateBullets() {
        bullets = bullets.map { CGPoint(x: $0.x, y: $0.y - 10) }.filter { $0.y >= 0 }
    }

    private func updateEnemies() {
        enemies = enemies.map {
            var updatedEnemy = $0
            updatedEnemy.position.y += 2
            return updatedEnemy
        }.filter { $0.position.y < screenHeight }
    }

    private func updateEnemyBullets() {
        enemyBullets = enemyBullets.map { CGPoint(x: $0.x, y: $0.y + 5) }.filter { $0.y <= screenHeight }
    }
    
    private func updateEnemyShooting() {
        let currentTime = Date().timeIntervalSince1970 

        enemies = enemies.map { enemy in
            var updatedEnemy = enemy
            if currentTime - updatedEnemy.lastShotTime >= enemyBulletSpeed {
                enemyBullets.append(
                    CGPoint(
                        x: updatedEnemy.position.x + (enemyWidth / 2),
                        y: updatedEnemy.position.y + enemyHeight
                    )
                )
                updatedEnemy.lastShotTime = currentTime
            }
            return updatedEnemy
        }
    }

    private func checkCollisions() {
        // Check player bullet collisions with enemies
        bullets = bullets.filter { bullet in
            !enemies.contains { enemy in
                let enemyRect = CGRect(x: enemy.position.x, y: enemy.position.y, width: enemyWidth, height: enemyHeight)
                let hit = enemyRect.contains(bullet)
                if hit {
                    score += 1
                    enemies.removeAll { $0 == enemy }
                }
                return hit
            }
        }

        // Check enemy bullets hitting player
        enemyBullets = enemyBullets.filter { bullet in
            let playerRect = CGRect(
                x: playerPosition,
                y: screenHeight - paddingToDrawPlayerX,
                width: playerWidth,
                height: playerHeight
            )
            let hit = playerRect.contains(bullet)
            if hit { lives -= 1 }
            return !hit
        }

        // Check enemies colliding with player
        enemies = enemies.filter { enemy in
            let playerRect = CGRect(
                x: playerPosition,
                y: screenHeight - paddingToDrawPlayerX,
                width: playerWidth,
                height: playerHeight
            )
            let enemyRect = CGRect(x: enemy.position.x, y: enemy.position.y, width: enemyWidth, height: enemyHeight)
            let hit = playerRect.intersects(enemyRect)
            if hit { lives -= 1 }
            return !hit
        }
    }
    
    public func onPause() {
        self.isPaused = true
    }
    
    public func onResume() {
        self.isPaused = false
    }
}
