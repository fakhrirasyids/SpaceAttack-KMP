//
//  Comparable+Ext.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

extension Comparable {
    func clamped(to limits: ClosedRange<Self>) -> Self {
        return min(max(self, limits.lowerBound), limits.upperBound)
    }
}
