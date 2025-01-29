//
//  GameScreen.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Combine
import shared

struct GameScreen: View {
    @EnvironmentObject var router: Router
    
    @StateObject var viewModel: GameViewModel
    @State private var lastDragValue: CGFloat = 0
    
    @State private var showPauseDialog: Bool = false
    
    init(playerName: String, gameDifficulty: String) {
        _viewModel = StateObject(wrappedValue: GameViewModel(playerName: playerName, gameDifficulty: gameDifficulty))
    }

    var body: some View {
        GeometryReader { geometry in
            ZStack {
                // Scrolling Background                
                ScrollingStarsBackground(
                    isPaused: ($viewModel.isPaused.wrappedValue || $viewModel.isButtonPaused.wrappedValue || $viewModel.isGameEnded.wrappedValue),
                    scrollSpeed: 1
                )
            
                // Player
                Image(resource: \.asset_player_point)
                    .resizable()
                    .frame(width: viewModel.playerWidth, height: viewModel.playerHeight)
                    .position(
                        x: viewModel.playerPosition + (viewModel.playerWidth / 2),
                        y: geometry.size.height - viewModel.paddingToDrawPlayerX
                    )
                    .shadow(
                        color: Color(SharedRes.colors().colorOnPrimary.getUIColor()),
                        radius: 10,
                        x: 0,
                        y: 0
                    )

                // Bullets
                ForEach(Array(viewModel.bullets.enumerated()), id: \.offset) { index, bullet in
                    Image(resource: \.asset_player_bullet_point)
                        .resizable()
                        .frame(width: viewModel.bulletWidth, height: viewModel.bulletHeight)
                        .position(bullet)
                }


                // Enemies
                ForEach(viewModel.enemies, id: \.self) { enemy in
                    Image(resource: enemy.isRed ? \.asset_enemy_red_one_point : \.asset_enemy_green_one_point)
                        .resizable()
                        .frame(width: viewModel.enemyWidth, height: viewModel.enemyHeight)
                        .position(x: enemy.position.x + (viewModel.enemyWidth / 2), y: enemy.position.y)
                        .shadow(
                            color: enemy.isRed ? Color(SharedRes.colors().primaryRed.getUIColor()) : Color(SharedRes.colors().primaryGreen.getUIColor()),
                            radius: 10,
                            x: 0,
                            y: 0
                        )
                }

                // Enemy Bullets
                ForEach(Array(viewModel.enemyBullets.enumerated()), id: \.offset) { index, bullet in
                    Image(resource: (viewModel.gameDifficulty == GameDifficulty.EASY.name) ? \.asset_enemy_bullet_point_green : \.asset_enemy_bullet_point_red)
                        .resizable()
                        .frame(width: viewModel.bulletWidth, height: viewModel.bulletHeight)
                        .position(bullet)
                }


                // Score and Lives
                VStack {
                    HStack {
                        Text(
                            String(
                                format: SharedRes.strings().game_score.localized(),
                                viewModel.score
                            )
                        ).font(Font(resource: \.pressstart2p_regular, withSize: 14.0))
                            .foregroundStyle(.white)
                        
                        Spacer()
                        
                        Button(action: {
                            viewModel.onButtonPause()
                            showPauseDialog = true
                        }) {
                            Image(resource: \.icon_pause)
                                .resizable()
                                .frame(width: 32, height: 32)
                        }
                        
                        Spacer()
                        
                        Text(
                            String(
                                format: SharedRes.strings().game_lives.localized(),
                                viewModel.lives
                            )
                        ).font(Font(resource: \.pressstart2p_regular, withSize: 14.0))
                            .foregroundStyle(.white)
                    }
                    .padding()
                    .padding(.top, 42)
                    Spacer()
                }
                
                if showPauseDialog {
                    GamePauseDialog(
                        isPresented: $showPauseDialog,
                        isDisabled: true,
                        onResumeSelected: { viewModel.onButtonResume() },
                        onEndGameSelected: { viewModel.stopGame() },
                        onLeaveGameSelected: { self.router.navigateBack() }
                    )
                }
                
                if viewModel.isGameEnded {
                    GameEndedDialog(
                        isPresented: $viewModel.isGameEnded,
                        isDisabled: true,
                        playerResult: viewModel.playerResult,
                        onLeaderboardsSelected: { self.router.navigate(to: .LEADERBOARDS) },
                        onLeaveGameSelected: { self.router.navigateBack() }
                    )
                }
            }
            .onAppear {
                viewModel.screenWidth = geometry.size.width
                viewModel.screenHeight = geometry.size.height
                viewModel.startGameLoops()
            }
            .gesture(
                DragGesture()
                    .onChanged { value in
                        let delta = (value.translation.width - lastDragValue)
                        viewModel.movePlayer(delta: delta)
                        lastDragValue = value.translation.width
                    }
                    .onEnded { _ in
                        lastDragValue = 0
                    }
            )
            .onReceive(NotificationCenter.default.publisher(for: UIApplication.willEnterForegroundNotification)) { _ in
                viewModel.onResume()
            }
            .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
                viewModel.onPause()
            }
        }
    }
}
