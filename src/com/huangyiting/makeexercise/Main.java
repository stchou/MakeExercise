package com.huangyiting.makeexercise;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame  {
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 480;
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	private static String WEIXIN_CHART_FILE_PATH = "";
	private static String WEIXIN_MEM_FILE_PATH = "";
	
	public Main() {

		this.setLayout(null);

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("小点健康打卡统计系统 V 1.0");
		// 居中限制
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH,
				FRAME_HEIGHT);
		
		
		// 选择聊天文件
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JLabel chooseFileLael = new JLabel("请选择待统计文件：");
		final JTextField chooseFileField = new JTextField();
		chooseFileField.setEditable(false);
		JButton chooseFileButton = new JButton("浏览");
		chooseFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "选择微信聊天記錄文件");
				File file = jfc.getSelectedFile();
				if(file==null || !file.exists()) {
					return ;
				}
				WEIXIN_CHART_FILE_PATH = file.getAbsolutePath();
				chooseFileField.setText(WEIXIN_CHART_FILE_PATH);
			}
		});

		chooseFileLael.setBounds(new Rectangle(10, 10, 120, 30));
		chooseFileField.setBounds(new Rectangle(130, 14, 200, 26));
		chooseFileButton.setBounds(new Rectangle(350, 10, 100,30));
		panel.add(chooseFileLael);
		panel.add(chooseFileField);
		panel.add(chooseFileButton);
		
		//选择日期
		JLabel startDateLabel = new JLabel("开始时间：");
		JLabel endDateLabel = new JLabel("结束时间：");
		final DateChooserJButton dateChooserJButton = new DateChooserJButton(new Date(System.currentTimeMillis() - 7 *24 *60 *60 *1000));
		final DateChooserJButton endDateChooserJButton = new DateChooserJButton(new Date(System.currentTimeMillis() - 1 *24 *60 *60 *1000));
		
		
		startDateLabel.setBounds(new Rectangle(10, 50, 80, 26));
		endDateLabel.setBounds(new Rectangle(290, 50, 80, 26));
		dateChooserJButton.setBounds(new Rectangle(80, 50, 200, 26));
		endDateChooserJButton.setBounds(new Rectangle(360, 50, 200, 26));
		panel.add(startDateLabel);
		panel.add(dateChooserJButton);
		panel.add(endDateLabel);
		panel.add(endDateChooserJButton);
		
		// 选择群人员信息
		JLabel memberLabel = new JLabel("选择群成员文件：");
		final JTextField memchooseFileField = new JTextField();
		memchooseFileField.setEditable(false);
		JButton memchooseFileButton = new JButton("浏览");
		memchooseFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "选择微信群成员文件");
				File file = jfc.getSelectedFile();
				if(file==null || !file.exists()) {
					return ;
				}
				WEIXIN_MEM_FILE_PATH = file.getAbsolutePath();
				memchooseFileField.setText(WEIXIN_MEM_FILE_PATH);
				
			}
		});
		memberLabel.setBounds(new Rectangle(10, 90, 120, 30));
		memchooseFileField.setBounds(new Rectangle(130, 90, 200, 26));
		memchooseFileButton.setBounds(new Rectangle(350, 90, 100,30));
		panel.add(memberLabel);
		panel.add(memchooseFileField);
		panel.add(memchooseFileButton);
		
		

		JButton startButton = new JButton("统计 Do It !!!");
		final JTextArea infoLabel = new JTextArea("等待执行.......");
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(WEIXIN_CHART_FILE_PATH.equals("")) {
					JOptionPane.showMessageDialog(null,  "您还没有选择聊天文件！","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(WEIXIN_MEM_FILE_PATH.equals("")) {
					JOptionPane.showMessageDialog(null,  "您还没有选择微信群成员文件！","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				infoLabel.append(" 开始扫描群成员信息...." + "\n");
				Users usersScan = new Users();
				Users.FILE_NAME = WEIXIN_MEM_FILE_PATH;
				Users.FILE_OUT_NAME = WEIXIN_MEM_FILE_PATH + ".out";
				try {
					usersScan.run();

					infoLabel.append("获取群成员信息成功！！ 存储在： " + Users.FILE_OUT_NAME  + "\n");
				} catch (Exception e1) {
					e1.printStackTrace();
					infoLabel.append(e1.toString() + "\n");
					return;
				}
				
				
				FillterHabit fillterHabit = new FillterHabit();
				FillterHabit.FILE_NAME = WEIXIN_CHART_FILE_PATH;
				FillterHabit.FILE_OUT_NAME = WEIXIN_CHART_FILE_PATH + " 打卡导出.txt";
				FillterHabit.USER_FILE_NAME = Users.FILE_OUT_NAME;
				FillterHabit.StartDateString = FillterHabit.dateFormat.format(dateChooserJButton.getDate());
				FillterHabit.EndDateString = FillterHabit.dateFormat.format(endDateChooserJButton.getDate());
				try {
					fillterHabit.run();
				} catch (Exception e1) {
					e1.printStackTrace();
					infoLabel.append(e1.toString() + "\n");
					return;
				}
				

				infoLabel.append("统计结束\n");
				infoLabel.append("结果文件存放在： " + FillterHabit.FILE_OUT_NAME + "\n");
				
				try {
					Runtime.getRuntime().exec("start  " + FillterHabit.FILE_OUT_NAME);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		infoLabel.setBounds(new Rectangle(10, 200, FRAME_WIDTH, 100));
		startButton.setBounds(new Rectangle((FRAME_WIDTH - 300 ) / 2 , 300, 300, 60));
		panel.add(infoLabel);
		panel.add(startButton);
		
		panel.setBounds(new Rectangle(0, 0, this.getWidth(), this.getHeight()));;
		this.add(panel);
		
		this.setVisible(true);
	}



	public static void main(String[] args) {

		Main mainFrame = new Main();
		mainFrame.show();

	}

}
