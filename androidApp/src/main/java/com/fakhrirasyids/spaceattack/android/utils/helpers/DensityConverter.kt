package com.fakhrirasyids.spaceattack.android.utils.helpers

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object DensityConverter {
    val Float.asDp: Dp
        get() = this.dp

    val Float.asPx: Int
        get() = (this.toInt() * Resources.getSystem().displayMetrics.density).toInt()
}