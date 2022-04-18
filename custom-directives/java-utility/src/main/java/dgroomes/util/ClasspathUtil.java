package dgroomes.util;

import java.io.IOException;

/**
 * Generic utilities related to the classpath.
 */
public class ClasspathUtil {

  /**
   * Read the contents of a classpath resource into a string.
   *
   * @param name the name of the classpath resource
   * @return the contents of the resource
   * @throws IllegalStateException if something went wrong
   */
  public static String classpathResourceToString(String name) {
    try {
      return new String(ClasspathUtil.class.getResourceAsStream(name).readAllBytes());
    } catch (IOException e) {
      var msg = "Something went wrong while trying to read the contents of the classpath resource '%s'".formatted(name);
      throw new IllegalStateException(msg, e);
    }
  }
}
