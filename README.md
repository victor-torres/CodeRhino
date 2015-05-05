# CodeRhino
Java Applet for running backend executables through JavaScript and webpages.

## Embedding CodeRhino in your site
Add these lines to the HTML of your site:
```
<applet id="CodeRhino" archive="CodeRhino.jar" code="coderhino.CodeRhinoApplet"
  MAYSCRIPT width="0" height="0"></applet>
```

## Trying with JavaScript console
### Check if the Applet is loaded and ready to run
If the Applet is fully loaded and properly running the function below should return `true`.
```
CodeRhino.isReady()
```

### Get a list of available serial ports
Start or schedule a serial port scanning.
```
CodeRhino.scanPorts()
```
Check if the scan is being executed yet. If it's done, the following function should return `false`.
```
CodeRhino.isScanning()
```
Get the list of available serial ports in a JSON list format.
Example: `["/dev/tty1", "/dev/tty2"]`.
```
CodeRhino.getPorts()
```

### Download a remote file
Start or schedule a file download.
```
CodeRhino.downloadFile("google_logo.png", "http://upload.wikimedia.org/wikipedia/commons/3/30/Googlelogo.png")
```
Check if the download is done. If it's done, the following function should return `false`.
```
CodeRhino.isDownloading()
```

### Run a system command from browser
Try to run a list command. In Windows you could use `dir`. In Unix-based systems such as Mac OS X and GNU Linux you can just use `ls`.
```
CodeRhino.runCommand("ls")
```
Check if the command is still running. If it's done, the following function should return `false`.
```
CodeRhino.isRunning()
```
Check if the downloaded file is in your command output.
```
CodeRhino.getCommandOutput()
```
The command outputs are separated in Standard Input and Standard Error dictionary in a JSON String format.

`output = JSON.parse(CodeRhino.getCommandOutput())` could be used to parse the String into a JavaScript object so that you can access it as the following example:
```
output["stdInput"]
output["stdError"]
```
