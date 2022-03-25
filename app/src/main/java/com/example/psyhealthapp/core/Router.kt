package com.example.psyhealthapp.core

import android.os.Bundle

interface Router {
    fun navigateTo(screenType: ScreenType, payload: Bundle?)
}