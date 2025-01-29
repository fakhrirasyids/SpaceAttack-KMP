//
//  GamePauseDialog.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct GamePauseDialog: DialogView {
    var isPresented: Binding<Bool>
    var isDisabled: Bool
    var isVisible: State<Bool> = State(initialValue: false)
    
    var onResumeSelected: () -> Void
    var onEndGameSelected: () -> Void
    var onLeaveGameSelected: () -> Void

    var body: some View {
        DialogContent {
            OctagonBackground(
                backgroundColor: Color(SharedRes.colors().colorPrimary.getUIColor()),
                borderColor: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                borderSize: 2
            ) {
                VStack {
                    Text(resource: \.game_paused)
                        .font(Font(resource: \.pressstart2p_regular))
                        .foregroundColor(.white)
                        .padding(.vertical, 16)

                    Button(action: {
                        dismissWithAnimation()
                        onResumeSelected()
                    }) {
                        Text(resource: \.game_resume)
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
                        onEndGameSelected()
                    }) {
                        Text(resource: \.game_end)
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
            }.padding(32)
        }
    }
}
