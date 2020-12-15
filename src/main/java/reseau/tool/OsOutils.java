package reseau.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface OsOutils {
     String OS = System.getProperty("os.name").toLowerCase();

     static String execCommande(String exec,String argc,String commande) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(exec,argc, commande);

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
                output.append(line).append("\n");

            if (process.waitFor() == 0)
                return output.toString();
             else
                return null;

        } catch (IOException | InterruptedException e) {
            return null;
        }
     }

     static boolean isWindows() {
        return (OS.contains("win"));
    }

     static boolean isMac() {
        return (OS.contains("mac"));
    }

     static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
}
