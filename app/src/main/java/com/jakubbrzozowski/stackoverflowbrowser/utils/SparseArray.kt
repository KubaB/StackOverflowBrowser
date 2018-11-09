package com.jakubbrzozowski.stackoverflowbrowser.utils

import android.util.LongSparseArray

inline fun <V> LongSparseArray<V>.getOrPut(key: Long, defaultValue: () -> V): V {
    val value = get(key)
    return if (value == null) {
        val answer = defaultValue()
        put(key, answer)
        answer
    } else {
        value
    }
}