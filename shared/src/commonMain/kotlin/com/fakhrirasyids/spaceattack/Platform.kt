package com.fakhrirasyids.spaceattack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform