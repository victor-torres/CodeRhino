package coderhino;

import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

public class CodeRhinoApplet extends Applet {

    public boolean ready = false;
    public String osName = "";

    public boolean download = false;
    public String downloadFilename = "";
    public String downloadURL = "";
    public String downloadCallback = null;

    public boolean run = false;
    public String commandToRun ="";
    public String commandOutput = "";
    public String runCallback = null;

    public boolean scan = false;
    public String portsList = "";
    public String scanCallback = null;

    /**
     * Tells if the Applet is already loaded and ready to work.
     * @return boolean.
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Callbacks JavaScript function.
     * @param callback JavaScript callback function name.
     */
    public void callback(String callback) {
        JSObject win = JSObject.getWindow(this);
        win.call(callback, new Object[] {});
    }

    /**
     * Schedules a download from file URL to OS's basedir.
     * @param filename The file name desired on disk after download.
     * @param url The file URL.
     */
    public void downloadFile(String filename, String url) {
        downloadFilename = filename;
        downloadURL = url;
        download = true;
        downloadCallback = null;
    }

    /**
     * Schedules a download from file URL to OS's basedir with callback.
     * @param filename The file name desired on disk after download.
     * @param url The file URL.
     * @param callback JavaScript callback function name.
     */
    public void downloadFile(String filename, String url, String callback) {
        downloadFile(filename, url);
        downloadCallback = callback;
    }

    /**
     * Checks if there's a download running or scheduled.
     * @return boolean.
     */
    public boolean isDownloading(){
        return download;
    }

    /**
     * Gets last download's filename.
     * @return String Last download's filename.
     */
    public String getFilename(){
        return downloadFilename;
    }

    /**
     * Schedules a command to run.
     * @param command Command line to execute.
     */
    public void runCommand(String command) {
        commandToRun = command;
        run = true;
        runCallback = null;
    }

    /**
     * Schedules a command to run with callback.
     * @param command Command line to execute.
     * @param callback JavaScript callback function name.
     */
    public void runCommand(String command, String callback) {
        runCommand(command);
        runCallback = callback;
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
     */
    public void scanPorts() {
        scan = true;
        scanCallback = null;
    }

    /**
     * Schedules a serial port scanning with callback.
     * @param callback JavaScript callback function name.
     */
    public void scanPorts(String callback) {
        scanPorts();
        scanCallback = callback;
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

    /**
     * Gets Operating System's name.
     * @return String Operating System's name or 'not-supported'.
     */
    public String getOS() {
        return osName;
    }

    @Override
    public void init() {

        ready = true;
        osName = CodeRhino.getOS();

        // Creates Timer Task which will run every 500 ms.
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                if(download){
                    try {
                        DownloadFiles.DownloadToDisk(
                                downloadFilename,
                                downloadURL
                        );
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(
                                CodeRhinoApplet.class.getName()
                        ).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(
                                CodeRhinoApplet.class.getName()
                        ).log(Level.SEVERE, null, ex);
                    }
                    download = false;
                    if(downloadCallback != null) {
                        callback(downloadCallback);
                        downloadCallback = null;
                    }
                }
                if(run) {
                    try {
                        commandOutput = CodeRhino.runCommand(commandToRun);
                    } catch (IOException ex) {
                        Logger.getLogger(
                                CodeRhinoApplet.class.getName()
                        ).log(Level.SEVERE, null, ex);
                    }
                    run = false;
                    if(runCallback != null) {
                        callback(runCallback);
                        runCallback = null;
                    }
                }
                if(scan) {
                    portsList = CodeRhino.getPorts();
                    scan = false;
                    if(scanCallback != null) {
                        callback(scanCallback);
                        scanCallback = null;
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 500);
    }
}
