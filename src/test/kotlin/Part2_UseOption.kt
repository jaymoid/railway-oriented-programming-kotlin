import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

/*
WELCOME TO PART 2 :)

0. Convert the code in "src/main/OptionalUseCase.kt" to kotlin

1. The functions used by the usecase shoukd return optionals, instead of booleans/throwing exceptions, etc.

2. Uncomment the lines in the tests and make them pass!

*/
class Part2_UseOption : StringSpec({

    "valid requests" {
        val rows = listOf(222, 666).toRows()
        forall(*rows) { id: Int ->
            // UNCOMMENT THIS...
            // executeUseCase(id) shouldBe Option.Some("OK")
        }
    }

    "invalid requests" {
        forall(*listOf(111, 333, 444, 555, 777).toRows()) { id: Int ->
            // UNCOMMENT THIS...
            // executeUseCase(id) shouldBe Option.None
        }
    }

})

private fun <E> List<E>.toRows() = this.map(::row).toTypedArray()