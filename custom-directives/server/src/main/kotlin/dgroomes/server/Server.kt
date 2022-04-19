package dgroomes.server

import dgroomes.graphql.GraphqlWiring
import org.http4k.core.then
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters
import org.http4k.server.Http4kServer
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

/**
 * An http4k-based (and Netty-based) GraphQL server.
 */
object Server {

    private val log = LoggerFactory.getLogger(Server::class.java)
    private const val PORT = 8080

    @JvmStatic
    fun main(args: Array<String>) {
        val server: Http4kServer = run {
            val corsFilter = ServerFilters.Cors(CorsPolicy.UnsafeGlobalPermissive)
            val graphql = GraphqlWiring.build()
            val graphqlHandler = GraphqlHandler(graphql)
            corsFilter.then(graphqlHandler).asServer(Netty(PORT))
        }
        server.start()
        log.info("Started GraphQL server on http://127.0.0.1:{} using http4k and Netty", PORT)
    }
}
