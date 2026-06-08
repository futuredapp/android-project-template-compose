package app.futured.androidprojecttemplate.domain.usecase

import app.futured.androidprojecttemplate.data.persistence.UserPersistence
import app.futured.androidprojecttemplate.navigation.MainRoute
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetInitialDestinationUseCase @Inject constructor(private val userPersistence: UserPersistence) : UseCase<Unit, MainRoute> {

    override suspend fun build(args: Unit): MainRoute =
        if (userPersistence.isUserLoggedIn()) MainRoute.First else MainRoute.Login
}
