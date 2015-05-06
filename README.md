# ![CodeRhino logo](https://cloud.githubusercontent.com/assets/3674112/7498819/006a7244-f3fa-11e4-9b75-9b4a7e7d5e5b.png)
Java Applet for running backend executables through JavaScript and webpages.

## Embedding CodeRhino in your site
Add these lines to the HTML of your site:
```HTML
<applet id="CodeRhino" archive="CodeRhino.jar" code="coderhino.CodeRhinoApplet"
  MAYSCRIPT width="0" height="0"></applet>
```

## Trying with JavaScript console

The basic operation of CodeRhino is:

1. Schedule task
2. Verify task status
3. Get task results

### Check if the Applet is loaded and ready to run
If the Applet is fully loaded and properly running the function below should return `true`.
```JavaScript
CodeRhino.isReady();
```

### Get a list of available serial ports
Start or schedule a serial port scanning.
```JavaScript
CodeRhino.scanPorts();
```
Check if the scan is being executed yet. If it's done, the following function should return `false`.
```JavaScript
CodeRhino.isScanning();
```
Get the list of available serial ports in a JSON list format.
Example: `["/dev/tty1", "/dev/tty2"]`.
```JavaScript
CodeRhino.getPorts();
```

### Download a remote file
Start or schedule a file download.
```JavaScript
CodeRhino.downloadFile("google_logo.png", "http://upload.wikimedia.org/wikipedia/commons/3/30/Googlelogo.png");
```
Check if the download is done. If it's done, the following function should return `false`.
```JavaScript
CodeRhino.isDownloading();
```

### Run a system command from browser
Try to run a list command. In Windows you could use `dir`. In Unix-based systems such as Mac OS X and GNU Linux you can just use `ls`.
```JavaScript
CodeRhino.runCommand("ls basedir/");
```
`basedir/` is replaced by the directory where downloads are made.


Check if the command is still running. If it's done, the following function should return `false`.
```JavaScript
CodeRhino.isRunning();
```
Check if the downloaded file is in your command output.
```JavaScript
CodeRhino.getCommandOutput();
```
The command outputs are separated in Standard Input and Standard Error dictionary in a JSON String format.

`output = JSON.parse(CodeRhino.getCommandOutput());` could be used to parse the String into a JavaScript object so that you can access it as the following example:
```JavaScript
output["stdInput"];
output["stdError"];
```

## JavaScript callback function

You can also use JavaScript callback functions with scanPorts(), downloadFile() and runCommand() methods.

### scanPort() with callback

```JavaScript
function serialPortScanCallback() {
  var serialPorts = JSON.parse(CodeRhino.getPorts());
  console.log('Serial ports: ' + serialPorts.join(", "));
}

CodeRhino.scanPorts('serialPortScanCallback');
```

### downloadFile() with callback

```JavaScript
function downloadCallback() {
  var filename = CodeRhino.getFilename();
  console.log('File "' + filename + '" has been downloaded.');
}

CodeRhino.downloadFile(
  'document.pdf', 
  'http://www.company.com/document_link.pdf', 
  'downloadCallback'
);
```

### runCommand() with callback

```JavaScript
function commandCallback() {
  var output = JSON.parse(CodeRhino.getCommandOutput());
  console.log('stdInput:  ' + output['stdInput']);
  console.log('stdError:  ' + output['stdError']);
}

CodeRhino.runCommand('ls basedir/', 'commandCallback');
```

`basedir/` is replaced by the directory where downloads are made.

### Important note

Do not forget to feedback your application's users about the actions that are being executed.
For example, if a user clicks a `download` button, disable that button and show him a message like `download has been started`.
A task cannot be started if there's another task of the same kind being executed or scheduled.
When the download is finished, show him another message like `download has been completed` and reactivate the button.

## Development

### Build JAR

We currently use NetBeans to build our JAR files. The repository itself is a NetBeans project, so you can download it and open in the application. Once open, click `Build` or `Clear and Build` to generate new JAR files.

You can find an already build JAR in `dist/CodeRhino.jar`.

### Sign JAR

You should buy a Java Applet certificate with trusted dealers if you want to use the library in production mode with lot of public access. If you want to use it just for personal use or private intranets or web applications, it's just fine to self-sign JAR files.

The file `dist/CodeRhino.jar` comes self-signed by default.

#### Create the keystore

```shell
$ keytool -genkey -keystore mykeystore -alias myalias
````

#### Create the certificate

```shell
$ keytool -selfcert -keystore mykeystore -alias myalias
````

#### Sign JAR file

```shell
$ jarsigner -keystore mykeystore CodeRhino.jar myalias
```

## Special Thanks

- Lucas Limeira ([@lucasalveslm](https://github.com/lucasalveslm)) - Support and testing
- Emerson Yalli ([@emersonyalli](https://github.com/EmersonYalli)) - CodeRhino logo

## More information

For more information and help, visit our [Wiki](http://github.com/victor-torres/CodeRhino/wiki/).

