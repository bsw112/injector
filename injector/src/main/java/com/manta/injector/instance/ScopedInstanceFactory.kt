package com.manta.injector.instance

import com.manta.injector.*

class ScopedInstanceFactory<T>(beanDefinition: BeanDefinition<T>) :
    InstanceFactory<T>(beanDefinition) {

    private var values = hashMapOf<ScopeID,T>()

    override fun isCreated(context: InstanceContext?): Boolean = (values[context?.scope?.id] != null)


    override fun create(context: InstanceContext): T {
        return if (values[context.scope.id] == null) {
            super.create(context)
        } else values[context.scope.id] ?:  error("Scoped instance not found for ${context.scope.id}")
    }

    override fun get(context: InstanceContext): T {
        if (context.scope.scopeQualifier != beanDefinition.scopeQualifier){
            error("Wrong Scope: trying to open instance for ${context.scope.id} in $beanDefinition")
        }
        InjectorPlatformTools.synchronized(this) {
            if (!isCreated(context)) {
                values[context.scope.id] = create(context)
            }
        }
        return values[context.scope.id] ?: error("Scoped instance not found for ${context.scope.id}")
    }

    override fun drop(scope: Scope?) {
        scope?.let {
            values.remove(it.id)
        }
    }

    override fun dropAll(){
        values.clear()
    }
}