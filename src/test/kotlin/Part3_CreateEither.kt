import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

typealias ErrorCode = Int
typealias ErrorReason = String

/*
                         _-====-__-======-__-========-_____-============-__
                       _(                                                 _)
                    OO(        Two track programming                       )_
                   0  (_          dead ahead! All aboard!                  _)
                 o0     (_                                                _)
                o         '=-___-===-_____-========-___________-===-_--='
              .o                                _________
             . ______          ______________  |         |      _____
           _()_||__|| ________ |            |  |_________|   __||___||__
          (         | |      | |            | __Y______00_| |_         _|
         /-OO----OO""="OO--OO"="OO--------OO"="OO-------OO"="OO-------OO"=P
        #####################################################################


    You know now that Option represents the single track in Railway oriented programming, so let's move onto the double
    track.

    Having two tracks enable us to transmit two types of data. Typically this is used to convey a success OR a failure.

    With the one track data type you probably found yourself feeling frustrated that the error information is lost.
    That error information is useful to debug your application, and find out why things didn't go to plan.

    You may have come across something that holds two pieces of information in the standard Kotlin library, a Pair.

    Either is different to a Pair. A Pair is an example of a product type. Where as Either is an example of a sum type.

    A Pair can hold two types simultaneously. e.g. Pair(1, 'a')

    However a Either can only hold one type at a time, the left side or the right: e.g.
        val success = Either.Right("FP Rocks! \m/");
        // or
        val error = Either.Left(404);

    The number of possible outcomes of a SUM type is calculated via:
        number of possibilities of left type  +  number of possibilities of right type

    Where as the of possible outcomes of a PRODUCT type is calculated via:
        number of possibilities of left type  *  number of possibilities of right type

    So you can probably see why it is called a SUM (+) type or a PRODUCT (*) Type.

    That was a little introduction to Algebraic Data Types. Pretty cool huh?!

    Some languages don't have the power to express Sum types, but luckily in Kotlin we have access to Sealed classes,
    which enable us to create a limited number of subclasses of a type, thus allowing us to create a limited number
    of constructors, meaning the data we get out of it

    ENOUGH WITH THE WAFFLE...

    You'll Implement "Either" as a sealed class again.

    To get started uncomment the code in the first test, implement the code in the Either class, make the tests pass and
    then move on.

    Scroll down for hint/s tips! if you get stuck.

    You'll need a "Left" class and a "Right" class inside Either to cope with the cases when you have data or when you
    don't.

    If you get stuck check the hints and tips below the tests, if you don't want any help, don't scroll too far! :)
*/

