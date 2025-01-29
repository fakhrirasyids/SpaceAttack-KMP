import SwiftUI
import shared

@main
struct SpaceAttack: App {
	var body: some Scene {
		WindowGroup {
			ContentView()
                .background(Color(SharedRes.colors().colorPrimary.getUIColor()))
		}
	}
}
