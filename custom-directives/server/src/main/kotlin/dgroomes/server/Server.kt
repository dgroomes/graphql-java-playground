package dgroomes.server

import graphql.GraphQL
import org.http4k.server.Http4kServer
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * An http4k-based (and Netty-based) GraphQL server.
 */
object Server {

    private val log: Logger = LoggerFactory.getLogger(Server::class.java)
    private const val PORT = 8080

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 2) {
            throw IllegalArgumentException("Expected to find two arguments (the location of the GraphQL schema files) but found ${args.size}.")
        }

        val graphql: GraphQL = GraphqlWiring.build(args[0], args[1])
        val graphqlHandler = GraphqlHandler(graphql)
        val server: Http4kServer = graphqlHandler.asServer(Netty(PORT))
        server.start()
        log.info("Started GraphQL server on http://127.0.0.1:${PORT} using http4k and Netty")
    }
}
