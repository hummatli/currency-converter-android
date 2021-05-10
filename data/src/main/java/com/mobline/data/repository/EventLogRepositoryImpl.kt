package com.mobline.data.repository

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.mobline.domain.repository.EventLogRepository

class EventLogRepositoryImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : EventLogRepository {

    override fun logScreen(name: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, name)
        }
    }
}