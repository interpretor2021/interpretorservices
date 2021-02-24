package com.db.interpretor.entity;


//@Entity
public class FileDetails {
	
	private String filename;
	private String filepath;
	private String filefullpath;
	private String filesize;
	private String filetype;
	
	
	
	public FileDetails() {
		super();
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilefullpath() {
		return filefullpath;
	}
	public void setFilefullpath(String filefullpath) {
		this.filefullpath = filefullpath;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	@Override
	public String toString() {
		return "FileDetails [filename=" + filename + ", filepath=" + filepath + ", filefullpath=" + filefullpath
				+ ", filesize=" + filesize + ", filetype=" + filetype + "]";
	}
	
	

}
