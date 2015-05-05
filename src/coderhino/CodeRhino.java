package coderhino;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;

public class CodeRhino {

    /**
     * Gets system temporary files base directory.
     * @return String System's basedir.
     */
    public static String getBaseDir() {

        String basedir = (String) AccessController.doPrivileged(
                (PrivilegedAction) () -> System.getProperty("java.io.tmpdir")
        );

        return basedir;

    }

    /**
     * Gets operating system's name.
     * OS's name's in ['windows', 'linux', 'mac', 'not-suported'].
     * @return String OS's name or 'not-supported'.
     */
    public static String getOS(){

        String os;
        os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")){
            return "windows";
        } else if(os.contains("linux")) {
            return "linux";
        } else if(os.contains("mac")) {
            return "mac";
        } else {
            return "not-supported";
        }
    }

    /**
     * Gets available serial ports' list.
     * @return String Serial ports' list.
     */
    public static String getPorts() {

        String ports = (String) AccessController.doPrivileged(
                (PrivilegedAction) () -> SerialPortList.getPortNames()
        );

        return ports;
    }

    /**
     * Runs command line on user's operating system.
     * @param command Command line String.
     * @return String JSON with STD Input and STD Error output.
     * @throws java.io.IOException
     */
    public static String runCommand(String command) throws IOException {
        HashMap<String, String> dictionary = RunCommand.RunCommand(command);
        String stdInput = dictionary.get("stdInput");
        String stdError = dictionary.get("stdError");
        String output = "";

        output = output + "{\"stdInput\": \"" + stdInput + "\",";
        output = output + "\"stdError\": \"" + stdError + "\"}";

        return output;
    }
}
