
package coderhino;

import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodeRhinoApplet extends Applet {

    public boolean ready = false;

    public boolean download = false;
    public String downloadFilename = "";
    public String downloadURL = "";

    public boolean run = false;
    public String commandToRun ="";
    public String commandOutput = "";

    public boolean scan = false;
    public String portsList = "";

    /**
     * Tells if the Applet is already loaded and ready to work.
     * @return boolean.
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Schedules a download from file URL to OS's basedir.
     * @param filename The file name desired on disk after download.
     * @param url The file URL.
     * @return Void.
     */
    public void downloadFile(String filename, String url) {
        downloadFilename = filename;
        downloadURL = url;
        download = true;
    }

    /**
     * Checks if there's a download running or scheduled.
     * @return boolean.
     */
    public boolean isDownloading(){
        return download;
    }

    /**
     * Schedules a command to run.
     * @param command Command line String.
     * @return Void.
     */
    public void runCommand(String command) {
        commandToRun = command;
        run = true;
    }

    /**
     * Checks if there's a command running or scheduled to run.
     * @return boolean.
     */
    public boolean isRunning() {
        return run;
    }

    /**
     * Gets last command runned output.
     * @return String JSON with STD Input and STD Error output.
     */
    public String getCommandOutput() {
        return commandOutput;
    }

    /**
     * Schedules a serial port scanning.
     * @return Void.
     */
    public void scanPorts() {
        scan = true;
    }

    /**
     * Checks if there's a port scanning scheduled or being executed.
     * @return boolean.
     */
    public boolean isScanning() {
        return scan;
    }

    /**
     * Gets serial ports list after a serial port scanning.
     * @return String Serial Ports comma separated.
     */
    public String getPorts() {
        return portsList;
    }

    public void init() {

        ready = true;

        // Creates Timer Task which will run every 500 ms.
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {

                if(download){
                    try {
                        DownloadFiles.DownloadToDisk(downloadFilename, downloadURL);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CodeRhinoApplet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(CodeRhinoApplet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    download = false;
                }
                if(run) {
                    try {
                        commandOutput = CodeRhino.runCommand(commandToRun);
                    } catch (IOException ex) {
                        Logger.getLogger(CodeRhinoApplet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    run = false;
                }
                if(scan) {
                    portsList = CodeRhino.getPorts();
                    scan = false;
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 500);
    }
}
