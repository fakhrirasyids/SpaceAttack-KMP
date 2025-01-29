//
//  OctagonHelper.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct OctagonShape: Shape {
    var edgeInsetFraction: Double = 0.05 // fill with max value of 0.1
    
    func path(in rect: CGRect) -> Path {
        let edgeInset = rect.width * self.edgeInsetFraction
        var path = Path()
        path.move(to: CGPoint(x: edgeInset, y: 0))
        path.addLine(to: CGPoint(x: rect.width - edgeInset, y: 0))
        path.addLine(to: CGPoint(x: rect.width, y: edgeInset))
        path.addLine(to: CGPoint(x: rect.width, y: rect.height - edgeInset))
        path.addLine(to: CGPoint(x: rect.width - edgeInset, y: rect.height))
        path.addLine(to: CGPoint(x: edgeInset, y: rect.height))
        path.addLine(to: CGPoint(x: 0, y: rect.height - edgeInset))
        path.addLine(to: CGPoint(x: 0, y: edgeInset))
        path.closeSubpath()
        return path
    }
}

struct OctagonBackground<Content: View>: View {
    var backgroundColor: Color
    var borderColor: Color
    var borderSize: CGFloat
    var content: Content

    init(backgroundColor: Color, borderColor: Color, borderSize: CGFloat = 4, @ViewBuilder content: () -> Content) {
        self.backgroundColor = backgroundColor
        self.borderColor = borderColor
        self.borderSize = borderSize
        self.content = content()
    }

    var body: some View {
        content
            .padding()
            .background(
                OctagonShape()
                    .fill(backgroundColor)
            )
            .overlay(
                OctagonShape()
                    .stroke(borderColor, lineWidth: borderSize)
            )
    }
}
