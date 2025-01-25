//
//  Enemy.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct Enemy: Equatable, Hashable {
    var position: CGPoint
    let isRed: Bool
    var lastShotTime: TimeInterval = Date().timeIntervalSince1970
    
    init(position: CGPoint, isRed: Bool) {
        self.position = position
        self.isRed = isRed
    }
}
