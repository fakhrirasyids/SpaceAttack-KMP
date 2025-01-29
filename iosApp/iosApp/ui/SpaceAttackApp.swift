import SwiftUI
import shared
import UIKit
import Foundation

struct ContentView: View {
    @ObservedObject var router = Router()
    
	var body: some View {
        NavigationStack(path: $router.navPath) {
            HomeScreen()
                .navigationDestination(for: ScreenDestination.self) { destination in
                    switch destination {
                    case .GAME(let playerName, let gameDifficulty):
                        GameScreen(playerName: playerName, gameDifficulty: gameDifficulty)
                            .navigationBarHidden(true)
                            .background(Color(SharedRes.colors().colorPrimary.getUIColor())
                                .ignoresSafeArea())
                            .ignoresSafeArea()
                    case .LEADERBOARDS:
                        LeaderboardsScreen()
                            .navigationBarHidden(true)
                            .background(Color(SharedRes.colors().colorPrimary.getUIColor())
                                .ignoresSafeArea())
                            .ignoresSafeArea()
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(Color(SharedRes.colors().colorPrimary.getUIColor())
                    .ignoresSafeArea())
        }
        .environmentObject(router)
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
