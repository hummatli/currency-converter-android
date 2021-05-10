package com.mobline.domain.repository

interface EventLogRepository {
    fun logScreen(name: String)
}