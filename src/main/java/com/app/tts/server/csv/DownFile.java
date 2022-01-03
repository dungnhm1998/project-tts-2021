package com.app.tts.server.csv;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownFile {
	public static boolean Download(String urlStr, String file) {
	      URL url;
	      try {
	        url = new URL(urlStr);
	        
	        URLConnection urlConnection;
	        try {
	          urlConnection = new URL(urlStr).openConnection();
	          urlConnection.addRequestProperty("User-Agent", "Microsoft Edge");
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
}
