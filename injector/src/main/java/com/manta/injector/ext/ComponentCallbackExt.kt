package com.manta.injector.ext

import android.content.ComponentCallbacks
import com.manta.injector.ParametersDefinition
import com.manta.injector.qualifier.Qualifier

inline fun <reified T : Any> ComponentCallbacks.inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null,
) = lazy(mode) { get<T>(qualifier, parameters) }


inline fun <reified T : Any> ComponentCallbacks.get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    return getInjectorScope().get(qualifier, parameters)
}
