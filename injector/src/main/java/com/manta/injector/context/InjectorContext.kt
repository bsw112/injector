package com.manta.injector.context

import com.manta.injector.Injector

interface InjectorContext {
    fun get(): Injector
}