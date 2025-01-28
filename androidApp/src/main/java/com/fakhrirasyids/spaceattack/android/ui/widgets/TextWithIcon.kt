package com.fakhrirasyids.spaceattack.android.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fakhrirasyids.spaceattack.SharedRes
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun TextWithIcon(
    text: String,
    imageResource: ImageResource,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    color: Color,
    fontResource: FontResource = SharedRes.fonts.pressstart2p_regular,
    iconSize: Dp = 24.dp,
    spacing: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically // Align icon and text in the center vertically
    ) {
        // Icon on the left
        Image(
            painter = painterResource(imageResource = imageResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(spacing)) // Add spacing between icon and text
        // Text
        Text(
            text = text,
            fontFamily = fontFamilyResource(fontResource = fontResource)
        )
    }
}
