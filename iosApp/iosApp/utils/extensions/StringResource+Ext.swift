//
//  StringResource+Ext.swift
//  iosApp
//
//  Created by Fakhri Rasyid Saputro on 23/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared

extension StringResource {
    func localized() -> String {
        return desc().localized()
    }
}
