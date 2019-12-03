package eitherusecase

import Either
import Employee
import Request
import UserData

sealed class DomainError
object InvalidRequest : DomainError()
object DbErrorCannotAddUser : DomainError()
object DbIsAPotatoError : DomainError()
object DbErrorCannotCreateEmployee : DomainError()
object CouldNotSendEmail : DomainError()

// vv Change my return type to Either<DomainError,Employee> vv
internal fun executeEitherUseCase(request: Request): Either<DomainError, Employee> =
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
private fun validateRequest(req: Request): Either<DomainError, Request> {
    val valid = with(req) {
        formData.firstName.isNotEmpty() && formData.surname.isNotEmpty()
    }
    return if (valid) Either.Right(req) else Either.Left(InvalidRequest)
}

private class Database {
    // vv Change my return type vv
    fun recordNewUserRequest(userData: UserData): Either<DomainError, UserData> =
        // not a nice DB, we only persist even Request ids!

        when {
            (1..10).contains(userData.surname.length) -> Either.Right(userData)
            (11..15).contains(userData.surname.length) -> Either.Left(DbIsAPotatoError)
            else -> Either.Left(DbErrorCannotAddUser)
        }

    // vv Change my return type vv
    fun createEmployee(userData: UserData): Either<DomainError, Employee> {
        val id = userData.surname.length + userData.firstName.length
        return if (id % 5 == 0) Either.Left(DbErrorCannotCreateEmployee)
        else Either.Right(Employee(userData.email ?: "", userData.firstName, userData.surname, id))
    }
}

private val db = Database()

private val smtpServer = SMTPServer()

class SMTPServer {
    // vv Change my return type vv
    fun sendEmail(e: Employee): Either<DomainError, Employee> =
        if ("\\w{1,}\\.\\w{1,}@railway.com$".toRegex().matches(e.email))
            Either.Right(e) // email sent
        else
            Either.Left(CouldNotSendEmail) // email not sent :( not in correct format
}
