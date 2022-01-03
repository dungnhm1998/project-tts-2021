package com.app.tts.services;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DriveFile {
    @Autowired
    public Drive googleDrive;

    public  Map createGoogleFile(String googleFolderIdParent, String contentType,
                                        String customFileName, java.io.File uploadFile) throws IOException {

        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        File file =  _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);

        Map<String, String> result = new LinkedHashMap();
        String urlFile;
        String idFile;
        urlFile = file.getWebContentLink();
        idFile = file.getId();
        result.put("urlFile", urlFile);
        result.put("idFile", idFile);
        return result;
    }

    private  File _createGoogleFile(String googleFolderIdParent, String contentType, //
                                          String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {

        File fileMetadata = new File();
        fileMetadata.setName(customFileName);

        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);

        File file = googleDrive.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();

        return file;
    }


    //get all file
    public List<File> getAllGoogleDriveFiles() throws IOException {
        FileList result = googleDrive.files().list()
                .setFields("nextPageToken, files(id, name, parents, mimeType)")
                .execute();
        return result.getFiles();
    }

    // tao folder moi
    public Map createNewFolder(String folderName) throws IOException {
        Map<String, String> result = new LinkedHashMap();
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");//vnd.google-apps.shortcut");
        System.out.println("googleDrive = " + googleDrive);

        File file = googleDrive.files().create(fileMetadata).setFields("id, webViewLink").execute();
        result.put("idFolder", file.getId());
        result.put("webViewLinkFolder", file.getWebViewLink());
        return result;
    }

    //upload file
    public Map uploadFile(List<String> parents, String fileName, java.io.File fileToUpload) {
        Map<String, String> result = new LinkedHashMap();
        File newGGDriveFile = new File();
        newGGDriveFile.setParents(parents).setName(fileName);
        // dinh dang file up len la file zip
        FileContent mediaContent = new FileContent("application/zip", fileToUpload);
        String urlFile;
//        String nameFile = null;
        String idFile;
        try {
            File file = googleDrive.files().create(newGGDriveFile, mediaContent).setFields("id, name, webContentLink").execute();
            urlFile = file.getWebContentLink();
//            nameFile = file.getName();
            idFile = file.getId();
            result.put("urlFile", urlFile);
//            result.put("nameFile", nameFile);
            result.put("idFile", idFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //delete file
    public  void deleteFile(String fileId) { // fileId: id file can xoa tren googleDrive
        try {
            googleDrive.files().delete(fileId).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    //download file
    public void downloadFile(String fileId, String idDb){
        try {
            // luu vao file co duong dan
            OutputStream outputStream = new FileOutputStream("E:\\fileDriveTest\\" + idDb);
            googleDrive.files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadFile2(String webContentLink, String path){
        try {
            org.apache.commons.io.FileUtils.copyURLToFile(new URL(webContentLink), new java.io.File(path), 5000, 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
