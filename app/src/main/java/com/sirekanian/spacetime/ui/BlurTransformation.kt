package com.sirekanian.spacetime.ui

import android.content.Context
import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation

class BlurTransformation(
    private val context: Context,
    private val radius: Float,
    url: String,
) : Transformation {

    override val cacheKey: String = "url=$url|radius=$radius"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        if (radius > 0) {
            doTransformation(input)
        }
        return input
    }

    private fun doTransformation(input: Bitmap) {
        var helper: BlurHelper? = null
        try {
            helper = BlurHelper(context, input)
            helper.blur(radius)
        } finally {
            helper?.destroy()
        }
    }

}