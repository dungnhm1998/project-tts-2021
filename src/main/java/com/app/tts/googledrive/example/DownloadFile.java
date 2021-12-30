package com.app.tts.googledrive.example;

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
	public static boolean Download(String urlStr, String file) {
			URL url;
			try {
				url = new URL(urlStr);
				
				URLConnection urlConnection;
				try {
					urlConnection = new URL(urlStr).openConnection();
					urlConnection.addRequestProperty("User-Agent", "Microsoft Edge");
//			        urlConnection.setReadTimeout(5000);
//			        urlConnection.setConnectTimeout(5000);

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
						return true;
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
			return false;
	}
	
	
	public static void downloadFile(URL url, String outputFileName) throws IOException
    {
        try (InputStream in = url.openStream();
            ReadableByteChannel rbc = Channels.newChannel(in);
            FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
	
	public static void saveUrl(final String filename, final String urlString)
	        throws MalformedURLException, IOException {
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);

	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }
	}
}