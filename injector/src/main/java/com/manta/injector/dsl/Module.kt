package com.manta.injector.dsl

import com.manta.injector.Module


typealias ModuleDeclaration = Module.() -> Unit

fun module(moduleDeclaration: ModuleDeclaration) : Module {
    val module = Module()
    moduleDeclaration(module)
    return module
}