package com.manta.injector

import com.manta.injector.instance.InstanceFactory
import com.manta.injector.instance.ScopedInstanceFactory

class ScopeDSL (val scopeQualifier: Qualifier, val module : Module){

    inline fun <reified T> scoped(
        qualifier: Qualifier? = null,
        noinline definition: Definition<T>
    ): Pair<Module, InstanceFactory<T>> {
        val def = createDefinition(Kind.Scoped, qualifier, definition, scopeQualifier = scopeQualifier)
        val mapping = indexKey(def.primaryType, qualifier, scopeQualifier)
        val instanceFactory = ScopedInstanceFactory(def)
        module.saveMapping(mapping, instanceFactory)
        return Pair(module, instanceFactory)
    }

}