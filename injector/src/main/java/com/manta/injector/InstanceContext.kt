package com.manta.injector

class InstanceContext(
    val injector: Injector,
    val scope: Scope,
    val parameters: ParametersHolder? = null
) {
}