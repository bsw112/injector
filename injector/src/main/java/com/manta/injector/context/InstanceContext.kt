package com.manta.injector.context

import com.manta.injector.Injector
import com.manta.injector.ParametersHolder
import com.manta.injector.Scope

class InstanceContext(
    val injector: Injector,
    val scope: Scope,
    val parameters: ParametersHolder? = null
) {
}