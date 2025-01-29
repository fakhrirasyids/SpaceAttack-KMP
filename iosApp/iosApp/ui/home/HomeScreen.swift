//
//  HomeScreen.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeScreen: View {
    @EnvironmentObject var router: Router
    
    @State private var showDifficultyDialog = false
    @State private var startAnimation = false
    @State private var endAnimation = false

    var body: some View {
        ZStack {
            ScrollingStarsBackground(isPaused: false,scrollSpeed: 0.5)

            VStack {
                Image(resource: \.logo_spaceattack)
                    .resizable()
                    .scaledToFit()
                    .frame(width: startAnimation ? 350 : 0)
                    .padding(.vertical, 120)
                    .offset(y: startAnimation ? 0 : -UIScreen.main.bounds.height)
                    .opacity(startAnimation ? 1 : 0)
                    .animation(.easeInOut(duration: 1.5), value: startAnimation)

                Spacer()

                VStack {
                    Text(resource: \.home_start_game)
                        .font(Font(resource: \.pressstart2p_regular, withSize: 14.0))
                        .foregroundColor(.white)
                        .shadow(
                            color: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                            radius: 10,
                            x: 0,
                            y: 0
                        )
                        .padding()
                        .onTapGesture {
                            showDifficultyDialog = true
                        }

                    Text(resource: \.home_leaderboards)
                        .font(Font(resource: \.pressstart2p_regular, withSize: 14.0))
                        .foregroundColor(.white)
                        .shadow(
                            color: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                            radius: 10,
                            x: 0,
                            y: 0
                        )
                        .padding()
                        .onTapGesture {
                            
                        }

                    Text(resource: \.home_settings)
                        .font(Font(resource: \.pressstart2p_regular, withSize: 14.0))
                        .foregroundColor(.white)
                        .shadow(
                            color: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                            radius: 10,
                            x: 0,
                            y: 0
                        )
                        .padding()
                        .onTapGesture {
                            
                        }
                    
                    Spacer()

                    Text(resource: \.home_instructions)
                        .font(Font(resource: \.pressstart2p_regular, withSize: 8.0))
                        .foregroundColor(.white.opacity(0.8))
                        .shadow(
                            color: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                            radius: 10,
                            x: 0,
                            y: 0
                        )
                        .multilineTextAlignment(.center)
                        .padding(24)
                }
                .opacity(endAnimation ? 1 : 0)
                .animation(.easeIn(duration: 0.3), value: endAnimation)
            }
            .onAppear {
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
                    startAnimation = true
                }

                DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                    endAnimation = true
                }
            }

            if showDifficultyDialog {
                HomeGameDifficultyDialog(
                    isPresented: $showDifficultyDialog,
                    isDisabled: false,
                    onDifficultySelected: { playerName, gameDifficulty in
                        self.router.navigate(to: .GAME(playerName: playerName, gameDifficulty: gameDifficulty))
                    }
                )
            }
        }.edgesIgnoringSafeArea(.all)
    }
}
