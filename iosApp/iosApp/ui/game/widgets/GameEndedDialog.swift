//
//  GameEndedDialog.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct GameEndedDialog: DialogView {
    var isPresented: Binding<Bool>
    var isDisabled: Bool
    var isVisible: State<Bool> = State(initialValue: false)
    
    var playerResult: PlayerResult
    
    var onLeaderboardsSelected: () -> Void
    var onLeaveGameSelected: () -> Void

    var body: some View {
        DialogContent {
            VStack(spacing: 32) {
                OctagonBackground(
                    backgroundColor: Color(SharedRes.colors().colorPrimary.getUIColor()),
                    borderColor: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                    borderSize: 2
                ) {
                    VStack {
                        Text(playerResult.playerName)
                            .font(Font(resource: \.pressstart2p_regular))
                            .foregroundColor(.white)
                            .padding(.vertical, 16)
                        
                        Image(resource: \.asset_player_point)
                            .resizable()
                            .frame(width: 40, height: 20)
                            .shadow(
                                color: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                                radius: 10,
                                x: 0,
                                y: 0
                            )

                        
                        Text(String(
                            format: SharedRes.strings().game_score.localized(),
                            playerResult.playerScore
                        ))
                            .font(Font(resource: \.pressstart2p_regular))
                            .foregroundColor(.white)
                            .padding(.vertical, 16)
                    }.frame(maxWidth: .infinity)
                }.frame(maxWidth: .infinity)

                OctagonBackground(
                    backgroundColor: Color(SharedRes.colors().colorPrimary.getUIColor()),
                    borderColor: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                    borderSize: 2
                ) {
                    VStack {
                        Button(action: {
                            dismissWithAnimation()
                            onLeaderboardsSelected()
                        }) {
                            Text(resource: \.home_leaderboards)
                                .font(Font(resource: \.pressstart2p_regular))
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity)
                                .padding()
                                .overlay(
                                    OctagonShape(edgeInsetFraction: 0.01)
                                        .stroke(Color(SharedRes.colors().colorOnPrimary.getUIColor()), lineWidth: 2)
                                )
                        }

                        Button(action: {
                            dismissWithAnimation()
                            onLeaveGameSelected()
                        }) {
                            Text(resource: \.game_leave)
                                .font(Font(resource: \.pressstart2p_regular))
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity)
                                .padding()
                                .overlay(
                                    OctagonShape(edgeInsetFraction: 0.01)
                                        .stroke(Color(SharedRes.colors().colorOnPrimary.getUIColor()), lineWidth: 2)
                                )
                        }
                    }
                }
            }
            .padding(32)
        }
    }
}
