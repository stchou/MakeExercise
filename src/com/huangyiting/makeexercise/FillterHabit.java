package com.huangyiting.makeexercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class FillterHabit {
	private static final String FILE_NAME = "2016-04-24.txt";
	private static final String FILE_OUT_NAME = FILE_NAME + ".out.txt";
	
	


	public static void main(String[] args) {
		ArrayList<HabitBean> userContents = new ArrayList<HabitBean>();
		
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
				if (line.contains("打卡")) {
					String[] param = line.split(" ");
					String time = param[11];
					String userName = param[18];
					HabitBean bean = new HabitBean();
					bean.time = time.trim();
					bean.userName = userName.trim().replaceAll("\\?", " ");
					
					int wenben = line.indexOf("文本");
					String content = line.substring(wenben + 4, line.length());
							
					bean.content = content.trim();
					
					userContents.add(bean);
//					bufferedWriter.write(line + "\n");
				}

				line = bufferedReader.readLine();
			}
			
			
			Collections.sort(userContents , new HabitComparator());
			for (HabitBean bean : userContents) {
				bufferedWriter.write(bean.userName + "\t");
				bufferedWriter.write(bean.time + "\t");
				bufferedWriter.write(bean.content + "\n");
			}

			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
