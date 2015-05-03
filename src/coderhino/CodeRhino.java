
package coderhino;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

public class CodeRhino {

    /**
     * Gets system temporary files base directory.
     * @return String System's basedir.
     */
    public static String getBaseDir() {

        String basedir = (String) AccessController.doPrivileged(
          new PrivilegedAction() {
            public Object run() {
                return System.getProperty("java.io.tmpdir");
            }
          }
        );

        return basedir;

    }

    /**
     * Gets operating system name.
     * @return String OS's name or 'not-supported'.
     */
    public static String getOS(){

        String os;
        os = System.getProperty("os.name").toLowerCase();

        if(os.indexOf("win") >= 0){
            return "windows";
        } else if(os.indexOf("linux") >= 0) {
            return "linux";
        } else if(os.indexOf("mac") >= 0) {
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
          new PrivilegedAction() {
            public Object run() {
                return SerialPortList.getPortNames();
            }
          }
        );

        return ports;
    }

    /**
     * Runs command line on user's operating system.
     * @returns String JSON with STD Input and STD Error output.
     */
    public static String runCommand(String command) throws IOException {
        Map<String, String> dictionary = RunCommand.RunCommand(command);
        String stdInput = dictionary.get("stdInput");
        String stdError = dictionary.get("stdError");
        String output = "";

        output = output + "{\"stdInput\": \"" + stdInput + "\",";
        output = output + "\"stdError\": \"" + stdError + "\"}";

        return output;
    }
}
