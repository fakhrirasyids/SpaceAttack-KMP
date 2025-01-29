//
//  ScreenDestination.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

public enum ScreenDestination: Codable, Hashable {
    case LEADERBOARDS
    case GAME(playerName: String, gameDifficulty: String)
}
