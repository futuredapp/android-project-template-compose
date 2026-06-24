package app.futured.androidprojecttemplate.ui.screens.first

import android.content.res.Resources
import app.futured.androidprojecttemplate.R
import app.futured.androidprojecttemplate.domain.usecase.CounterUseCase
import app.futured.androidprojecttemplate.domain.usecase.GetPersonUseCase
import app.futured.arkitekt.compose.BaseViewModel
import app.futured.arkitekt.crusecases.execute
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class FirstViewModel @Inject constructor(
    override val viewState: FirstViewState,
    private val getPersonUseCase: GetPersonUseCase,
    private val counterUseCase: CounterUseCase,
    private val resources: Resources,
) : BaseViewModel<FirstViewState>(),
    First.Actions {

    private companion object {
        const val COUNTER_ALERT_AT_SECONDS = 30L
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    }

    init {
        getRandomPerson()
        observeCounter()
        updateCreatedAtTimestamp()
    }

    override fun onContinue() {
        sendEvent(NavigateToSecondEvent)
    }

    private fun getRandomPerson() {
        getPersonUseCase.execute(Unit) {
            onSuccess { person ->
                viewState.randomPerson = person.name.orEmpty()
            }
            onError {
                viewState.randomPerson = "Failed to fetch"
            }
        }
    }

    private fun observeCounter() = counterUseCase.execute(CounterUseCase.Args(interval = 200.milliseconds)) {
        onNext { count ->
            viewState.counter = count

            if (count == COUNTER_ALERT_AT_SECONDS) {
                Timber.d("Counter reached $COUNTER_ALERT_AT_SECONDS")
                sendEvent(ShowToastEvent(resources.getString(R.string.first_counter_alert, COUNTER_ALERT_AT_SECONDS.toString())))
            }
        }
        onError { error ->
            Timber.e(error)
        }
    }

    private fun updateCreatedAtTimestamp() {
        val now = ZonedDateTime.now().format(DATE_FORMATTER)
        viewState.createdAt = resources.getString(R.string.first_created_at, now)
    }
}
