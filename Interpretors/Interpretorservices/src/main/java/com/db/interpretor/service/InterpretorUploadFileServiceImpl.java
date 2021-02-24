package com.db.interpretor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Component("InterpretorUploadFileService")
public class InterpretorUploadFileServiceImpl implements InterpretorUploadFileService  {

	    @Value("${app.path}")
	//	@Value("C:\\Users\\umreshi\\Project\\Hackathon\\Files")
	    public String uploadDir;
		


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
	
}
