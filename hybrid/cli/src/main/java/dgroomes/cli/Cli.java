package dgroomes.cli;

import dgroomes.graphql.GraphqlUtil;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A toy GraphQL Java program that provides information about the Java runtime environment. It uses a hybrid approach
 * for creating the schema by combining an SDL file with dynamic values from programmatic Java code.
 *
 * This class is the runner.
 */
public class Cli {

    private static final Logger log = LoggerFactory.getLogger(Cli.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Expected exactly one argument but found {}", args.length);
            System.exit(1);
        }

        var graphqlQuery = args[0];

        GraphQLSchema schema = JavaInfoGraphqlSchema.schema();
        GraphQL graphQl = GraphQL.newGraphQL(schema).build();
        ExecutionResult executionResult = graphQl.execute(graphqlQuery);

        GraphqlUtil.printResult(executionResult);
    }
}
