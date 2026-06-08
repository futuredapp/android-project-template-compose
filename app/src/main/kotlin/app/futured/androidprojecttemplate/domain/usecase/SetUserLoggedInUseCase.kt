package app.futured.androidprojecttemplate.domain.usecase

import app.futured.androidprojecttemplate.data.persistence.UserPersistence
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class SetUserLoggedInUseCase @Inject constructor(private val userPersistence: UserPersistence) :
    UseCase<SetUserLoggedInUseCase.Args, Unit> {

    override suspend fun build(args: Args) = userPersistence.setUserLoggedIn(args.isLoggedIn)

    data class Args(val isLoggedIn: Boolean)
}
