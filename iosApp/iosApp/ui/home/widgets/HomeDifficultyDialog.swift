//
//  HomeDifficultyDialog.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeGameDifficultyDialog: DialogView {
    var isPresented: Binding<Bool>
    var isDisabled: Bool
    var isVisible: State<Bool> = State(initialValue: false)
    
    var onDifficultySelected: (String, String) -> Void

    @State private var playerName: String = PlayerHelper().generatePlayerName()
    @State private var showSnackbar: Bool = false
    @State private var snackbarMessage: String = ""

    var body: some View {
        DialogContent {
            VStack(spacing: 32) {
                OctagonBackground(
                    backgroundColor: Color(SharedRes.colors().colorPrimary.getUIColor()),
                    borderColor: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                    borderSize: 2
                ) {
                    VStack {
                        Text(resource: \.home_player_name)
                            .font(Font(resource: \.pressstart2p_regular))
                            .foregroundColor(.white)
                            .padding(.vertical, 16)

                        TextField(_: SharedRes.strings().home_player_name_placeholder.localized(), text: $playerName)
                            .padding()
                            .foregroundColor(.white)
                            .font(Font(resource: \.pressstart2p_regular))
                            .overlay(
                                OctagonShape(edgeInsetFraction: 0.01)
                                    .stroke(Color(SharedRes.colors().colorOnPrimary.getUIColor()), lineWidth: 2)
                            )
                    }
                }

                OctagonBackground(
                    backgroundColor: Color(SharedRes.colors().colorPrimary.getUIColor()),
                    borderColor: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                    borderSize: 2
                ) {
                    VStack {
                        Text(resource: \.home_game_difficulty_select)
                            .font(Font(resource: \.pressstart2p_regular))
                            .foregroundColor(.white)
                            .padding(.vertical, 16)

                        Button(action: {
                            validateAndSelect(difficulty: GameDifficulty.EASY.name)
                        }) {
                            Text(resource: \.home_game_difficulty_easy)
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
                            validateAndSelect(difficulty: GameDifficulty.HARD.name)
                        }) {
                            Text(resource: \.home_game_difficulty_hard)
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
            .alert(isPresented: $showSnackbar) {
                Alert(title: Text("Error"), message: Text(snackbarMessage), dismissButton: .default(Text("OK")))
            }
        }
    }

    private func validateAndSelect(difficulty: String) {
        guard !playerName.isEmpty else {
            snackbarMessage = SharedRes.strings().home_player_name_invalid.localized()
            showSnackbar = true
            return
        }
        dismissWithAnimation()
        onDifficultySelected(playerName, difficulty)
    }
}
