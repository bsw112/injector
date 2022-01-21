package com.manta.injector.instance

import com.manta.injector.BeanDefinition
import com.manta.injector.InjectorPlatformTools
import com.manta.injector.context.InstanceContext
import com.manta.injector.Scope

class SingleInstanceFactory<T>(beanDefinition: BeanDefinition<T>)  : InstanceFactory<T>(beanDefinition) {

    private var value : T? = null

    private fun getValue() : T = value ?: error("Single instance created couldn't return value")

    override fun get(context: InstanceContext): T {
        InjectorPlatformTools.synchronized(this) {
            if (!isCreated(context)) {
                value = create(context)
            }
        }
        return getValue()
    }



    override fun isCreated(context: InstanceContext?): Boolean {
        return value != null
    }

    override fun drop(scope: Scope?) {
        value = null
    }

    override fun dropAll() {
        drop()
    }
}