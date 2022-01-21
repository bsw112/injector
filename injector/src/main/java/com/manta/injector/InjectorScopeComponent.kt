package com.manta.injector

fun <T : Any> T.getScopeId() = this::class.getFullName() + "@" + this.hashCode()
fun <T : Any> T.getScopeName() = TypeQualifier(this::class)