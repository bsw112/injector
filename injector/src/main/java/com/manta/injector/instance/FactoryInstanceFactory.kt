package com.manta.injector.instance

import com.manta.injector.BeanDefinition
import com.manta.injector.context.InstanceContext
import com.manta.injector.Scope

class FactoryInstanceFactory<T>(beanDefinition: BeanDefinition<T>) :
    InstanceFactory<T>(beanDefinition) {

    override fun isCreated(context: InstanceContext?): Boolean = false

    override fun get(context: InstanceContext): T {
        return create(context)
    }

    override fun drop(scope: Scope?) {

    }

    override fun dropAll() {

    }
}