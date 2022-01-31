package dgroomes.javainfo;

/**
 * Instances of this record describe a Java package. For example: "org.slf4j"
 */
public record JavaPackage(String packageName) implements JavaComponent {
}
