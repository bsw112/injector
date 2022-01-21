package com.manta.injector

import com.manta.injector.qualifier.TypeQualifier

fun <T : Any> T.getScopeId() = this::class.getFullName() + "@" + this.hashCode()
fun <T : Any> T.getScopeName() = TypeQualifier(this::class)