class Part1_CreateEither : StringSpec({

    "Create Either left with Either.Left(...) " {

        // uncomment the lines below, and make them compile!

        // val left1: Either<Int, String> = Either.Left(1)
        // val left2: Either<Char, Int> = Either.Left('a')
        // val left3: Either<String, Boolean> = Either.Left("Choo-choo!")
    }

    /*  ~~~~~ HINTS / TIPS ~~~~~

    a) The Left class needs to be a "data class", inside the Either class.

    b) the Generic type will be Left<E, out A>

    c) The Left class needs to have one instance variable, of type E, make the variable private.

    If you're still stuck don't worry check the solution folder and see if it makes sense.
    Copy over the solution and move on, don't sweat it.

    --------------------------------------------------------------------

    2. Left is ok, but Right is ALL-RIGHT! You know what to do...
    */
    "Create a Either.Right with Either.Right" {

        // uncomment the lines below, and make them compile!

        // val right1: Either<Int, Long> = Either.Right(42L)

        // val right2: Either<Boolean, String> = Either.Right("Ahoy hoy!")

        // val right3: Either<String, Char> = Either.Right('c')
    }

    /*   ~~~~~ HINTS / TIPS ~~~~~

    If you're still stuck don't worry check the solution folder and see if it makes sense.
    Copy over the solution and move on, don't sweat it.

    --------------------------------------------------------------------

    ALRIGHT! We defined an Either.

    we can also match on it using a when...
    */


    "Just an example of pattern matching" {

        // When you get here, just uncomment the lines, and see some pattern matching on your Option data type

        // val choo: Either<ErrorCode, String> = Either.Left(404)

        // when (choo) {
        //    is Either.Left -> println("Ah snap, error code${choo.value})")
        //    is Either.Right -> println("Woohhoo, response is ${choo.value}")
        // }

        /*
            As with optional, it's good, but typically we want more power and convenience from our types. Let's define
            some functions
         */
    }

    /*
    3.  Make your Either a Functor by implementing Map

    When you flatmap over the Either, only the right value is effected.
    */
    "Map over an Either.Right" {

        assertSoftly {

            // uncomment, make this compile and pass the tests.

            //val right: Either<Int, Long> = Either.Right(42L)
            //(right.map { it * 2 }) shouldBe Either.Right<Int, Long>(84L)

            //val rightList: Either<ErrorCode, List<String>> = Either.Right(listOf("Hello", " ", "World!"))
            //rightList.map { it.size } shouldBe Either.Right<ErrorCode, Int>(3)

            // But what happens whe we map a function over Left?
            //val left: Either<ErrorCode, Int> = Either.Left(418)
            //left.map { it * 2 } shouldBe left
        }
    }

    /*   ~~~~~ HINTS / TIPS  ~~~~~

    a) Add the following abstract function to the Option class:

    abstract fun <B> map(f: (A) -> B): Either<E, B>

    ... and then implement it in your Left and Right classes.

    b) f is the function you apply to the value in RIGHT side only.

    c) If you have a value in the Left position, then you can only reply with one thing, the unchanged value.

    If you're still stuck don't worry check the solution folder and see if it makes sense.
    Copy over the solution and move on.

    --------------------------------------------------------------------

    4.  Make your Either a Monad, by adding a flatMap function.

    When you flatmap over the Either, only the right value is effected.

    */
    "FlatMap over an Either.Right" {

        assertSoftly {

            // uncomment, make this compile and pass the tests.

            // val evensAreValid: (Int) -> Either<ErrorReason, Int> = { num: Int ->
            //     if (num % 2 == 0)
            //         Either.Right(num)
            //     else
            //         Either.Left("Not an even number :(")
            // }

            // val right1: Either<ErrorReason, Int> = Either.Right(1)
            // right1.flatMap(evensAreValid) shouldBe Either.Left<ErrorReason, Int>("Not an even number :(")

            // val right2: Either<ErrorReason, Int> = Either.Right(2)
            // right2.flatMap(evensAreValid) shouldBe Either.Right<ErrorReason, Int>(2)

            // // But what happens whe we flatMap a function over Left?
            // val leftie: Either<ErrorReason, Int> = Either.Left("Nope, sorry. ")
            // leftie.flatMap { Either.Right<ErrorReason, Int>(it * 2) } shouldBe leftie
        }
    }

    /* ~~~~~ HINTS / TIPS  ~~~~~

    a) Add the following abstract function to Either:

    abstract fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B>

    ... and then implement it in your Left and Right classes.

    b) If you have a left in the Either, then you can only reply with one thing, yup... the same Left value!


    --------------------------------------------------------------------

    6. Remember that Fold function that "breaks down" a data type to another single value, using the provided function.

        Have a crack at writing one for Either...

    */
    "Fold your Either" {

        // uncomment, make this compile and pass the tests.

        // val either1: Either<ErrorReason, List<Int>> = Either.Right(listOf(2, 4, 6, 8))

        // either1.fold(
        //     ifLeft = { 0 },
        //     ifRight = { it.sum() }
        // ) shouldBe 20

        // val either2: Either<ErrorReason, List<Int>> = Either.Left("soz, couldn't get the list *puppy dog eyes*")

        // either2.fold(
        //     ifLeft = { 0 },
        //     ifRight = { it.sum() }
        // ) shouldBe 0
    }

    /*
    --------------------------------------------------------------------

    7. Recall that flatTap is like flatMap, but it ignores the result of the function that is passed to it.

        Once again, we can only operate on the right hand side of the either. If the Either is occupied with a left
        value, then we can't execute the function passed to flattap.
     */

    "flatTap" {
        // Uncomment and make the tests pass!

        // val flatTapped: Either<ErrorCode, String> =
        //    Either.Right<ErrorCode, String>("Hello World")
        //        .flatTap { Either.Right<ErrorCode, Int>(it.length) }

        // flatTapped shouldBe Either.Right<ErrorCode, String>("Hello World")
    }

    /*

    ~~~~~ HINTS / TIPS  ~~~~~
    a) the function flatTap takes is of type  (A) -> Either<B>

    b) your flatTap will return: Either<E, A>

    --------------------------------------------------------------------

    You made it to the end of implementing tons of cool FP stuff in a Option data type! Great work.

    Next, let's put it to use and clean up some code spaghetti...

    Head over to src/test/Part4_UseEither.kt, it'll be like the Option exercise, but you get to retain the Error
    information.
    */
})
