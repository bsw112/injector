package com.manta.sting

import android.app.Application
import com.manta.injector.context.GlobalContext.startInjector
import com.manta.injector.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startInjector {
            modules(module)
        }
    }

    val module = module {
        scope<MainActivity> {
            scoped { Foo() }
        }
    }
}