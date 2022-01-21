package com.manta.injector

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.manta.injector.context.GlobalContext
import com.manta.injector.context.InjectorContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class LifecycleScopeDelegate<T>(
    val lifecycleOwner: LifecycleOwner,
    private val koinContext: InjectorContext = GlobalContext,
    private val createScope: (Injector) -> Scope = { injector: Injector -> injector.createScope(lifecycleOwner.getScopeId(), lifecycleOwner.getScopeName(), lifecycleOwner as T) },
) : ReadOnlyProperty<LifecycleOwner, Scope> {

    private var _scope: Scope? = null

    init {
        val koin = koinContext.get()

        val scopeId = lifecycleOwner.getScopeId()
         _scope = koin.getScopeOrNull(scopeId) ?: createScope(koin)

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(owner: LifecycleOwner) {
                if (_scope?.closed == false) {
                    _scope?.close()
                }
                _scope = null
            }
        })
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): Scope {
        return if (_scope != null) _scope!!
        else {
            if (!thisRef.isActive()) {
                error("can't get Scope for $lifecycleOwner - LifecycleOwner is not Active")
            } else {
                val koin = koinContext.get()
                _scope = koin.getScopeOrNull(thisRef.getScopeId()) ?: createScope(koin)
                _scope!!
            }
        }
    }
}

internal fun LifecycleOwner.isActive(): Boolean {
    val ownerState = lifecycle.currentState
    return ownerState.isAtLeast(Lifecycle.State.CREATED)
}
