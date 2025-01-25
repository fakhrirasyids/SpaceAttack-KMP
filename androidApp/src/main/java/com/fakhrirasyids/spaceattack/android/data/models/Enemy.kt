package com.fakhrirasyids.spaceattack.android.data.models

import androidx.compose.ui.geometry.Offset

data class Enemy(
    var position: Offset,
    val isRed: Boolean,
    var lastShotTime: Long = System.currentTimeMillis()
)
