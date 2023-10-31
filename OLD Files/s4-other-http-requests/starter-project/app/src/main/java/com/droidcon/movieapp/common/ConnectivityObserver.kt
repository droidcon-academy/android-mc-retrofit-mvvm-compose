package com.droidcon.movieapp.common

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Lost, UnAvailable
    }
}
