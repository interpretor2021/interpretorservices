package com.db.interpretor.service;

import java.util.ArrayList;

import com.db.interpretor.entity.FileDetails;

public interface SearchTextService {
		public ArrayList<FileDetails> searchText(String appmyCredentials,String appprojectId,String appbucketName, String appobjectName, String searchStr,String fileType);
}
