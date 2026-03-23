# CLAUDE.md

This file provides guidance to Claude Code when working with code in this repository.

## Common Commands

- `./gradlew lintCheck` - Run ktlint and detekt checks (same as CI)
- `./gradlew ktlintFormat` - Automatically fix code style issues
- `./gradlew test` - Run unit tests
- `./gradlew clean` - Remove all build artifacts

## Module Structure

Single-module project with the main app under `:app`. Build logic lives in `buildSrc/` and `convention-plugins/`.

Package root: `app.futured.androidprojecttemplate`

## Architecture

**MVVM** with [Arkitekt](https://github.com/futuredapp/arkitekt) library:

1. **ViewModel** (`*ViewModel.kt`) - extends `BaseViewModel<ViewState>`, implements `Actions` interface from the screen
2. **ViewState** (`*ViewState.kt`) - holds mutable Compose state (`var counter by mutableIntStateOf(0)`)
3. **Events** (`*Events.kt`) - sealed class of one-time events (navigation, toasts); collected via `EventsEffect`
4. **Screen** (`*Screen.kt`) - Composable; receives `viewState`, collects events, delegates interactions to `Actions`

Actions are defined as a nested interface inside the Screen object:
```kotlin
object HomeScreen {
    interface Actions {
        fun onIncrementCounter()
        fun onNavigateToDetail()
    }
}
```

## Arkitekt API Reference

### `BaseCoreViewModel<VS>`
- `abstract val viewState: VS` — injected ViewState
- `sendEvent(event: Event<VS>)` — sends a one-time event to the UI layer

### `BaseViewModel<VS>` (extends `BaseCoreViewModel`, implements `CoroutineScopeOwner`)
- Provides `coroutineScope` backed by `viewModelScope`
- Inherits all `CoroutineScopeOwner` extension functions below

### Use cases
Always extend the Arkitekt base classes — never use plain `suspend` functions with `invoke()`:

```kotlin
// UseCase<ARGS, RESULT> — single async operation
class SignInUseCase @Inject constructor(...) : UseCase<Unit, Unit>() {
    override suspend fun build(args: Unit) { /* business logic */ }
}

// FlowUseCase<ARGS, T> — streaming operation
class ObserveSomethingUseCase @Inject constructor(...) : FlowUseCase<Unit, MyModel>() {
    override fun build(args: Unit): Flow<MyModel> = /* … */
}
```

### `CoroutineScopeOwner` — use-case execution in ViewModels

```kotlin
// Async execution with callbacks (preferred; cancels previous by default)
someUseCase.execute {
    onStart   { /* show loading */ }
    onSuccess { value -> sendEvent(MyEvent) }   // sendEvent is non-suspend, safe here
    onError   { throwable -> /* … */ }
}

// Suspend execution — use inside launchWithHandler for error handling
launchWithHandler {
    val result = someUseCase.execute()   // returns Result<T>
    result.getOrNull()                   // or getOrThrow(), getOrDefault(), fold(…)
}

// Flow use case
someFlowUseCase.execute {
    onStart    { }
    onNext     { value -> }
    onError    { throwable -> }
    onComplete { }
}
```

### `EventsEffect` / `onEvent`
```kotlin
EventsEffect {
    onEvent<MyEvent> { /* handle */ }
}
```

## Navigation

Jetpack Navigation Compose with:
- **`Destinations.kt`** - sealed class with typed route objects (support arguments, deep links)
- **`NavRouter`** - interface abstracting navigation operations, implemented by `NavRouterImpl`
- **`NavGraph.kt`** - registers all screens/dialogs in `NavHost`

Arguments are passed as serialized Base64 strings via `SavedStateHandle`.

## Dependency Injection

**Hilt** throughout:
- `@HiltAndroidApp` on `App`, `@AndroidEntryPoint` on `AppActivity`
- `@HiltViewModel` on ViewModels, `@ViewModelScoped` on ViewState
- Modules: `ApplicationModule` (singletons), `NetworkModule` (Retrofit/OkHttp)

## Network Layer

Retrofit + OkHttp + kotlinx.serialization:
- Base URL and constants in `Constants.kt`
- API interface: `ApiService.kt` with suspend functions
- Models annotated with `@Serializable`
- `ZonedDateTimeSerializer` provided for date handling

## Build Flavors

Flavor dimension: `api` with three flavors:
- **mock** - local mock data
- **dev** - development API
- **prod** - production API

Build types: `debug`, `enterprise` (minified, debug key), `release` (minified, release key).

## Code Style

- Max line length: **140 characters**
- Indent: **4 spaces**, trailing commas allowed
- Ktlint code style: `android_studio`
- Detekt config: `config/detekt.yml`

## Naming Conventions

- `HomeViewModel`, `HomeViewState`, `HomeEvents`, `HomeScreen`
- Event objects: `NavigateToDetailEvent`, `NavigateBackEvent`
- Action methods: `onIncrementCounter()`, `onNavigateToDetail()` (prefix `on`)
- Composable functions: PascalCase; preview functions: `private fun HomePreview()`

## Design System

Material3 via `MaterialTheme`. Colors, typography, shapes, and dimensions defined in `app/src/main/kotlin/.../ui/theme/`.

Use `Dimensions.kt` tokens for spacing — avoid raw `dp` literals where theme tokens exist.

## Testing

- Unit tests: JUnit 4 + MockK
- Instrumented tests: AndroidJUnit4
- Run unit tests: `./gradlew test`
- Run module tests: `./gradlew :app:test`
