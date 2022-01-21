package com.manta.injector.instance

import com.manta.injector.BeanDefinition
import com.manta.injector.InstanceContext
import com.manta.injector.Scope
import com.manta.injector.emptyParametersHolder

abstract class InstanceFactory<T>(val beanDefinition: BeanDefinition<T>) {
    abstract fun get(context: InstanceContext): T
    abstract fun isCreated(context: InstanceContext? = null): Boolean
    open fun create(context: InstanceContext): T {
        return beanDefinition.definition.invoke(
            context.scope,
            context.parameters ?: emptyParametersHolder()
        )
    }

    abstract fun drop(scope: Scope? = null)

    abstract fun dropAll()
}