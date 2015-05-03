package coderhino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RunCommand {

    /**
     * Runs a command in user's operating system.
     * @param command Command line String.
     * @return Map<String, String> with STD Input and Error command's output.
     */
    public static HashMap<String, String> RunCommand(String command) throws IOException {

        // Replaces "basedir/" text occurrences by the OS's basedir.
        String basedir = CodeRhino.getBaseDir();
        command = command.replace("basedir/", basedir);

        Process proc;
        String line;
        String stdIn = "";
        String stdEr = "";
        HashMap<String, String> output = new HashMap<String, String>();

        try {
            proc = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(proc.getErrorStream()));
            while ((line = stdInput.readLine()) != null) {
                stdIn += line + "\n";
            }
            while ((line = stdError.readLine()) != null) {
                stdEr += line + "\n";
            }
            output.put("stdInput", stdIn);
            output.put("stdError", stdEr);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return output;
    }
}
