package com.huangyiting.makeexercise;

import java.util.regex.Pattern;

public class test {
	public static void main(String args[]) {
		String url = "/admin/user/add";
		if(Pattern.compile("^(/admin).*").matcher(url).matches()) {
			System.out.println("yes");
			//return false;
		}
	}

}
