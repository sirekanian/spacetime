@file:Suppress("DEPRECATION")

package com.sirekanian.spacetime.ui

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

class BlurHelper(context: Context, private val bitmap: Bitmap) {

    private val script = RenderScript.create(context)
    private val input = Allocation.createFromBitmap(script, bitmap)
    private val output = Allocation.createTyped(script, input.type)
    private val blur = ScriptIntrinsicBlur.create(script, Element.U8_4(script))

    fun blur(radius: Float) {
        blur.setRadius(radius)
        blur.setInput(input)
        blur.forEach(output)
        output.copyTo(bitmap)
    }

    fun destroy() {
        blur.destroy()
        output.destroy()
        input.destroy()
        script.destroy()
    }

}