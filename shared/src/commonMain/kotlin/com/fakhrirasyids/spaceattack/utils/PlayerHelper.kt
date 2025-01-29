package com.fakhrirasyids.spaceattack.utils

class PlayerHelper {
    fun generatePlayerName(): String {
        val randomNumber = (100000..999999).random()
        return "Player@$randomNumber"
    }
}