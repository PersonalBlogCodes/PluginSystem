package com.bluewhaleyt.openapi

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest

sealed interface AsyncImageModel {
    val data: Any?
    data class Unspecified internal constructor(
        override val data: Any?
    ) : AsyncImageModel
    data class Svg internal constructor(
        override val data: Any?
    ) : AsyncImageModel
    data class Gif internal constructor(
        override val data: Any?
    ) : AsyncImageModel
}

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    model: AsyncImageModel,
    contentDescription: String?,
    colorFilter: ColorFilter? = null
) {
    val context = LocalContext.current
    with(ImageRequest.Builder(context).data(model.data)) {
        val finalModel = when (model) {
            is AsyncImageModel.Unspecified -> this
            is AsyncImageModel.Svg -> decoderFactory(SvgDecoder.Factory())
            is AsyncImageModel.Gif -> decoderFactory(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoderDecoder.Factory()
                else GifDecoder.Factory()
            )
        }.build()
        coil.compose.AsyncImage(
            modifier = modifier,
            model = finalModel,
            contentDescription = contentDescription,
            colorFilter = colorFilter
        )
    }
}