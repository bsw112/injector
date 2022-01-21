package com.manta.injector

import com.manta.injector.InjectorPlatformTools.safeHashMap

class ScopeRegistry(val _injector: Injector) {

    val rootScope = Scope(rootScopeQualifier, ROOT_SCOPE_ID, true, _injector)

    private val _scopeDefinitions = HashSet<Qualifier>()
    val scopeDefinition: Set<Qualifier>
        get() = _scopeDefinitions

    private val _scopes = safeHashMap<ScopeID, Scope>()

    fun loadScopes(modules : List<Module>){
        modules.forEach {
            loadModule(it)
        }
    }

    private fun loadModule(module: Module) {
        _scopeDefinitions.addAll(module.scopes)
    }

    internal fun createScope(scopeId: ScopeID, qualifier: Qualifier, source: Any? = null): Scope {
        if (!_scopeDefinitions.contains(qualifier)){
            throw Throwable("Scope '$qualifier' doesn't exist. Please declare it in a module.")
        }
        if (_scopes.contains(scopeId)) {
            throw Throwable("Scope with id '$scopeId' is already created")
        }
        val scope = Scope(qualifier,scopeId, _injector = _injector)
        source?.let {
            scope._source = source
        }
        scope.linkTo(rootScope)
        _scopes[scopeId] = scope
        return scope
    }

    internal fun getScopeOrNull(scopeId: ScopeID): Scope? {
        return _scopes[scopeId]
    }


    internal fun deleteScope(scope: Scope) {
        _injector.instanceRegistry.dropScopeInstances(scope)
        _scopes.remove(scope.id)
    }

    companion object {
        private const val ROOT_SCOPE_ID = "_"
        @PublishedApi
        internal val rootScopeQualifier = _q(ROOT_SCOPE_ID)
    }
}