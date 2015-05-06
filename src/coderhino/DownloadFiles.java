package coderhino;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFiles {

    /**
     * Downloads URL file to OS's basedir.
     * @param filename The file name desired on disk after download.
     * @param url The file URL.
     * @return Void.
     */
    public static void DownloadToDisk(String filename, String url) throws MalformedURLException, IOException{

        URL fileURL = new URL(url);
        String basedir = CodeRhino.getBaseDir();
        FileOutputStream fileOS;

        try (InputStream is = fileURL.openStream()) {
            fileOS = new FileOutputStream(basedir + filename);
            int bytes;
            while ((bytes = is.read()) != -1) {
                fileOS.write(bytes);
            }
        }

        fileOS.close();
    }
}
