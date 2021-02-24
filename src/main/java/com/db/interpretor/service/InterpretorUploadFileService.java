package com.db.interpretor.service;



import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.db.interpretor.entity.FileDetails;



public interface InterpretorUploadFileService {
	 public void uploadFile(MultipartFile file);
	 public void uploadObject(String myCredentials,String projectId, String bucketName, String objectName, String filePath);
	 public  ArrayList<FileDetails> getFileListing();
	  
}
