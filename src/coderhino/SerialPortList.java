package coderhino;

import jssc.SerialNativeInterface;
import org.json.simple.JSONArray;

import java.applet.Applet;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;


/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 3/3/12
 * Time: 12:01 PM
 *
 * Edited by NetBeans IDE.
 * User: victor-torres
 * Date: 4/4/13
 * Time: ?
 */

public final class SerialPortList extends Applet {

    private static final String DEVICES_PATH = "/dev";
    private static final SerialNativeInterface serialInterface = new SerialNativeInterface();

    /**
     * Lists available serial ports in OS.
     * @return String of available ports comma separated.
     */
    public static String getPortNames() {

        String[] portsArray = new String[0];
        String osName = CodeRhino.getOS();
        switch (osName) {
            case "windows":
                portsArray = getWindowsPortNames();
                break;
            case "linux":
                portsArray = getLinuxPortNames();
                break;
            case "mac":
                portsArray = getMacPortNames();
                break;
            case "not-supported":
                return "Your operating system is not supported currently.";
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(Arrays.asList(portsArray));

        return jsonArray.toJSONString();
    }

    /**
     * Lists available serial ports in Linux.
     * @return String[] Array of available ports.
     */
    public static String[] getLinuxPortNames() {

        final List<String> portsList = new ArrayList<>();
        portsList.addAll(seachSerialPorts("ttyACM"));
        portsList.addAll(seachSerialPorts("ttyUSB"));

        return (String[]) portsList.toArray(new String[portsList.size()]);

    }

    /**
     * Lists available serial ports in Windows.
     * @return String[] Array of available ports.
     */
    public static String[] getWindowsPortNames() {

        final List<String> ports = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            final int handle = serialInterface.openPort("COM" + i);
            if (handle > 0) {
                serialInterface.closePort(handle);
                ports.add("COM" + i);
            }
        }
        String[] portsString = new String[ports.size()];
        for (int i = 0; i < ports.size(); i++) {
            portsString[i] = ports.get(i);
        }

        return portsString;
    }

    /**
     * Lists available serial ports in Mac OS.
     * @return String[] Array of available ports.
     */
    public static String[] getMacPortNames() {

        final List<String> portsList = new ArrayList<>();
        portsList.addAll(seachSerialPorts("cu."));

        return (String[]) portsList.toArray(new String[portsList.size()]);
    }

    /**
     * Searchs for available serial ports in OS.
     * @returns String list of available ports.
     */
    private static List<String> seachSerialPorts(final String key) {

        final File dir = new File(DEVICES_PATH);
        List<String> portsList = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            final File[] files = dir.listFiles();
            if (files.length > 0) {
                final TreeSet<String> portsTree = new TreeSet<>();
                portsList = new ArrayList<>();
                for (File file : files) {
                    if (
                        !file.isDirectory() &&
                        !file.isFile() &&
                        file.getName().contains(key)
                    ) {
                        portsTree.add("/dev/" + file.getName());
                    }
                }
                for (String portName : portsTree) {
                    portsList.add(portName);
                }
            }
        }

        return portsList;
    }
}
