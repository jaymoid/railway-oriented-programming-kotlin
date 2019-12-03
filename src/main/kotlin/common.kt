import java.time.Instant

data class Request(val formData: UserData) {
    val requestTime: Instant = Instant.now()
}
data class UserData(val firstName: String, val surname: String, val email: String? = null, val id: String? = null)

data class Employee(val email: String, val firstName: String, val surname: String, val id: Int)

val log = Log()
class Log {
    fun error(s: String) = System.err.println(s)
}

class DatabaseException(override val message: String?) : RuntimeException(message)

class RequestException(override val message: String?) : RuntimeException(message)
