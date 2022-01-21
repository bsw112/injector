package com.manta.injector.context

import com.manta.injector.Injector
import com.manta.injector.InjectorApplication
import com.manta.injector.dsl.InjectorAppDeclaration

object GlobalContext : InjectorContext {

    private var _injector : Injector? = null
    private var _injectorApplication : InjectorApplication? = null

    override fun get() = _injector ?: error("KoinApplication has not been started")

    fun startInjector(injectorAppDeclaration: InjectorAppDeclaration): InjectorApplication {
        val injectorApplication = InjectorApplication()
        injectorAppDeclaration(injectorApplication)
        register(injectorApplication)
        return injectorApplication
    }

    fun register(injectorApplication: InjectorApplication){
        _injectorApplication = injectorApplication
        _injector = injectorApplication.injector
    }
}