package com.droidcon.movieapp.common

sealed class UiEvents {

    /**
     * This function contains common user initiated events,
     * like showing SnackBars, Toast Messages and even Navigation events
     */
    data class SnackBarEvent(val message: String) : UiEvents()
}
