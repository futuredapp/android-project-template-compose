package app.futured.androidprojecttemplate.domain.usecase

import app.futured.androidprojecttemplate.data.persistence.UserPersistence
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val userPersistence: UserPersistence) : UseCase<Unit, Unit> {

    override suspend fun build(args: Unit) {
        userPersistence.setUserLoggedIn(false)
    }
}
