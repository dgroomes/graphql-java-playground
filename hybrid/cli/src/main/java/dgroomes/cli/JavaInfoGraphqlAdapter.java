package dgroomes.cli;

import dgroomes.javainfo.JavaComponent;
import dgroomes.javainfo.JavaInfoUtil;
import dgroomes.javainfo.JavaPackage;
import dgroomes.javainfo.JavaStaticComponent;

import java.util.Map;
import java.util.TreeMap;

/**
 * Adapting the capabilities of the 'java-info' module into a GraphQL environment.
 */
public class JavaInfoGraphqlAdapter {

    /**
     * Create the "backing map", or "runtime wiring data structure", that backs the GraphQL schema enum type
     * "JavaComponent". I didn't explain that well, but just remember the duality of GraphQL schemas vs the Java runtime
     * code that backs it. The GraphQL Java library calls this the "runtime wiring".
     *
     * @return a map of {@link JavaComponent} instances keyed by the GraphQL enum name
     */
    public static Map<String, JavaComponent> javaComponentsById() {
        var map = new TreeMap<String, JavaComponent>();
        map.put("VERSION", JavaStaticComponent.VERSION);
        map.put("RUNTIME_MODE", JavaStaticComponent.RUNTIME_MODE);

        for (JavaPackage thirdPartyPackage : JavaInfoUtil.findThirdPartyPackages()) {
            map.put(normalizeNameForGraphql(thirdPartyPackage), thirdPartyPackage);
        }

        return map;
    }

    /**
     * Normalize a Java package name so that it is legal as a GraphQL enum name.
     * <p>
     * Specifically, uppercase it, turn periods into underscores, and pref with "PACKAGE_". For example, turn
     * "org.slf4j" into "PACKAGE_ORG_SLF4J"
     *
     * @param javaPackage the {@link JavaPackage}
     * @return a normalized name
     */
    public static String normalizeNameForGraphql(JavaPackage javaPackage) {
        return "PACKAGE_" + javaPackage.packageName().toUpperCase().replace(".", "_");
    }
}
