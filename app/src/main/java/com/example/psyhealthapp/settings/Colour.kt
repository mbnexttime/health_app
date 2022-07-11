package com.example.psyhealthapp.settings

import android.graphics.Color

class Colour {

    var whiteness : Float = 0F
    var darkness : Float = 0F
    var pureColour : Int = Color.argb(255, 255, 0, 0)

    constructor(color: Int) {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)

        val max = maxOf(r, g, b)
        val min = minOf(r, g, b)
        whiteness = min.toFloat() / 255
        darkness = 1F - reverseComponentWhiteness(max).toFloat() / 255
        val newR = reverseComponentDarkness(reverseComponentWhiteness(r))
        val newG = reverseComponentDarkness(reverseComponentWhiteness(g))
        val newB = reverseComponentDarkness(reverseComponentWhiteness(b))

        pureColour = Color.argb(255, newR, newG, newB)
    }

    private fun reverseComponentWhiteness(component: Int): Int {
        var newComponent = ((component - 255 * whiteness) / (1 - whiteness)).toInt()
        if (newComponent > 250) newComponent = 255
        return newComponent
    }

    private fun reverseComponentDarkness(component: Int): Int {
        var newComponent = (component / (1 - darkness)).toInt()
        if (newComponent < 5) newComponent = 0
        return newComponent
    }

    constructor(wh : Float, dar : Float, pC : Int) {
        whiteness = wh
        darkness = dar
        pureColour = pC
    }


    fun getColor(): Int {
        return colourWhiteness(
            colourDarkness(
                pureColour
            )
        )
    }

    private fun colourComponentDarkness(component : Int): Int {
        return component - (component * darkness).toInt()
    }

    private fun colourDarkness(pureColour: Int): Int {
        return Color.argb(
            Color.alpha(pureColour),
            colourComponentDarkness(Color.red(pureColour)),
            colourComponentDarkness(Color.green(pureColour)),
            colourComponentDarkness(Color.blue(pureColour))
        )
    }

    private fun colourComponentWhiteness(component: Int): Int {
        return component + ((255 - component) * whiteness).toInt()
    }

    private fun colourWhiteness(pureColour: Int): Int {
        return Color.argb(
            Color.alpha(pureColour),
            colourComponentWhiteness(Color.red(pureColour)),
            colourComponentWhiteness(Color.green(pureColour)),
            colourComponentWhiteness(Color.blue(pureColour))
        )
    }
}