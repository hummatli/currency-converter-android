package com.mobline.domain.usecase.logs

import com.mobline.domain.base.BaseUseCase
import com.mobline.domain.exceptions.ErrorConverter
import com.mobline.domain.repository.EventLogRepository
import kotlin.coroutines.CoroutineContext

class LogScreenInfoUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: EventLogRepository,
) : BaseUseCase<LogScreenInfoUseCase.Params, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Params) {
        repository.logScreen(params.name)
    }

    class Params(val name: String)
}