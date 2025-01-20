import SwiftUI
import shared
import UIKit
import Foundation

struct ContentView: View {
    let primaryColor: UIColor = SharedRes.colors().colorPrimary.getUIColor()
    let colorOnPrimary: UIColor = SharedRes.colors().colorOnPrimary.getUIColor()

	var body: some View {
        VStack {
            Text(SharedRes.strings().app_name.localized())
                .font(Font(resource: \.pressstart2p_regular, withSize: 24.0))
                .foregroundStyle(Color(colorOnPrimary))
            Image(resource: \.asset_enemy_red_one)
                .frame(width: 100, height: 100)
        }.frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(primaryColor))
            .edgesIgnoringSafeArea(.all)
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

extension Image {
    init(resource: KeyPath<SharedRes.images, shared.ImageResource>) { self.init(uiImage: SharedRes.images()[keyPath: resource].toUIImage()!)
    }
}

extension StringResource {
    func localized() -> String {
        return desc().localized()
    }
}

extension Font {
    init(resource: KeyPath<SharedRes.fonts, FontResource>, withSize: Double = 14.0) {
        self.init(SharedRes.fonts()[keyPath: resource].uiFont(withSize: withSize))
    }
}
