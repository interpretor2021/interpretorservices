package com.db.interpretor.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.db.interpretor.entity.FileDetails;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;



@Component("InterpretorUploadFileService")
public class InterpretorUploadFileServiceImpl implements InterpretorUploadFileService  {

	    @Value("${app.path}")
	    public String uploadDir;
	      public void uploadObject(String myCredentials,String projectId, String bucketName, String objectName, String filePath) {
	      
	        Storage storage = null;
			try {
				storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(ServiceAccountCredentials.fromStream(
				        new FileInputStream(myCredentials))).build().getService();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        BlobId blobId = BlobId.of(bucketName, objectName);
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
	        try {
				storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath.toString())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        System.out.println(
	            "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
	      }
	  
	    

	 public void uploadFile(MultipartFile file)  {
		 		 Path copyLocation = Paths
	                .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
	            try {
					Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 
	    }
	 
	public  ArrayList<FileDetails> getFileListing() {
	        File dir = new File("C:/Hackethon/Files/");
	        File[] files = dir.listFiles();
	        ArrayList<FileDetails> filePaths=new ArrayList<FileDetails>(); 
	        FileDetails filedet = new FileDetails();
	
	        for (File file : files) { 
	        	filedet.setFilefullpath(file.getAbsolutePath());
	        	filedet.setFilename(file.getName());
	        	filedet.setFilepath(file.getParent());
	        	filedet.setFilesize(String.valueOf(file.length()));
	        	filedet.setFiletype(file.getName().substring(file.getName().lastIndexOf(".") + 1));
	        	filePaths.add(filedet);
	        }
	        return filePaths;
	 }
}
