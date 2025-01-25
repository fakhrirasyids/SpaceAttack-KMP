//
//  ScrollingStarsBackground.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 25/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ScrollingStarsBackground: View {
    @State private var offsetY: CGFloat = 0
    let isPaused: Bool
    let scrollSpeed: CGFloat

    var body: some View {
        GeometryReader { geometry in
            ZStack {
                Canvas { context, size in
                    guard let uiImage = SharedRes.images()[keyPath: \.asset_stars_bg].toUIImage() else { return }
                    let imageSize = uiImage.size
                    let scaleX = size.width / imageSize.width
                    let scaleY = size.height / imageSize.height
                    let scale = max(scaleX, scaleY)

                    let scaledImageSize = CGSize(
                        width: imageSize.width * scale,
                        height: imageSize.height * scale
                    )

                    let firstImagePosition = CGPoint(
                        x: 0,
                        y: offsetY.truncatingRemainder(dividingBy: size.height)
                    )
                    context.draw(
                        Image(uiImage: uiImage),
                        in: CGRect(origin: firstImagePosition, size: scaledImageSize)
                    )

                    let secondImagePosition = CGPoint(
                        x: 0,
                        y: firstImagePosition.y - size.height
                    )
                    context.draw(
                        Image(uiImage: uiImage),
                        in: CGRect(origin: secondImagePosition, size: scaledImageSize)
                    )
                }
            }
            .onAppear {
                startScrolling()
            }
        }
    }
    
    private func startScrolling() {
        Timer.scheduledTimer(withTimeInterval: 0.01, repeats: true) { timer in
            if !isPaused {
                offsetY += scrollSpeed
            }
        }
    }
}
