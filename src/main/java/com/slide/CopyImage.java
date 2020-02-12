package com.slide;

import java.util.ArrayList;
import java.util.List;

public class CopyImage {

	private String tmestmp;
	private List<String> files;
	private String sec;

	public String getTmestmp() {
		return tmestmp;
	}

	public void setTmestmp(String tmestmp) {
		this.tmestmp = tmestmp;
	}

	public List<String> getFiles() {
		if (files == null) {

			files = new ArrayList<String>();
		}
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

}
