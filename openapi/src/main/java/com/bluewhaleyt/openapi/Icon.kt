package com.bluewhaleyt.openapi

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.decode.SvgDecoder
import coil.request.ImageRequest

interface Icon

data class VectorIcon(val imageVector: ImageVector) : Icon
data class PainterIcon(val painter: Painter) : Icon
data class BitmapIcon(val bitmap: ImageBitmap) : Icon
data class AsyncImageModelIcon(val model: AsyncImageModel) : Icon

fun ImageVector.toIcon() = VectorIcon(this)
fun Painter.toIcon() = PainterIcon(this)
fun ImageBitmap.toIcon() = BitmapIcon(this)
fun Bitmap.toIcon() = BitmapIcon(this.asImageBitmap())
fun Drawable.toIcon() = AsyncImageModelIcon(AsyncImageModel.Unspecified(this))

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: Icon?,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
    keepSpaceIfNull: Boolean = false
) {
    if (keepSpaceIfNull) {
        AnimatedContent(targetState = icon) { icon ->
            if (icon != null) {
                Icon(modifier, icon, contentDescription, tint)
            } else {
                Spacer(modifier = modifier.size(24.dp))
            }
        }
    } else {
        Icon(modifier, icon, contentDescription, tint)
    }
}

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: Icon,
    contentDescription: String?,
    tint: Color = LocalContentColor.current
) {
    when (icon) {
        is VectorIcon -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is PainterIcon -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                painter = icon.painter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is BitmapIcon -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                bitmap = icon.bitmap,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is AsyncImageModelIcon -> {
            AsyncImage(
                modifier = modifier.size(24.dp),
                model = icon.model,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(tint)
            )
        }
    }
}