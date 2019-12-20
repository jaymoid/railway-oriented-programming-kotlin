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

* Convert the code in "src/main/EitherUseCase.kt" to use your new Either type.

* The functions used by the usecase should return Either<...>, instead of booleans/throwing exceptions, etc.

* Uncomment the lines in the tests and make them pass!

* If you get stuck, take a peek at the solution in solutions/OptionUseCaseFunctional.kt

*/

class Part2_UseEither : StringSpec({

    // This is just some test/dummy data
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


/*
LET'S RECAP WHAT WE HAVE...

* List of finite number of domain errors that can occur
* Each Error could be exhaustively handled in a kotlin when clause
* Short simple business logic, no littering of error handling through the code.



Where do you go from here?
-----------------------------

Q: Can't somebody describe the Types for me?

A: Yes, I suggest using Arrow. The Functional library to accompany Kotlin. It has defined Option, Either and many other
data types. They have fixed many of the little issues where you've had to specify types explicitly.


Q: This is a fairly simple example, our data maps directly into the next function like a chain. If the flow is not this
simple, and we want to use some data we may have discarded earlier in the chain we would have to nest lambdas and this
can look a bit more complicated, this isn't really much better than the imperative code!

A: The answer To this is Monad Comprehensions https://arrow-kt.io/docs/patterns/monad_comprehensions/

With Monad Comprehensions our code could look like this:

internal fun executeEitherUseCase(request: Request): Either<DomainError, Employee> =
    Either.fx {
        val (_) = validateRequest(request)
        val (rawFormData) = request.formData
        val (formData) = formData.canonicalizeEmail()
        val (_) = db.recordNewUserRequest(formData)
        val (_) = db.createEmployee(formData)
        smtpServer.sendEmail(formData)

        formData // aka "return employee" in the RHS of the either
    }

^ Again anything that returned an Either.Left would immediately exit the processing of the comprehension and return the
domain error contained in the Either.

You could bring in the Arrow library and try and convert your existing logic using an arrow monad comprehensions, you
already have the tests!


*/
