package com.manta.injector

import com.manta.injector.ScopeRegistry.Companion.rootScopeQualifier
import com.manta.injector.instance.FactoryInstanceFactory
import com.manta.injector.instance.InstanceFactory
import com.manta.injector.instance.SingleInstanceFactory

// 주입할 인스턴스들을 구성하는 역할
class Module {
    var eagerInstance = hashSetOf<SingleInstanceFactory<*>>()
        internal set

    val mappings = hashMapOf<IndexKey, InstanceFactory<*>>()

    val scopes = hashSetOf<Qualifier>()

    fun scope(qualifier: Qualifier, scopeSet: ScopeDSL.() -> Unit) {
        ScopeDSL(qualifier, this).apply(scopeSet)
        scopes.add(qualifier)
    }

    inline fun <reified T> scope(scopeSet : ScopeDSL.() -> Unit){
        val qualifier = TypeQualifier(T::class)
        ScopeDSL(qualifier, this).apply(scopeSet)
        scopes.add(qualifier)
    }


    inline fun <reified T> single(noinline definition: Definition<T>) : Pair<Module, InstanceFactory<T>> {
       val def = createDefinition(Kind.Singleton, null, definition,  scopeQualifier = rootScopeQualifier)
        val mapping = indexKey(def.primaryType, null, rootScopeQualifier)
        val instanceFactory = SingleInstanceFactory(def)
        saveMapping(mapping, instanceFactory)
        eagerInstance.add(instanceFactory)
        return Pair(this, instanceFactory)
    }

    inline fun <reified T> factory(
        qualifier: Qualifier? = null,
        noinline definition: Definition<T>
    ): Pair<Module, InstanceFactory<T>> {
        return factory(qualifier, definition, rootScopeQualifier)
    }

    @PublishedApi
    internal inline fun <reified T> factory(
        qualifier: Qualifier? = null,
        noinline definition: Definition<T>,
        scopeQualifier: Qualifier
    ): Pair<Module, InstanceFactory<T>> {
        val def = createDefinition(Kind.Factory, qualifier, definition, scopeQualifier = scopeQualifier)
        val mapping = indexKey(def.primaryType, qualifier, scopeQualifier)
        val instanceFactory = FactoryInstanceFactory(def)
        saveMapping(mapping, instanceFactory)
        return Pair(this, instanceFactory)
    }


    fun saveMapping(mapping : IndexKey, factory : InstanceFactory<*>){
        mappings[mapping] = factory
    }
}