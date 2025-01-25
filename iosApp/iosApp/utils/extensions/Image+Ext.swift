//
//  Image+Ext.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

extension Image {
    init(resource: KeyPath<SharedRes.images, shared.ImageResource>) { self.init(uiImage: SharedRes.images()[keyPath: resource].toUIImage()!)
    }
}
