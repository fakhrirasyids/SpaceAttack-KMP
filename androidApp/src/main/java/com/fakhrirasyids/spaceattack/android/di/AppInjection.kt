package com.fakhrirasyids.spaceattack.android.di

import com.fakhrirasyids.spaceattack.android.ui.game.GameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { GameViewModel() }
}