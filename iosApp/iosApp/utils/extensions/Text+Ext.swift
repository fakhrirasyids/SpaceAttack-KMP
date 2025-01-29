//
//  Text+Ext.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 29/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

extension Text {
    init(resource: KeyPath<SharedRes.strings, shared.StringResource>) {
        self.init(SharedRes.strings()[keyPath: resource].localized())
    }
}
