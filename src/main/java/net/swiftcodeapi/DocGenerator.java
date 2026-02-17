package net.swiftcodeapi;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Auto-generates SwiftAPI_Docs.txt listing all public static methods
 * available in the Swift class, grouped by category.
 */
public final class DocGenerator {

    private static final String DOCS_FILE_NAME = "SwiftAPI_Docs.txt";

    private DocGenerator() {
    }

    public static void generate(SwiftCodeAPIPlugin plugin) {
        try {
            File docsFile = new File(plugin.getDataFolder(), DOCS_FILE_NAME);
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }

            Method[] methods = Swift.class.getDeclaredMethods();

            // Filter to public static methods only
            List<Method> publicMethods = Arrays.stream(methods)
                    .filter(m -> Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()))
                    .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                    .collect(Collectors.toList());

            // Group by return type category for readability
            Map<String, List<Method>> grouped = new LinkedHashMap<>();
            grouped.put("void (Actions)", publicMethods.stream()
                    .filter(m -> m.getReturnType() == void.class)
                    .collect(Collectors.toList()));
            grouped.put("Getters & Queries", publicMethods.stream()
                    .filter(m -> m.getReturnType() != void.class)
                    .collect(Collectors.toList()));

            try (PrintWriter writer = new PrintWriter(new FileWriter(docsFile))) {
                writer.println("╔══════════════════════════════════════════════════════════════╗");
                writer.println("║              SwiftCodeAPI - Method Reference                 ║");
                writer.println("║              Auto-Generated Documentation                    ║");
                writer.println("╚══════════════════════════════════════════════════════════════╝");
                writer.println();
                writer.println("Total Methods: " + publicMethods.size());
                writer.println("Usage: Swift.<methodName>(<args>)");
                writer.println();
                writer.println("================================================================");

                for (Map.Entry<String, List<Method>> entry : grouped.entrySet()) {
                    if (entry.getValue().isEmpty())
                        continue;

                    writer.println();
                    writer.println("── " + entry.getKey() + " (" + entry.getValue().size() + " methods) ──");
                    writer.println();

                    for (Method m : entry.getValue()) {
                        StringBuilder sig = new StringBuilder();
                        sig.append("  Swift.").append(m.getName()).append("(");

                        Parameter[] params = m.getParameters();
                        for (int i = 0; i < params.length; i++) {
                            if (i > 0)
                                sig.append(", ");
                            sig.append(params[i].getType().getSimpleName())
                                    .append(" ")
                                    .append(params[i].getName());
                        }
                        sig.append(")");

                        if (m.getReturnType() != void.class) {
                            sig.append(" → ").append(m.getReturnType().getSimpleName());
                        }

                        writer.println(sig.toString());
                    }
                }

                writer.println();
                writer.println("================================================================");
                writer.println("SwiftCodeAPI by TBnasa | www.swiftcode.net");
            }

            plugin.getLogger()
                    .info("Generated " + DOCS_FILE_NAME + " (" + publicMethods.size() + " methods documented)");

        } catch (Exception e) {
            plugin.getLogger().warning("Failed to generate API docs: " + e.getMessage());
        }
    }
}
