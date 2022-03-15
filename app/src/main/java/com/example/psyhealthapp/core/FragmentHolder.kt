package com.example.psyhealthapp.core

import androidx.fragment.app.Fragment

/**
 * Implement your own subclass of Fragment, and corresponding view with it.
 * Return this fragment instance
 */
fun interface FragmentHolder {
    fun getFragment(): Fragment
}