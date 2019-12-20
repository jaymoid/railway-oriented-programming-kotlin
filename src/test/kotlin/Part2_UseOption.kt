import io.kotlintest.data.forall
import io.kotlintest.specs.StringSpec
import optionusecase.executeOptionalUseCase
import io.kotlintest.shouldBe

/*

* WELCOME TO PART 2 :)

* Convert the code in "src/main/OptionUseCase.kt" to use your Option type.

* The functions used by the usecase shoukd return option<...>, instead of booleans/throwing exceptions, etc.

* Uncomment the lines in the tests and make them pass!

* If you get stuck, take a peek at the solution in solutions/OptionUseCaseFunctional.kt

*/

class Part2_UseOption : StringSpec({

    val validRequests = listOf(
        UserData(firstName = "Eve", surname = "Jones"),
        UserData(firstName = "Beel", surname = "Zebub")
    ).map(::Request).toRows()

    // Uncomment and make these tests pass...
    // "valid requests" {
    //     forall(*validRequests) { r: Request ->
    //         (executeOptionalUseCase(r) is Option.Some) shouldBe true
    //     }
    // }

    val invalidRequests = listOf(
        UserData(firstName = "", surname = "Wilson"), // invalid, no firstname
        UserData(firstName = "STEVE", surname = ""), // invalid, no surname
        UserData(firstName = "Peter", surname = "Fletcher-Harvey-Montgomery"), // DB cant save, surname too long!
        UserData(firstName = "Sally", surname = "Pools") // DB cant create employee
    ).map(::Request).toRows()

    // Uncomment and make these tests pass...
    // "invalid requests" {
    //     forall(*invalidRequests) { req ->
    //         executeOptionalUseCase(req) shouldBe Option.None
    //     }
    // }
})
