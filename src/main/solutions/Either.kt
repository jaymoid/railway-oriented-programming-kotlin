sealed class Either<E, out A> {

    internal data class Left<E, out A>(val value: E) : Either<E, A>() {

        override fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B> = Left(value)

        override fun <B> map(f: (A) -> B): Either<E, B> = Left(value)

        override fun toString(): String = "Left($value)"

        override fun <B> fold(ifLeft: (E) -> B, ifRight: (A) -> B): B = ifLeft(value)

        override fun <B> flatTap(f: (A) -> Either<E, B>): Either<E, A> = this
    }

    internal data class Right<E, out A>(val value: A) : Either<E, A>() {

        override fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B> = f(value)

        override fun <B> map(f: (A) -> B): Either<E, B> = Right(f(value))

        override fun toString(): String = "Right($value)"

        override fun <B> fold(ifLeft: (E) -> B, ifRight: (A) -> B): B = ifRight(value)
        override fun <B> flatTap(f: (A) -> Either<E, B>): Either<E, A> {
            f(value)
            return this
        }
    }

    abstract fun <B> map(f: (A) -> B): Either<E, B>

    abstract fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B>

    abstract fun <B> fold(ifLeft: (E) -> B, ifRight: (A) -> B): B

    abstract fun <B> flatTap(f: (A) -> Either<E, B>): Either<E, A>

}
