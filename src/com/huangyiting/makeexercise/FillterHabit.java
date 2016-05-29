package com.huangyiting.makeexercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FillterHabit {
	public static String FILE_NAME = "2016-5-29.txt";
	public static String FILE_OUT_NAME = FILE_NAME + ".out.txt";
	public static String USER_FILE_NAME = "makeUsers.txt";
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	public static String StartDateString = "2016-05-22";
	public static String EndDateString = "2016-05-28";
	private static Date START_DATE;
	private static Date END_DATE;
	private static int REACH_COUNT = 3;
	

	public void run() throws Exception {
		START_DATE = dateFormat.parse(StartDateString);
		END_DATE = dateFormat.parse(EndDateString);
		
		ArrayList<HabitBean> userContents = new ArrayList<HabitBean>();
		Map<String, ArrayList<HabitBean>> notReachuserContents = new HashMap<>();

		// 读取微信的所有好友姓名

		ArrayList<String> userNames = new ArrayList<>();

		FileInputStream fileInputStream = new FileInputStream(USER_FILE_NAME);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
		BufferedReader usebufferedReader = new BufferedReader(inputStreamReader);

		String readline = usebufferedReader.readLine();

		while (readline != null) {

			userNames.add(readline);
			readline = usebufferedReader.readLine();
		}

		inputStreamReader.close();
		inputStreamReader.close();
		fileInputStream.close();

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
				
				if(dateFormat.parse(time).compareTo(START_DATE) < 0) {
					line = bufferedReader.readLine();
					continue;
				}
				if(dateFormat.parse(time).compareTo(END_DATE) > 0) {
					line = bufferedReader.readLine();
					continue;
				}
				
				
				
				HabitBean bean = new HabitBean();
				bean.time = time.trim();
				bean.userName = userName.trim().replaceAll("\\?", " ");
				bean.userName = bean.userName.trim();
				String number = bean.userName.split("-")[0];
				bean.number = number;
				int wenben = line.indexOf("文本");
				String content = line.substring(wenben + 4, line.length());

				bean.content = content.trim();

				userContents.add(bean);
				// bufferedWriter.write(line + "\n");
			}

			line = bufferedReader.readLine();
		}

		// 转化list 2 map
		for (HabitBean bean : userContents) {
			ArrayList<HabitBean> array = notReachuserContents.get(bean.number);
			if (array == null) {
				array = new ArrayList<>();
			}
			array.add(bean);
			notReachuserContents.put(bean.number, array);
		}

		bufferedWriter.write("已达标======================\n");


		ArrayList<String> arrayList = new ArrayList<String>();
		for (String username : notReachuserContents.keySet()) {
			arrayList.add(username);
		}
		Collections.sort(arrayList, new HabitComparator());
		for (String username : arrayList) {
			if (notReachuserContents.get(username).size() >= REACH_COUNT) {
				for (HabitBean bean : notReachuserContents.get(username)) {
					writeInfoOut(bufferedWriter, bean);
				}
			}
		}

		bufferedWriter.write("打卡次数未达标的人=================\n");
		for (String username : arrayList) {
			if (notReachuserContents.get(username).size() < REACH_COUNT) {
				for (HabitBean bean : notReachuserContents.get(username)) {
					writeInfoOut(bufferedWriter, bean);
				}
			}
		}

		// 查找没有任何打卡记录的人
		bufferedWriter.write("没有打卡记录的人==================\n");
		for (String name : userNames) {
			String number = name.split("-")[0];
			if (notReachuserContents.get(number) == null) {
				HabitBean bean = new HabitBean();
				bean.userName = name;
				writeInfoOut(bufferedWriter, bean);
			}
		}

		// for (HabitBean bean : userContents) {
		// bufferedWriter.write(bean.userName + "\t");
		// bufferedWriter.write(bean.time + "\t");
		// bufferedWriter.write(bean.content + "\n");
		// }

		System.out.println("写出成功！！！！！！1\n");
		System.out.println("存储在：" + FILE_OUT_NAME + "\n");

		bufferedWriter.flush();
		bufferedReader.close();
		bufferedWriter.close();

	}

	public static void main(String[] args) {
		try {
			new FillterHabit().run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	// 格式化输出
	private static void writeInfoOut(BufferedWriter bufferedWriter, HabitBean bean) throws Exception {

		bufferedWriter.write(bean.userName + "\t");
		bufferedWriter.write(bean.time + "\t");
		bufferedWriter.write(bean.content + "\n");
	}

}
