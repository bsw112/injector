package com.manta.injector

data class StringQualifier(override val value: QualifierValue) : Qualifier {
    override fun toString(): String {
        return value
    }
}