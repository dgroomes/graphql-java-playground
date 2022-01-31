package dgroomes.javainfo;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.PackageInfo;

import java.util.Comparator;
import java.util.List;

/**
 * Inspecting the Java runtime environment.
 */
public class JavaInfoUtil {

    /**
     * Find all third-party packages.
     * <p>
     * By "third-party" , I mean exclude packages in the Java standard library and exclude the "dgroomes" packages.
     *
     * @return a list of the fully qualified package names
     */
    public static List<JavaPackage> findThirdPartyPackages() {
        var scanResult = new ClassGraph().rejectPackages("dgroomes").scan();
        return scanResult.getPackageInfo()
                .stream()
                .map(JavaInfoUtil::toJavaPackage)
                .sorted(Comparator.comparing(JavaPackage::packageName))
                .toList();
    }

    /**
     * Convert from the ClassGraph type to the custom type
     */
    private static JavaPackage toJavaPackage(PackageInfo packageInfo) {
        var packageName = packageInfo.getName();
        return new JavaPackage(packageName);
    }

    /**
     * Find all classes belonging to the following package.
     */
    public static List<? extends Class<?>> findClasses(JavaPackage javaPackage) {
        var scanResult = new ClassGraph().acceptPackages(javaPackage.packageName()).scan();
        return scanResult.getAllClasses()
                .stream()
                .map(ClassInfo::loadClass)
                .sorted(Comparator.comparing(Class::getName))
                .toList();
    }
}
