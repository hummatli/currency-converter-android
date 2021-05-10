package com.mobline.data.local.base

import kotlin.reflect.KProperty

interface ReadDelegate<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}