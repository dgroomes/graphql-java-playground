package dgroomes.server;

import dgroomes.graphql.GraphqlWiring;
import graphql.GraphQL;
import org.http4k.server.Http4kServer;
import org.http4k.server.Http4kServerKt;
import org.http4k.server.Netty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An http4k-based (and Netty-based) GraphQL server.
 */
public class Server {

  private final static Logger log = LoggerFactory.getLogger(Server.class);
  private final static int PORT = 8080;

  public static void main(String[] args) {

    Http4kServer server;
    {
      GraphQL graphql = GraphqlWiring.build();
      var graphqlHandler = new GraphqlHandler(graphql);
      server = Http4kServerKt.asServer(graphqlHandler, new Netty(PORT));
    }

    server.start();
    log.info("Started GraphQL server on http://127.0.0.1:{} using http4k and Netty", PORT);
  }
}
