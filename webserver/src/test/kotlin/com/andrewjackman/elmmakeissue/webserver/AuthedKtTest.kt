package com.andrewjackman.elmmakeissue.webserver

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.hamkrest.hasHeader
import org.http4k.hamkrest.hasStatus
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.junit.jupiter.api.Test
import java.util.*

internal class AuthedKtTest {
    val authedApi = Authed(routes("/authed" bind GET to { Response(OK) }))

    @Test
    fun `routes are inaccessible without basic auth`() {
        assertThat(
            authedApi(Request(GET, "/authed")),
            hasStatus(UNAUTHORIZED).and(hasHeader("WWW-Authenticate", "Basic Realm=\"Test\"")))
    }

    @Test
    fun `routes are accessible with basic auth`() {
        assertThat(
            authedApi(Request(GET, "/authed").header("Authorization", "Basic ${base64Encode("admin:admin")}")),
            hasStatus(OK))
    }

    private fun base64Encode(s: String): String {
        return Base64.getEncoder().encodeToString(s.toByteArray())
    }
}