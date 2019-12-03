import io.kotlintest.tables.row

fun <E> List<E>.toRows() = this.map(::row).toTypedArray()
