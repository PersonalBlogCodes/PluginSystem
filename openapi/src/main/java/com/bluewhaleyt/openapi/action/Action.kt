package com.bluewhaleyt.openapi.action

import com.bluewhaleyt.openapi.Icon

interface Action {
    val icon: Icon? get() = null
    fun onClick()
}