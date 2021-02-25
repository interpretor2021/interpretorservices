package com.db.interpretor.service;

import org.springframework.stereotype.Component;

@Component("searchTextService")
public class SearchTextServiceImpl implements SearchTextService {

	public String searchText(String searchStr, String fileType) {
		
		System.out.println("SearchString is "+"-"+searchStr+"Type-"+fileType);
		
		return "Success";
	}

}
