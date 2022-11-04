package ua.nure.danielost.shuttler.utility;

import java.io.IOException;

public final class DatabaseUtil {

    public static boolean backup(
            String dbUsername,
            String dbPassword,
            String dbName,
            String outputFile
    ) throws IOException, InterruptedException {
        String command = String.format("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);
        Process process = Runtime.getRuntime().exec(command);
        int processComplete = process.waitFor();
        return processComplete == 0;
    }

    public static boolean restore(
            String dbUsername,
            String dbPassword,
            String dbName,
            String sourceFile
    ) throws IOException, InterruptedException {
        String[] command = new String[]{
                "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql",
                "-u" + dbUsername,
                "-p" + dbPassword,
                "-e",
                " source " + sourceFile,
                dbName
        };
        Process runtimeProcess = Runtime.getRuntime().exec(command);
        int processComplete = runtimeProcess.waitFor();
        return processComplete == 0;
    }
}
