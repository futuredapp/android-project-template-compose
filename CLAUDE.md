# CLAUDE.md

This file provides guidance to Claude Code when working with code in this repository.

## Common Commands

- `./gradlew lintCheck` - Run ktlint and detekt checks (same as CI)
- `./gradlew ktlintFormat` - Automatically fix code style issues
- `./gradlew test` - Run unit tests
- `./gradlew clean` - Remove all build artifacts

## Module Structure

Single-module project with the main app under `:app`. Build logic lives in `buildSrc/` and `convention-plugins/`.

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
// Always define a nested Args data class, even for a single parameter; never use the raw type directly.
class SetUserLoggedInUseCase @Inject constructor(...) : UseCase<SetUserLoggedInUseCase.Args, Unit> {
    override suspend fun build(args: Args) { /* business logic */ }
    data class Args(val isLoggedIn: Boolean)
}

// Use Unit only when there are truly no inputs
class SignInUseCase @Inject constructor(...) : UseCase<Unit, Unit> {
    override suspend fun build(args: Unit) { /* business logic */ }
}

// FlowUseCase<ARGS, T> — streaming operation (same Args convention applies)
class ObserveSomethingUseCase @Inject constructor(...) : FlowUseCase<Unit, MyModel> {
    override fun build(args: Unit): Flow<MyModel> = /* … */
}
```

### Calling use cases outside ViewModels

`build(args)` is a plain `suspend fun` — call it directly from any coroutine context (e.g. `PagingSource.load()`, a coroutine builder):

```kotlin
// In PagingSource.load() or any suspend context
val response = someUseCase.build(args)
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

Uses **Navigation 3** (`androidx.navigation3`), not standard Navigation Compose.

- **Routes** — `MainRoute.kt`: `@Serializable sealed interface MainRoute : NavKey`. Use `data class` for routes with args, `data object` for routes without:
  ```kotlin
  data object Home : MainRoute
  data class Detail(val args: DetailScreenArgs) : MainRoute
  ```
- **Screen registration** — `NavGraph.kt`: add an `entry<MainRoute.Foo> { ... }` block inside `entryProvider { }` on `NavDisplay` for every new screen.
- **Triggering navigation** — `NavRouter` interface + `NavRouterImpl`. `NavRouterImpl` holds a `NavBackStack<NavKey>` and mutates it:
  - `backStack.add(route)` — push
  - `backStack.removeLastOrNull()` — pop (`popBackStack()`)
  - `backStack.clear(); backStack.add(route)` — replace all (e.g. `navigateToLogin()`)

  Add new `navigateTo*` methods to both `NavRouter` and `NavRouterImpl` when adding a new screen.
- **Screen args** — Each screen that needs input has a `@Serializable data class *ScreenArgs` in its own package, embedded in the route. The **calling** ViewModel constructs the args when firing a navigation event; `NavGraph` only threads `entry.args` to the screen; the destination ViewModel receives them via `@AssistedInject`:
  ```kotlin
  // Calling ViewModel (e.g. HomeViewModel)
  sendEvent(NavigateToDetailEvent(DetailScreenArgs(title = "Demo")))

  // NavGraph.kt — just pass through
  entry<MainRoute.Detail> {
      DetailScreen(it.args, navigation = backStackNavigator)
  }

  // Destination ViewModel
  @HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
  class DetailViewModel @AssistedInject constructor(
      @Assisted val args: DetailScreenArgs, override val viewState: DetailViewState,
  ) : BaseViewModel<DetailViewState>(), Detail.Actions {
      @AssistedFactory interface Factory { fun create(args: DetailScreenArgs): DetailViewModel }
  }
  ```
- **Bottom sheets** — `BottomSheetSceneStrategy` is registered as a `sceneStrategy` on `NavDisplay`.
- **Inter-screen results** — `ResultStore` + `LocalResultStore` composition local; use `navigateBackWithResult` / `setCurrentResult` on `NavRouterImpl`. Read results with `consume(key)`, which removes the value so it doesn't re-trigger on recomposition.

## Networking

**Stack:** Ktor 3 (CIO engine) + Ktorfit for type-safe API interfaces (KSP-generated).

### API interface
- `ApiService.kt` — suspend functions annotated with Ktorfit `@GET`/`@POST`/etc. The Ktorfit instance is built with `NetworkResultConverterFactory`, so functions may return either the model directly or `NetworkResult<T>`.

### Ktor plugins (each in `data/remote/plugins/`, implementing the local `HttpClientPlugin` interface and installed in `NetworkModule`)
| Plugin | Responsibility |
|---|---|
| `ContentNegotiationPlugin` | Kotlinx Serialization JSON; sets `Content-Type` header |
| `LoggingPlugin` | `LogLevel.ALL` via Timber (`tag = "Ktor"`) |
| `HttpTimeoutPlugin` | Connect 10 s, request 15 s, socket 10 s |
| `UserAgentPlugin` | Custom UA with app version, application id, Android OS, device model, Ktor version |

### Error handling
`NetworkResult<T>` — `Success(data)` / `Failure(error: NetworkError)`; `getOrThrow()` rethrows the error.

`NetworkError` subtypes: `HttpError(statusCode, message)`, `SerializationError`, `ConnectionError`, `UnknownError`. Mapped by `NetworkErrorParser` and the custom `NetworkResultConverterFactory`.

### DI wiring
`NetworkModule` provides `HttpClient` (Singleton, wired with all plugins), `Ktorfit`, and `ApiService`. Base URL comes from `Constants.Api.BASE_PROD_URL`.
`ApplicationModule` provides the shared `Json` instance (lenient, `ignoreUnknownKeys = true`, contextual `ZonedDateTime` serializer).

## Dependency Injection

**Hilt** throughout:
- `@HiltAndroidApp` on `App`, `@AndroidEntryPoint` on `AppActivity`
- `@HiltViewModel` on ViewModels, `@ViewModelScoped` on ViewState
- Modules: `ApplicationModule` (singletons), `NetworkModule` (Ktor/Ktorfit)

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
