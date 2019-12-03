import eitherusecase.DbErrorCannotAddUser
import eitherusecase.DbErrorCannotCreateEmployee
import eitherusecase.DomainError
import eitherusecase.InvalidRequest
import eitherusecase.executeEitherUseCase
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

/*


WELCOME TO PART 4 :)

* Convert the code in "src/main/EitherUseCase.kt" to use your Option type.

* The functions used by the usecase shoukd return Either<...>, instead of booleans/throwing exceptions, etc.

* Uncomment the lines in the tests and make them pass!

If you get stuck, take a peek at the solution in solutions/OptionUseCaseFunctional.kt

*/

class Part2_UseEither : StringSpec({

    // This is just some test/dumy data
    val validRequests = listOf(
        UserData(firstName = "Eve", surname = "Jones"),
        UserData(firstName = "Beel", surname = "Zebub")
    ).map(::Request).toRows()

    // Uncomment and make the tests pass
    // "valid requests" {
    //     forall(*validRequests) { r: Request ->
    //         (executeEitherUseCase(r) is Either.Right<DomainError, Employee>) shouldBe true
    //     }
    // }

    val invalidRequests = listOf(
        UserData(firstName = "", surname = "Wilson") to InvalidRequest,
        UserData(firstName = "STEVE", surname = "") to InvalidRequest,
        UserData(
            firstName = "Peter",
            surname = "Fletcher-Harvey-Montgomery"
        ) to DbErrorCannotAddUser, // DB cant save, surname too long!
        UserData(firstName = "Sally", surname = "Pools") to DbErrorCannotCreateEmployee
    ).map { (userData, err) -> Request(userData) to err }.toRows()

    // Uncomment and make the tests pass
    // "invalid requests" {
    //     forall(*invalidRequests) { (req, expectedError) ->
    //         executeEitherUseCase(req) shouldBe Either.Left<DomainError, Employee>(expectedError)
    //         executeEitherUseCase(req) shouldBe Either.Left<DomainError, Employee>(expectedError)
    //     }
    // }
})
