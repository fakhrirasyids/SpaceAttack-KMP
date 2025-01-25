import SwiftUI
import shared
import UIKit
import Foundation

struct ContentView: View {
    let primaryColor: UIColor = SharedRes.colors().colorPrimary.getUIColor()
    let colorOnPrimary: UIColor = SharedRes.colors().colorOnPrimary.getUIColor()

	var body: some View {
        VStack {
            GameScreen()
        }.frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(primaryColor))
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
