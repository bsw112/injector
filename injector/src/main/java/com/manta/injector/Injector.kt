package com.manta.injector

class Injector {

    val instanceRegistry = InstanceRegistry(this)
    val scopeRegistry = ScopeRegistry(this)

    fun loadModules(modules: List<Module>){
        instanceRegistry.loadModules(modules)
        scopeRegistry.loadScopes(modules)
        createEagerInstance()
    }

    fun createEagerInstance(){
        instanceRegistry.createAllEagerInstances()
    }

    fun createScope(scopeId: ScopeID, qualifier: Qualifier, source: Any? = null): Scope {
        return scopeRegistry.createScope(scopeId, qualifier, source)
    }

    fun getScopeOrNull(scopeId: ScopeID): Scope? {
        return scopeRegistry.getScopeOrNull(scopeId)
    }




}