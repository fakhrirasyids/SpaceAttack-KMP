package com.fakhrirasyids.spaceattack.android

import android.app.Application
import com.fakhrirasyids.spaceattack.android.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceAttack: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpaceAttack)
            modules(viewModelModules)
        }
    }
}