package com.andrewjackman.elmmakeissue.webserver

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.http4k.core.Method.*
import org.http4k.core.then
import org.http4k.filter.AllowAllOriginPolicy
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters.Cors
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main(args: Array<String>) {
    RunServer().main(args)
}

class RunServer : CliktCommand() {
    private val webserverPort by option("--webserverPort", help = "The port where the Web Server is running.").int().default(8080)
    private val devMode by option("--dev", help = "Run the server in local dev mode, without auth.").flag()

    override fun run() {
        val cors = Cors(CorsPolicy(AllowAllOriginPolicy, listOf("Content-Type"), listOf(GET, PUT, POST, DELETE, PATCH, OPTIONS)))
        val httpApi = routes(
            "/" bind static(ResourceLoader.Classpath("/public"))
        )

        val http = if (devMode) httpApi else Authed(httpApi)
        val server = cors.then(http).asServer(Jetty(webserverPort)).start()

        addShutdownHook(server)
    }
}

private fun addShutdownHook(server: Http4kServer) {
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Web Server on port ${server.port()} shutting down...")
        server.stop()
        println("Web Server stopped.")
    })
    Thread.currentThread().join()
}