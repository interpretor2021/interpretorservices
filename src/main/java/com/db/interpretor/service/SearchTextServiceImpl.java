package com.db.interpretor.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.interpretor.entity.FileDetails;

@Component("searchTextService")
public class SearchTextServiceImpl implements SearchTextService {
	@Autowired
	private InterpretorUploadFileService interpretorUploadFileService;
	@Autowired
	private viewTextService viewTextService;
	
	public ArrayList<FileDetails> searchText(String appmyCredentials,String appprojectId,String appbucketName, String appobjectName, String searchStr, String fileType) {
		
		String fType;
		String fName;
		String retText;
		String strAll = "all";
		ArrayList<FileDetails> arrList = interpretorUploadFileService.getFileListingCloud(appmyCredentials,appprojectId,appbucketName,appobjectName);
		ArrayList<FileDetails> filePaths=new ArrayList<FileDetails>(); 
		
		FileDetails filedet = new FileDetails();
		for (FileDetails file : arrList) {  
			
			System.out.println("File Name is " + file.getFilename());
			fName =file.getFilename();
			fType = fName.substring(fName.lastIndexOf(".")+1);
			if (fileType.contains(strAll) || fileType.contains(fType))
			{			
					retText = viewTextService.extractText(appmyCredentials,appprojectId,appbucketName,fName);
					System.out.println("retText is " + retText);
					if (retText.contains(searchStr)) {
						System.out.println("Search String " + searchStr + " found in file " + fName);
						filePaths.add(new FileDetails(
								file.getFilename(),
								file.getFilefullpath(),
								file.getFilepath(),
								file.getFilesize(),
								file.getFiletype()));
			        	  }
					}
				 
			}

		System.out.println("SearchString is "+"-"+searchStr+"Type-"+fileType);
		
		return filePaths;
	}

}
