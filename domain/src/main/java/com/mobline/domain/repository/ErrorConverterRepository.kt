package com.mobline.domain.repository

interface ErrorConverterRepository {
    fun getError(code: Int, identifier: String)
}