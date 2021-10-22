package dgroomes;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import static dgroomes.Util.getNonNullArg;

public class EchoDataFetcher implements DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment env) {
        String message = getNonNullArg(env, "message");
        String echoFlavor = getNonNullArg(env, "echoFlavor");

        return switch (echoFlavor) {
            case "NORMAL" -> message;
            case "LOUD" -> message.toUpperCase();
            case "EXTRA" -> (message + "...").repeat(3);
            default -> throw new IllegalArgumentException("Unexpected value for 'echoFlavor'. Found: %s".formatted(echoFlavor));
        };
    }
}
