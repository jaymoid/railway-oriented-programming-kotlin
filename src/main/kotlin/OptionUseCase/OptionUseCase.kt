package OptionUseCase

import DatabaseException
import Request
import RequestEnvelope
import RequestException
import log
import validRequests

typealias UniqueId = Int

// vv Change my return type to Optional<UniqueId> vv
internal fun executeOptionalUseCase(requestId: Int): UniqueId {

    var requestEnvelope: RequestEnvelope? = receiveRequest(requestId)
    if (requestEnvelope == null) {
        throw RequestException("Cannot find user with that request id")
    }

    val request = requestEnvelope.request

    var isValidated: Boolean = validateRequest(request)
    if (!isValidated) {
        throw RequestException("Request is not valid")
    }

    canonicalizeEmail(request)

    var uuid: Int
    try {
        var result: Boolean = db.updateDbFromRequest(request)
        if (!result) {
            throw RequestException("Customer record not found")
        }
        uuid = db.retrieveUniqueId(request)
    } catch (e: DatabaseException) {
        throw RequestException("DB error: Customer record not updated")
    }

    if (!smtpServer.sendEmail(request.email)) {
        log.error("Customer email not sent")
    }

    // If we get to here I think it is ok?! *prays*
    return uuid;
}

// vv Change my return type vv
private fun receiveRequest(requestId: Int): RequestEnvelope? =
    validRequests.find { it.request.id == requestId }

// vv Change my return type vv
private fun canonicalizeEmail(request: Request): Unit {
    request.email = "${request.firstName}.${request.surname}@railway.com"
}

// vv Change my return type vv
private fun validateRequest(request: Request): Boolean =
    with(request) {
        email.isNotEmpty() && firstName.isNotEmpty() && surname.isNotEmpty()
    }

private val db = Database()

internal class Database {

    // vv Change my return type vv
    fun updateDbFromRequest(request: Request): Boolean {
        // not a nice DB, we only persist even Request ids!
        return if (request.id % 2 == 0)
            true
        else
            false
    }

    fun retrieveUniqueId(request: Request): UniqueId {
        val hc = request.hashCode()
        if (hc % 5 == 0) throw DatabaseException("Db Cannot get unique Code")
        return hc
    }
}

private val smtpServer = SMTPServer()

private class SMTPServer {
    // vv Change my return type vv
    fun sendEmail(email: String): Boolean =
        if ("\\w{1,}\\.\\w{1,}@railway.com$".toRegex().matches(email))
            true // email sent
        else
            false // email not sent :( not in correct format
}
