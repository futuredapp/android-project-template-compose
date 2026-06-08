package app.futured.androidprojecttemplate.domain.usecase

import app.futured.arkitekt.crusecases.FlowUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.time.Duration

class CounterUseCase @Inject constructor() : FlowUseCase<CounterUseCase.Args, Long> {

    override fun build(args: Args): Flow<Long> = flow {
        var counter = 0L
        while (currentCoroutineContext().isActive) {
            emit(counter++)
            delay(args.interval)
        }
    }

    data class Args(val interval: Duration)
}
