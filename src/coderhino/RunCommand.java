package coderhino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class RunCommand {

    /**
     * Runs a command in user's operating system.
     * @param command Command line String.
     * @return HashMap<String, String> with STD Input and Error command's output.
     * @throws java.io.IOException
     */
    public static HashMap<String, String> RunCommand(String command) throws IOException {

        // Replaces "basedir/" text occurrences by the OS's basedir.
        String basedir = CodeRhino.getBaseDir();
        command = command.replace("basedir/", basedir);

        Process proc;
        String line;
        String stdIn = "";
        String stdEr = "";
        HashMap<String, String> output = new HashMap<>();

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
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }

        return output;
    }
}
