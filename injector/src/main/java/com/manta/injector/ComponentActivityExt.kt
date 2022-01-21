package com.manta.injector

import android.app.Activity
import androidx.core.app.ComponentActivity

fun ComponentActivity.activityScope() = LifecycleScopeDelegate<Activity>(this)