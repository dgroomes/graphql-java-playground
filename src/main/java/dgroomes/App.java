package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Received the following arguments: {}", String.join(", ", args));
        log.error("Not yet implemented!");
    }
}
