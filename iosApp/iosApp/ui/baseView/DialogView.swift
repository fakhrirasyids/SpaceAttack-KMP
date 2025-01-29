//
//  DialogView.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

protocol DialogView: View {
    var isPresented: Binding<Bool> { get }
    var isVisible: State<Bool> { get }
    var isDisabled: Bool { get }
}

extension DialogView {
    @ViewBuilder
    func DialogContent<Content: View>(@ViewBuilder content: () -> Content) -> some View {
        ZStack {
            if isPresented.wrappedValue {
                Color.black.opacity(0.5)
                    .ignoresSafeArea()
                    .onTapGesture {
                        if !isDisabled {
                            dismissWithAnimation()
                        }
                    }
                
                content()
                    .opacity(isVisible.wrappedValue ? 1.0 : 0.0)
                    .scaleEffect(isVisible.wrappedValue ? 1.0 : 0.95)
                    .transition(.opacity)
                    .onAppear {
                        withAnimation(.easeInOut(duration: 0.1)) {
                            isVisible.wrappedValue = true
                        }
                    }
                    .onDisappear {
                        withAnimation(.easeInOut(duration: 0.1)) {
                            isVisible.wrappedValue = false
                        }
                    }
            }
        }
    }
    
    func dismissWithAnimation() {
        withAnimation(.easeInOut(duration: 0.3)) {
            isVisible.wrappedValue = false
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
            isPresented.wrappedValue = false
        }
    }
}
