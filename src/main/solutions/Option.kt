/*
    This file contains the solution/answers for creating your Option data type.

*/
sealed class Option<out A> {

    // 1
    data class Some<out A>(val value: A) : Option<A>() {
        override fun <B> fold(ifNone: () -> B, ifSome: (A) -> B): B = ifSome(value)

        // 3
        override fun <B> map(f: (A) -> B): Option<B> = Some(f(value))

        // 4
        override fun <B> flatMap(f: (A) -> Option<B>): Option<B> = f(value)

        // 5
        override fun filter(p: (A) -> Boolean): Option<A> = if (p(value)) this else None
    }

    // 2
    internal object None : Option<Nothing>() {
        override fun <B> fold(ifNone: () -> B, ifSome: (Nothing) -> B): B = ifNone()

        // 3
        override fun <B> map(f: (Nothing) -> B): Option<B> = None

        //4
        override fun <B> flatMap(f: (Nothing) -> Option<B>): Option<B> = None

        // 5
        override fun filter(p: (Nothing) -> Boolean): Option<Nothing> = None
    }

    // 3
    abstract fun <B> map(f: (A) -> B): Option<B>

    // 4
    abstract fun <B> flatMap(f: (A) -> Option<B>): Option<B>

    // 5
    abstract fun filter(p: (A) -> Boolean): Option<A>

    // 6
    abstract fun <B> fold(ifNone: () -> B, ifSome: (A) -> B): B
}
