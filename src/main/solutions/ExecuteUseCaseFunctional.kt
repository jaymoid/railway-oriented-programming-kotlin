import java.lang.RuntimeException

fun executeUseCase(requestId: Int): Option<String> =
    receiveRequest(requestId)
        .flatMap(::validateRequest)
        .flatMap(::canonicalizeEmail)
        .flatMap(db::updateDbFromRequest)
        .flatTap(smtpServer::sendEmail)
        .map { "OK" }

data class Request(var email: String, val firstName: String, val surname: String, val id: Int)

fun receiveRequest(requestId: Int): Option<Request> =
    validRequests.find { it.id == requestId }?.let { Option.Some(it) } ?: Option.None

val validRequests = listOf(
    Request("", "Steve", "Smith", 111),
    Request("", "Eve", "Jones", 222),
    Request("", "Chris", "Piper", 333),
    Request("", "", "Wilson", 444),
    Request("", "Prince", "", 555),
    Request("", "Beel", "Zebub", 666),
    Request("", "Prince", "", 7777)
)

fun canonicalizeEmail(request: Request): Option<Request> =
    Option.Some(
        request.copy(
            email = "${request.firstName}.${request.surname}@railway.com"
        )
    )

fun validateRequest(request: Request): Option<Request> =
    Option.Some(request).filter { req ->
        req.firstName.isNotEmpty() && req.surname.isNotEmpty()
    }

val db = Database()

class Database {

    fun updateDbFromRequest(request: Request): Option<Request> =
        try {
            if (!updateRequest(request))
                Option.None
            else
                Option.Some(request)
        } catch (e: DatabaseException) {
            Option.None
        }

    private fun updateRequest(request: Request): Boolean {
        // not a nice DB, we only persist even Request ids!
        return if (request.id % 2 == 0)
            true
        else
            false
    }
}

class DatabaseException(override val message: String?) : RuntimeException(message)

val smtpServer = SMTPServer()

class SMTPServer {
    fun sendEmail(request: Request) =
        if ("\\w{1,}\\.\\w{1,}@railway.com$".toRegex().matches(request.email))
            Option.Some(request) // email sent
        else
            Option.None // email not sent :( not in correct format
}

val log = Log()

class Log {
    fun error(s: String) = System.err.println(s)
}


