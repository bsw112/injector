package com.manta.injector

class InjectorApplication {

    val injector = Injector()

    fun modules(vararg modules : Module) : InjectorApplication {
        return modules(modules.toList())
    }

    fun modules(modules : List<Module>) : InjectorApplication {
        loadModules(modules)
        return this
    }

    private fun loadModules(modules: List<Module>){
        injector.loadModules(modules)
    }






}