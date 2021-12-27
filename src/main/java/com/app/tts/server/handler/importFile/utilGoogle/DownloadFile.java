package com.app.tts.server.handler.importFile.utilGoogle;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadFile {
    public static void Download(String urlStr, String file) {
        URL url;
        try {
            url = new URL(urlStr);

            URLConnection urlConnection;
            try {
                urlConnection = new URL(urlStr).openConnection();
                urlConnection.addRequestProperty("User-Agent", "Mozilla");
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);

                BufferedInputStream bis;
                try {
                    bis = new BufferedInputStream(url.openStream());

                    FileOutputStream fis = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = bis.read(buffer, 0, 1024)) != -1) {
                        fis.write(buffer, 0, count);
                    }
                    fis.close();
                    bis.close();
                    System.out.println("Download success");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void downloadFile(URL url, String outputFileName) throws IOException
    {
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}
