package com.sirekanian.spacetime.ext

import android.content.Context
import com.sirekanian.spacetime.App

fun Context.app(): App =
    applicationContext as App
