package com.app.tts.server.handler.importFile.utilGoogle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class CreateGoogleApi {
    // PRIVATE!
    private static File _createGoogleFile(String googleFolderIdParent, String contentType, //
                                          String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {

        File fileMetadata = new File();
        fileMetadata.setName(customFileName);

        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        //
        Drive driveService = GoogleDriveUtils.getDriveService();

        File file = driveService.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();

        return file;
    }

    // Create Google File from byte[]
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, byte[] uploadData) throws IOException {
        //
        AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    // Create Google File from java.io.File
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, java.io.File uploadFile) throws IOException {

        //
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    // Create Google File from InputStream
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, InputStream inputStream) throws IOException {

        //
        AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    public static void main(String[] args) throws IOException {

        java.io.File uploadFile = new java.io.File("D://react//dung_test_import.csv");

        String nameFile = fileName(uploadFile);
        System.out.println(nameFile);
        // Create Google File:

        File googleFile = createGoogleFile("1o5VrvjzjSuEa-OZMWXplehBdz3vuG6A8", "text/plain", nameFile, uploadFile);

        System.out.println("Created Google file!");
        System.out.println("WebContentLink: " + googleFile.getWebContentLink());
//        System.out.println("WebViewLink: " + googleFile.getWebViewLink() );

        System.out.println("Done!");

        String link = googleFile.getWebContentLink();
        URL urlLink = new URL(link);
//		System.out.println(link);
        String outFile = "D://react//jarr" + nameFile;
//		DownloadFile.Download(link, outFile);
        DownloadFile.downloadFile(urlLink, outFile);
        System.out.println("DONE!");


    }

    public static String fileName(java.io.File uploadFile) {
        String name = FilenameUtils.getBaseName(uploadFile.toString());
        String type = FilenameUtils.getExtension(uploadFile.toString());
        String fileName = name + "." + type;
        return fileName;

    }
}
