package app.futured.androidprojecttemplate.domain.usecase

import app.futured.androidprojecttemplate.data.remote.StarWarsApi
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject
import kotlin.random.Random

class GetPersonUseCase @Inject constructor(private val starWarsApi: StarWarsApi) : UseCase<Unit, StarWarsApi.Person> {

    override suspend fun build(args: Unit): StarWarsApi.Person =
        starWarsApi.getPerson(Random.nextInt(until = 100)).getOrThrow()
}
