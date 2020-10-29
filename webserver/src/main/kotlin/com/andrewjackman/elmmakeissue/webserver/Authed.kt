package com.andrewjackman.elmmakeissue.webserver

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.RoutingHttpHandler

val CREDENTIALS = EnvironmentKey.map(String::toCredentials, Credentials::fromCredentials)
    .defaulted("CREDENTIALS", Credentials("admin", "admin"))

private fun Credentials.fromCredentials() = "$user:$password"
private fun String.toCredentials() = split(":").run { Credentials(get(0), get(1)) }

fun Authed(handler: RoutingHttpHandler): HttpHandler {
    val auth = ServerFilters.BasicAuth("Test", Environment.ENV[CREDENTIALS])
    return auth.then(handler)
}
