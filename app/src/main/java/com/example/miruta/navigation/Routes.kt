package com.example.miruta.navigation

object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val ADD = "add"
    const val DETAIL = "detail/{id}"
    const val SETTINGS = "settings"
    fun detalle(id:Int) = "detail/$id"
}
