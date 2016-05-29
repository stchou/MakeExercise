package com.huangyiting.makeexercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Users {

	private static String FILE_NAME = "users.txt";
	private static String FILE_OUT_NAME = FILE_NAME + ".out.txt";
	
	private static String USERNAME_PREFIX = "<p class=\"nickname ng-binding\" ng-bind-html=\"getUserContact(item.UserName,currentContact.UserName).getDisplayName(currentContact.UserName)\">";

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(FILE_NAME);
			InputStreamReader fileReader = new InputStreamReader(fis, "UTF-8");

			File fileOut = new File(FILE_OUT_NAME);
			fileOut.createNewFile();
			FileWriter filterWriter = new FileWriter(fileOut);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			BufferedWriter bufferedWriter = new BufferedWriter(filterWriter);

			String line = bufferedReader.readLine();

			while (line != null) {
				if (line.contains(USERNAME_PREFIX)) {
//					System.out.println("line = " + line);
					String user = line.replace(USERNAME_PREFIX, "");
//					System.out.println("user = " + user);
				    user = user.replace("</p>", "");
				    
//				    if(user.contains("<img")) {
//				    	String[] emoji = user.split("<img");
//				    	String realName = "";
//				    	for (String s: emoji) {
//				    		if(!s.contains("<img")) {
//				    			realName += s;
//				    		}
//				    	}
//				    	user = realName.trim();
//				    }
				    bufferedWriter.write(user.trim() + "\n");
				}
				line = bufferedReader.readLine();
			}
			
			System.out.println("获取用户名成功！！！！\n");
			System.out.println("存储在："+ FILE_OUT_NAME +"\n");

			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
