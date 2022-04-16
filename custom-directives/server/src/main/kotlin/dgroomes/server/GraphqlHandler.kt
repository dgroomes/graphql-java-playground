package dgroomes.server

import com.fasterxml.jackson.databind.node.ObjectNode
import dgroomes.server.GraphqlHandler.Companion.SupportedContentType.GRAPHQL
import dgroomes.server.GraphqlHandler.Companion.SupportedContentType.JSON
import graphql.GraphQL
import org.http4k.core.*
import org.http4k.format.Jackson.asJsonObject

/**
 * This is a {@link HttpHandler} for GraphQL requests.
 *
 * This handler responds to requests based on the official "Serving over HTTP" guidance in https://graphql.org/learn/serving-over-http/
 *
 * For example, it responds to GraphQL queries that come in as POST requests of type "Content-Type: application/graphql"
 * with the URL path "/graphql".
 *
 * I should probably consider using the official http4k GraphQL module ("http4k-graphql") but I want to write this by
 * hand to learn http4k, better grok GraphQL over HTTP, and brush up on Kotlin!
 */
class GraphqlHandler(private val graphql: GraphQL) : HttpHandler {

    companion object {
        enum class SupportedContentType { GRAPHQL, JSON }
    }

    override fun invoke(request: Request): Response {
        if (request.method != Method.POST) {
            return Response(Status.METHOD_NOT_ALLOWED).body(Status.METHOD_NOT_ALLOWED.toString())
        }

        // Default to "application/json" if no Content-Type header is present. Insomnia, for example, does not include
        // this header when sending GraphQL requests. I'm sure many tools omit the header.
        val contentType: String = request.header("content-type") ?: "application/json"

        val typedContentType: SupportedContentType = when (contentType.lowercase()) {
            "application/graphql" -> GRAPHQL
            "application/json" -> JSON
            else -> return Response(Status.BAD_REQUEST).body("The only supported 'Content-Type' values are 'application/graphql' and 'application/json' but found '${contentType}'")
        }

        val stringBody = request.bodyString()
        val graphqlQuery: String = when (typedContentType) {
            GRAPHQL -> stringBody
            JSON -> stringBody.asJsonObject().get("query").textValue()
        }

        val result = graphql.execute(graphqlQuery)
        val resultNode = result.asJsonObject() as ObjectNode

        // According to the official GraphQL HTTP guidance, the "errors" field should only be included if errors exist.
        if (result.errors.isEmpty()) {
            resultNode.retain("data")
        } else {
            resultNode.retain("data", "errors")
        }

        val resultJson: String = resultNode.toPrettyString()

        return Response(Status.OK).body(resultJson)
    }
}
