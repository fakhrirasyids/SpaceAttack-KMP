//
//  Font+Ext.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

extension Font {
    init(resource: KeyPath<SharedRes.fonts, FontResource>, withSize: Double = 14.0) {
        self.init(SharedRes.fonts()[keyPath: resource].uiFont(withSize: withSize))
    }
}
