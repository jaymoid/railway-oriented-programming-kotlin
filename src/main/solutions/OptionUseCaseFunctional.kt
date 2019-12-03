package optionusecase

import Employee
import Option
import Request
import UserData

// vv Change my return type to Option<Employee> vv
fun executeOptionalUseCase(request: Request): Option<Employee> =
    validateRequest(request)
        .map(Request::formData)
        .map(::canonicalizeEmail)
        .flatMap(db::recordNewUserRequest)
        .flatMap(db::createEmployee)
        .flatTap(smtpServer::sendEmail)

// vv Change my return type vv
private fun canonicalizeEmail(userData: UserData): UserData {
    return userData.copy(email = "${userData.firstName}.${userData.surname}@railway.com")
}

// vv Change my return type vv
private fun validateRequest(req: Request): Option<Request> {
    val valid = with(req) {
        formData.firstName.isNotEmpty() && formData.surname.isNotEmpty()
    }
    return if (valid) Option.Some(req) else Option.None
}

private val db = Database()

private class Database {

    // vv Change my return type vv
    fun recordNewUserRequest(userData: UserData): Option<UserData> =
        // not a nice DB, we only persist even Request ids!
        when {
            (1..10).contains(userData.surname.length) -> Option.Some(userData)
            (11..15).contains(userData.surname.length) -> Option.None
            else -> Option.None
        }

    // vv Change my return type vv
    fun createEmployee(userData: UserData): Option<Employee> {
        val id = userData.surname.length + userData.firstName.length
        return if (id % 5 == 0)
            Option.None
        else
            Option.Some(Employee(userData.email ?: "", userData.firstName, userData.surname, id))
    }
}

private val smtpServer = SMTPServer()

private class SMTPServer {
    // vv Change my return type vv
    fun sendEmail(e: Employee): Option<Employee> =
        if ("\\w{1,}\\.\\w{1,}@railway.com$".toRegex().matches(e.email))
            Option.Some(e) // email sent
        else
            Option.None // email not sent :( not in correct format
}
