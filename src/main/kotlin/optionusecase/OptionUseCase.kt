package OptionUseCase

import DatabaseException
import Employee
import Request
import RequestException
import UserData
import log

typealias UniqueId = Int

// vv Change my return type to Option<Employee> vv
internal fun executeOptionalUseCase(request: Request): Employee {

    var isValidated: Boolean = validateRequest(request)
    if (!isValidated) {
        throw RequestException("Request is not valid")
    }

    var userData = request.formData

    userData = canonicalizeEmail(userData)

    var employee: Employee =
        try {
            var result: Boolean = Database.recordNewUserRequest(userData)
            if (!result) {
                throw RequestException("Unable to register new user info")
            }
            Database.createEmployee(userData)

        } catch (e: DatabaseException) {
            throw RequestException("DB error: Unable to store new employee")
        }

    if (!SmtpServer.sendEmail(employee)) {
        log.error("Customer email not sent")
    }

    // If we get to here I think it is ok?! *prays*
    return employee
}

// vv Change my return type vv
private fun canonicalizeEmail(userData: UserData): UserData {
    return userData.copy(email = "${userData.firstName}.${userData.surname}@railway.com")
}

// vv Change my return type vv
private fun validateRequest(req: Request): Boolean =
    with(req) {
        formData.firstName.isNotEmpty() && formData.surname.isNotEmpty()
    }

object Database {
    // vv Change my return type vv
    fun recordNewUserRequest(userData: UserData): Boolean =
        // not a nice DB, we only persist even Request ids!
        when {
            userData.surname.length >= 5 -> true
            userData.surname.length < 10 -> false
            else -> throw DatabaseException(
                "Can't update user, there's every chance this DB might be a potato"
            )
        }

    // vv Change my return type vv
    fun createEmployee(userData: UserData): Employee {
        val id = userData.surname.length + userData.firstName.length
        if (id % 5 == 0) throw DatabaseException("Db Cannot get unique Code")
        return Employee(userData.email ?: "", userData.firstName, userData.surname, id)
    }
}

object SmtpServer {
    // vv Change my return type vv
    fun sendEmail(e: Employee): Boolean =
        if ("\\w{1,}\\.\\w{1,}@railway.com$".toRegex().matches(e.email))
            true // email sent
        else
            false // email not sent :( not in correct format
}
