//
//  GameDifficulty.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared

enum GameDifficulty {
    case EASY
    case HARD
    
    public var name: String {
        switch self {
        case .EASY:
            return SharedRes.strings().home_game_difficulty_easy.localized()
        case .HARD:
            return SharedRes.strings().home_game_difficulty_hard.localized()
        }
    }
}
