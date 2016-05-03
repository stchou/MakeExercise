package com.huangyiting.makeexercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FillterHabit {
	private static String FILE_NAME = "2016-04-24.txt";
	private static String FILE_OUT_NAME = FILE_NAME + ".out.txt";
	private static int REACH_COUNT = 3;
	
	


	public static void main(String[] args) {
		ArrayList<HabitBean> userContents = new ArrayList<HabitBean>();
		Map<String, ArrayList<HabitBean>> notReachuserContents = new HashMap<>();
		
		if (args != null && args.length>=1 && args[0] != null) {
			FILE_NAME = args[0];
		} else {
			wirtehelp();
			return;
		}
		
//		if (args != null && args.length>=2 && args[0] != null) {
//			try{
//				REACH_COUNT = Integer.valueOf(args[1]);
//			} catch (Exception e) {
//				
//			}
//		}
		
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
			
			
			// 转化list 2 map
			for(HabitBean bean: userContents) {
				ArrayList<HabitBean> array = notReachuserContents.get(bean.userName);
				if(array == null) {
					array = new ArrayList<>();
				}
				array.add(bean);
				notReachuserContents.put(bean.userName, array);
			}
			
			bufferedWriter.write("已达标\n");
			for (String username : notReachuserContents.keySet()) {
				if(notReachuserContents.get(username).size() >= REACH_COUNT) {
					for (HabitBean bean: notReachuserContents.get(username)) {
						writeInfoOut(bufferedWriter, bean);
					}
				}
			}
			
			bufferedWriter.write("未达标\n");
			for (String username : notReachuserContents.keySet()) {
				if(notReachuserContents.get(username).size() < REACH_COUNT) {
					for (HabitBean bean: notReachuserContents.get(username)) {
						writeInfoOut(bufferedWriter, bean);
					}
				}
			}
			
//			for (HabitBean bean : userContents) {
//				bufferedWriter.write(bean.userName + "\t");
//				bufferedWriter.write(bean.time + "\t");
//				bufferedWriter.write(bean.content + "\n");
//			}

			System.out.println("写出成功！！！！！！1\n");
			System.out.println("存储在："+ FILE_OUT_NAME +"\n");

			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void wirtehelp() {
		System.out.println("+===========================================================+\n");
		System.out.println("|=======================小点健康统计程序 v1.0====================|\n");
		System.out.println("+===========================================================+\n");
		System.out.println("使用帮助：+\n");
		System.out.println("      \n");
		System.out.println("option: 输入文件路径 \n");
		System.out.println("demo: xiaodian.jar d:/xiaodian.txt \n");
		System.out.println("\n\n\n");
	}
	
	//格式化输出
	private static void writeInfoOut(BufferedWriter bufferedWriter, HabitBean bean) throws Exception{

		bufferedWriter.write(bean.userName + "\t");
		bufferedWriter.write(bean.time + "\t");
		bufferedWriter.write(bean.content + "\n");
	}

}
