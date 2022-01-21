package com.manta.sting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.manta.injector.Scope
import com.manta.injector.activityScope
import com.manta.injector.component.AndroidScopeComponent
import com.manta.injector.ext.inject

class MainActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope : Scope by activityScope()
    val foo : Foo by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("monster", foo.msg)


    }

}