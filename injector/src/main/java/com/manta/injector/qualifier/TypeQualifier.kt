package com.manta.injector.qualifier

import com.manta.injector.getFullName
import kotlin.reflect.KClass

class TypeQualifier(val type : KClass<*>) : Qualifier {

    override val value: QualifierValue = type.getFullName()

    override fun toString(): String {
        return "q:'$value'"
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TypeQualifier

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}
