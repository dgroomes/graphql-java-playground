package dgroomes.javainfo;

/**
 * This sealed type hierarchy represents a limited glossary of components of the Java runtime environment.
 */
public sealed interface JavaComponent permits JavaStaticComponent, JavaPackage {
}
