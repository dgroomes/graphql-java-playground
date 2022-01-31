package dgroomes;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import static dgroomes.GraphqlUtil.getNonNullArg;

public class EchoDataFetcher implements DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment env) {
        String message = getNonNullArg(env, "message");
        EchoFlavor echoFlavor = getNonNullArg(env, "echoFlavor");

        return switch (echoFlavor) {
            case NORMAL -> message;
            case LOUD -> message.toUpperCase();
        };
    }
}
