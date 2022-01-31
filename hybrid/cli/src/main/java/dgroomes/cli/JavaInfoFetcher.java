package dgroomes.cli;

import dgroomes.javainfo.JavaComponent;
import dgroomes.javainfo.JavaInfoUtil;
import dgroomes.javainfo.JavaPackage;
import dgroomes.javainfo.JavaStaticComponent;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.stream.Collectors;

import static dgroomes.graphql.GraphqlUtil.getNonNullArg;

public class JavaInfoFetcher implements DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment env) {
        Object component = getNonNullArg(env, "component");

        if (!(component instanceof JavaComponent javaComponent)) {
            throw new IllegalArgumentException("Unexpected value. Expected an instance of JavaComponent but found + %s".formatted(component));
        }

        return switch (javaComponent) {
            case JavaStaticComponent javaStaticComponent -> switch (javaStaticComponent) {
                case VERSION -> "The Java version is: %s".formatted(System.getProperty("java.version"));
                case RUNTIME_MODE -> {
                    String modulePath = System.getProperty("jdk.module.path");
                    if (modulePath == null) {
                        yield "The Java program is running in the traditional classpath mode. It is not modular.";
                    } else {
                        yield "The Java program is modularized.";
                    }
                }
            };

            case JavaPackage javaPackage -> {
                List<? extends Class<?>> found = JavaInfoUtil.findClasses(javaPackage);
                yield found.stream()
                        .map(Class::getName)
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        };
    }
}
