//
//  PlayerResult.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

struct PlayerResult {
    var playerName: String
    var playerScore: Int
    
    init(playerName: String = "", playerScore: Int = 0) {
        self.playerName = playerName
        self.playerScore = playerScore
    }
}
