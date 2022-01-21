package com.manta.injector.qualifier

interface Qualifier {
    val value: QualifierValue
}

typealias QualifierValue = String

fun _q(name: String) = StringQualifier(name)