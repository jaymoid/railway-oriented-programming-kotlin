package ExecuteUseCaseWithOption

import java.lang.RuntimeException

// vv Change my return type to Option<String> vv
fun executeUseCase(requestId: Int): String {

    var request: Request? = receiveRequest(requestId)
    if (request == null) {
        return "Cannot find user with that request id"
    }

    var isValidated: Boolean = validateRequest(request)
    if (!isValidated) {
        return "Request is not valid"
    }

    canonicalizeEmail(request)

    try {
        var result: Boolean = db.updateDbFromRequest(request)
        if (!result) {
            return "Customer record not found"
        }
    } catch (e: DatabaseException) {
        return "DB error: Customer record not updated"
    }

    if (!smtpServer.sendEmail(request.email)) {
        log.error("Customer email not sent")
    }

    // If we get to here I think it is ok?! *prays*
    return "OK";
}


data class Request(var email: String, val firstName: String, val surname: String, val id: Int)

// vv Change my return type vv
fun receiveRequest(requestId: Int): Request? =
    validRequests.find { it.id == requestId }

val validRequests = listOf(
    Request("", "Steve", "Smith", 111),
    Request("", "Eve", "Jones", 222),
    Request("", "Chris", "Piper", 333),
    Request("", "", "Wilson", 444),
    Request("", "Prince", "", 555),
    Request("", "Beel", "Zebub", 666),
    Request("", "Prince", "", 7777)
)

// vv Change my return type vv
fun canonicalizeEmail(request: Request): Unit {
    request.email = "${request.firstName}.${request.surname}@railway.com"
}

// vv Change my return type vv
fun validateRequest(request: Request): Boolean =
    with(request) {
        email.isNotEmpty() && firstName.isNotEmpty() && surname.isNotEmpty()
    }


val db = Database()
class Database {

    // vv Change my return type vv
    fun updateDbFromRequest(request: Request): Boolean {
        // not a nice DB, we only persist even Request ids!
        return if (request.id % 2 == 0)
            true
        else
            false
    }
}
class DatabaseException(override val message: String?) : RuntimeException(message)

val smtpServer= SMTPServer()
class SMTPServer {
    // vv Change my return type vv
    fun sendEmail(email: String): Boolean =
        if ("\\w{1,}\\.\\w{1,}@railway.com$".toRegex().matches(email))
            true // email sent
        else
            false // email not sent :( not in correct format
}

val log = Log()
class Log {
    fun error(s: String) = System.err.println(s)
}






