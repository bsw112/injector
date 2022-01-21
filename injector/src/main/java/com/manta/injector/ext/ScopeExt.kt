package com.manta.injector.ext

import android.content.ComponentCallbacks
import com.manta.injector.context.GlobalContext
import com.manta.injector.Scope
import com.manta.injector.component.AndroidScopeComponent

fun ComponentCallbacks.getInjectorScope(): Scope {
    return when (this) {
        is AndroidScopeComponent -> scope
        else -> GlobalContext.get().scopeRegistry.rootScope
    }

}