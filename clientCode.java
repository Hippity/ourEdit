package boomer;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.net.*;
import java.io.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
//Zein Zebib Mohammed Asaad Chadi el Hakim
public class GUI implements ActionListener {
	
	//Client Stuff
	private static Socket clientSocket;
	private static DataOutputStream outToServer;
	private static BufferedReader inFromServer;
	
	
	//GUI Stuff
	private static JLabel name;
	private static JLabel slogan;
	private static JLabel userLabel;
	private static JTextField userName;
	private static JLabel passLabel;
	private static JPasswordField userPass;
	private static JButton login;
	private static JPanel panel;
	private static JPanel panel2;
	private static JFrame frame;
	private static String username;
	private static JLabel welcome;
	private static JButton createFile;
	private static JButton accCreate;
	private static JButton cancelCreate;
	private static JTextField fileName;
	private static String filename;
	private static JLabel whiteLine ;
	private static JLabel greySpace ;
	private static JLabel fileLabel;
	private static JLabel replyLabel;
	private static String reply="Welcome to OurEdit";
	private static JLabel[] nameLabelArr;
	private static JLabel[] creatorLabelArr;
	private static JButton[] openFileArr;
	private static JButton[] deleteFileArr;
	
	private static	ArrayList<String> fileNameArr = new ArrayList<String>();
	private static ArrayList<String> fileCreatorsArr= new ArrayList<String>();
	
	private static String content;
	private static int numPressed=0;
	private static JLabel myFile;
	private static JLabel myCreator;
	private static JTextArea myContent;
	private static JButton back;
	private static JButton save;
	private static JTextArea message;
	private static String messages="";
	private static String passwordInput;
	private static String loginReply;
	
	private static Boolean updatedOnce =false;
	
	
	public GUI() {
		
		
	}
	public static void frontPage() {
		//Init
		frame = new JFrame();
		panel = new JPanel();
		frame.setTitle("OurEdit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBackground(new Color(32, 33, 36));
		panel.setLayout(null);
		frame.add(panel);
		frame.setSize(1900,800);
		//End Init 
		
		//Header 
		name = new JLabel();
		name.setFont(new Font("Arial",Font.PLAIN,35));
		name.setText("  OUREDIT");
		name.setBounds(0,0,1900,80);
		name.setOpaque(true);
		name.setForeground(Color.white);
		name.setBackground(new Color(53, 54, 58));
		panel.add(name);
		//End Header
		
		//Slogan
		slogan = new JLabel("<html><div style='text-align: center'>Welcome to OurEdit <br> Save and Share Your Files!!<div> <html>",SwingConstants.CENTER);
		slogan.setBounds(450,-100,600,600);
		slogan.setForeground(Color.white);
		slogan.setFont(new Font("Arial",Font.PLAIN,40));
		panel.add(slogan);
		//End Slogan
		
		//Username Button
		userLabel = new JLabel();
		userName = new JTextField(20);
		userLabel.setFont(new Font("Arial",Font.BOLD,20));
		userLabel.setText("Username: ");
		userLabel.setForeground(Color.white);
		userName.setBackground(Color.white);
		userName.setForeground(new Color(53, 54, 58));
		userName.setFont(new Font("Arial",Font.PLAIN,20));
		Border border = BorderFactory.createLineBorder(Color.white);
	    userName.setBorder(border);
	    userLabel.setBounds(575,300,200,30);
	    userName.setBounds(690,300,200,30); 
	    panel.add(userLabel);
		panel.add(userName);
		//End Username Button
		
		//Password Button
		passLabel = new JLabel();
		userPass = new JPasswordField(20);
		passLabel.setFont(new Font("Arial",Font.BOLD,20));
		passLabel.setText("Password: ");
		passLabel.setForeground(Color.white);
		userPass.setBackground(Color.white);
		userPass.setForeground(new Color(53, 54, 58));
		userPass.setFont(new Font("Arial",Font.PLAIN,20));
	    userPass.setBorder(border);
	    passLabel.setBounds(575,350,200,30);
	    userPass.setBounds(690,350,200,30); 
	    panel.add(passLabel);
		panel.add(userPass);
		//End Pass Button
		
		//Submit Button
		login = new JButton("LOGIN");
		login.setForeground(Color.white);
		login.setBackground(new Color(53, 54, 58));
		login.setFont(new Font("Arial",Font.PLAIN,20));
		login.addActionListener(new GUI());
		login.setBorder(border);
		login.setBounds(705,390,120,40);
		panel.add(login);
		//End Submit
		
		//To Set it visible
		frame.setVisible(true);
		
	}
	
	public static void fileLocation(){

		try {
			updateFromServer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		//Init
		panel2 = new JPanel();
		panel2.setVisible(true);
		panel2.setBackground(new Color(32, 33, 36));
		panel2.setLayout(null);
		frame.add(panel2);
		//End Init
		
		//Header 
		name = new JLabel();
		name.setFont(new Font("Arial",Font.PLAIN,35));
		name.setText("  OUREDIT");
		name.setBounds(0,0,1900,80);
		name.setOpaque(true);
		name.setForeground(Color.white);
		name.setBackground(new Color(53, 54, 58));
		
		//End Header
		
		//Welcome user
		welcome = new JLabel("Welcome "+username,SwingConstants.RIGHT);
		welcome.setFont(new Font("Arial",Font.PLAIN,20));
		welcome.setForeground(Color.white);
		welcome.setBounds(1200,25,300,80);
		//End Welcome user
		
		//Updates
		message = new JTextArea();
		message.setEditable(false);
		message.setFont(new Font("Arial",Font.PLAIN,13));
		message.setForeground(Color.white);
		message.setOpaque(false);
		message.setBounds(10,400,180,300);
		message.setText(messages);
		message.setLineWrap(true);

		panel2.add(message);
		
		//
		
		//Some grey space
		greySpace = new JLabel();
		greySpace.setOpaque(true);
		greySpace.setBackground(new Color(53, 54, 58));
		greySpace.setBounds(0,0,200,800);
		//
		
		//white line
		whiteLine = new JLabel();
		whiteLine.setBounds(0,80,200,4);
		whiteLine.setOpaque(true);
		whiteLine.setBackground(Color.white);
		//end white line
		
		Border border = BorderFactory.createLineBorder(Color.white);

		//Create new doc
		createFile = new JButton("Create File");
		createFile.setForeground(Color.white);
		createFile.setBackground(new Color(32, 33, 36));
		createFile.setFont(new Font("Arial",Font.PLAIN,20));
		createFile.setBorder(border);
		createFile.setBounds(30,95,135,40);
		createFile.addActionListener(new GUI());
		//End create new doc
		
		//Reply from server here
		replyLabel = new JLabel();
		replyLabel.setBackground(new Color(23, 23, 23));
		replyLabel.setForeground(Color.WHITE);
		replyLabel.setBounds(200,700,1900,75);
		replyLabel.setText(" "+reply);
		replyLabel.setOpaque(true);
		replyLabel.setFont(new Font("Arial",Font.PLAIN,20));
		// End
		
		panel2.add(createFile);
		panel2.add(welcome);
		panel2.add(name);
		panel2.add(whiteLine);
		panel2.add(greySpace);
		panel2.add(replyLabel);
		
		Border border2 = BorderFactory.createLineBorder(new Color(53, 54, 58),2);
		//All Files
		
		openFileArr = new JButton[fileNameArr.size()];
		nameLabelArr = new JLabel[fileNameArr.size()];
		creatorLabelArr = new JLabel[fileNameArr.size()];
		deleteFileArr = new JButton[fileNameArr.size()];
		for (int i =0;i<fileNameArr.size();i++) {
		nameLabelArr[i] = new JLabel();
		nameLabelArr[i].setFont(new Font("Arial",Font.PLAIN,20));
		nameLabelArr[i].setForeground(Color.WHITE);
		nameLabelArr[i].setBorder(border2);
		nameLabelArr[i].setBounds(190, 115+i*55, 1500, 50);
		nameLabelArr[i].setText("                                    "+fileNameArr.get(i));
		
		creatorLabelArr[i] = new JLabel();
		creatorLabelArr[i].setFont(new Font("Arial",Font.PLAIN,20));
		creatorLabelArr[i].setForeground(Color.WHITE);
		creatorLabelArr[i].setBounds(600, 115+i*55, 1500, 50);
		creatorLabelArr[i].setText("By: "+fileCreatorsArr.get(i));
			
		openFileArr[i] = new JButton("Open File");
		openFileArr[i].setForeground(Color.white);
		openFileArr[i].setBackground(new Color(53, 54, 58));
		openFileArr[i].setFont(new Font("Arial",Font.PLAIN,20));
		openFileArr[i].setBorder(border);
		openFileArr[i].setBounds(250,120+i*55,135,40);
		openFileArr[i].addActionListener(new GUI());
		
		deleteFileArr[i] = new JButton("Delete File");
		deleteFileArr[i].setForeground(Color.white);
		deleteFileArr[i].setBackground(new Color(53, 54, 58));
		deleteFileArr[i].setFont(new Font("Arial",Font.PLAIN,20));
		deleteFileArr[i].setBorder(border);
		deleteFileArr[i].setBounds(1300,120+i*55,135,40);
		deleteFileArr[i].addActionListener(new GUI());

		panel2.add(creatorLabelArr[i]);
		panel2.add(deleteFileArr[i]);
		panel2.add(openFileArr[i]);
		panel2.add(nameLabelArr[i]);
		
		}
		
		//End all files
		
		

		frame.repaint();
		
	}
	
	public static void createFilePressed() {
		Border border = BorderFactory.createLineBorder(Color.white);
		createFile.setVisible(false);
		
		//Label
		fileLabel = new JLabel("Enter File Name:");
		fileLabel.setForeground(Color.white);
		fileLabel.setFont(new Font("Arial",Font.PLAIN,18));
		fileLabel.setBounds(30,95,250,50);
		//
		//File Text
		fileName = new JTextField();
		fileName.setBackground(Color.white);
		fileName.setForeground(new Color(53, 54, 58));
		fileName.setFont(new Font("Arial",Font.PLAIN,20));
		fileName.setBorder(border);
		fileName.setBounds(10, 131, 180, 30);
		//
		
		//Create
		accCreate = new JButton("Create");
		accCreate.setForeground(Color.white);
		accCreate.setBackground(new Color(32, 33, 36));
		accCreate.setFont(new Font("Arial",Font.PLAIN,20));
		accCreate.setBorder(border);
		accCreate.setBounds(25,170,75,40);
		accCreate.addActionListener(new GUI());
		//
		
		//Cancel
		cancelCreate = new JButton("Cancel");
		cancelCreate.setForeground(Color.white);
		cancelCreate.setBackground(new Color(32, 33, 36));
		cancelCreate.setFont(new Font("Arial",Font.PLAIN,20));	
		cancelCreate.setBorder(border);
		cancelCreate.setBounds(110,170,75,40);
		cancelCreate.addActionListener(new GUI());
		//

		panel2.remove(greySpace);
		panel2.add(fileName);
		panel2.add(fileLabel);
		panel2.add(accCreate);
		panel2.add(cancelCreate);
		panel2.add(greySpace);
		frame.repaint();
		
	}
	
	public static void openFilePressed() {
		for (int i=0; i<fileNameArr.size();i++) {
			panel2.remove(openFileArr[i]);
			panel2.remove(deleteFileArr[i]);
			panel2.remove(nameLabelArr[i]);
			panel2.remove(creatorLabelArr[i]);
		}
		panel2.remove(createFile);
		//Label on the side
		myFile = new JLabel();
		myFile.setText(fileNameArr.get(numPressed));
		myFile.setForeground(Color.white);
		myFile.setFont(new Font("Arial",Font.PLAIN,20));
		myFile.setBounds(220,70,250,50);
		
		myCreator = new JLabel();
		myCreator.setText("By: "+fileCreatorsArr.get(numPressed));
		myCreator.setForeground(Color.white);
		myCreator.setFont(new Font("Arial",Font.PLAIN,20));
		myCreator.setBounds(500,70,250,50);
		//
		Border border = BorderFactory.createLineBorder(new Color(23, 23, 23),3);
		//Edit file area
		myContent = new JTextArea();
		myContent.setBorder(border);
		myContent.setBackground(Color.white);
		myContent.setFont(new Font("Arial",Font.PLAIN,18));
		myContent.setForeground(new Color(53, 54, 58));
		myContent.setBounds(220,110,1290,575);
		myContent.setText(content);
		myContent.setLineWrap(true);
		myContent.setWrapStyleWord(true);
		JScrollPane scrollpane = new JScrollPane(myContent);
		scrollpane.setBounds(220,110,1290,575);
		//
		
		Border border2 = BorderFactory.createLineBorder(Color.white);
		
		//Back Button
		back = new JButton("Back");
		back.setForeground(Color.white);
		back.setBackground(new Color(32, 33, 36));
		back.setFont(new Font("Arial",Font.PLAIN,20));
		back.setBorder(border2);
		back.setBounds(30,95,135,40);
		back.addActionListener(new GUI());
		//
		
		//Save Button
		save = new JButton("Save");
		save.setForeground(Color.white);
		save.setBackground(new Color(32, 33, 36));
		save.setFont(new Font("Arial",Font.PLAIN,20));
		save.setBorder(border2);
		save.setBounds(30,150,135,40);
		save.addActionListener(new GUI());
		//
		
		panel2.add(scrollpane);
		panel2.remove(greySpace);
		panel2.add(back);
		panel2.add(save);
		panel2.add(myCreator);
		panel2.add(myFile);
		panel2.add(greySpace);
		frame.repaint();
	}
	
	public static void createToServer() throws Exception {
			openConnection();
			String whatToSend= "Function:create";
		    whatToSend = whatToSend + "\n" + "File-Name:"+filename;
		    whatToSend = whatToSend + "\n" + "File-Creator:"+username;
		    whatToSend = whatToSend + "\n" + "UserName:"+username;
		    System.out.println(whatToSend);
		    outToServer.writeBytes(whatToSend+"\n");
		    reply = inFromServer.readLine();
		    System.out.println("FROM SERVER: " + reply);
		    clientSocket.close();
		
	}
	public static void openToServer() throws Exception{
			openConnection();
        	String whatToSend= "Function:open";
        	whatToSend = whatToSend + "\n" + "File-Name:"+fileNameArr.get(numPressed);
            whatToSend = whatToSend + "\n" + "File-Creator:"+fileCreatorsArr.get(numPressed);
		    whatToSend = whatToSend + "\n" + "UserName:"+username;
	        System.out.println(whatToSend);
	        outToServer.writeBytes(whatToSend+"\n");
	        reply = inFromServer.readLine();
	        content = inFromServer.lines().collect(Collectors.joining(System.lineSeparator()));
	        System.out.println("FROM SERVER: " + reply);
	        System.out.println(content);
	        clientSocket.close();	
	}
	
	public static void editToServer() throws Exception {
		openConnection();
		content = myContent.getText();
		int numLines = myContent.getLineCount();
		String whatToSend= "Function:edit";
    	whatToSend = whatToSend + "\n" + "File-Name:"+fileNameArr.get(numPressed);
        whatToSend = whatToSend + "\n" + "File-Creator:"+fileCreatorsArr.get(numPressed);
	    whatToSend = whatToSend + "\n" + "UserName:"+username;
        whatToSend = whatToSend + "\n" + "Line-Count:"+numLines;
        whatToSend = whatToSend + "\n" + "File-Content:"+"\n"+content;
        System.out.println(whatToSend);
        outToServer.writeBytes(whatToSend+"\n");
        reply = inFromServer.readLine();
        System.out.println("FROM SERVER: " + reply);
        clientSocket.close();	
	}
	
	public static void deleteToServer() throws Exception{
		openConnection();
		String whatToSend= "Function:delete";
    	whatToSend = whatToSend + "\n" + "File-Name:"+fileNameArr.get(numPressed);
        whatToSend = whatToSend + "\n" + "File-Creator:"+fileCreatorsArr.get(numPressed);
	    whatToSend = whatToSend + "\n" + "UserName:"+username;
        System.out.println(whatToSend);
        outToServer.writeBytes(whatToSend+"\n");
        reply = inFromServer.readLine();
        System.out.println("FROM SERVER: " + reply);
        clientSocket.close();
	}
	
	public static void requestDel() throws Exception{
		openConnection();
		String whatToSend= "Function:deleteReq";
    	whatToSend = whatToSend + "\n" + "File-Name:"+fileNameArr.get(numPressed);
        whatToSend = whatToSend + "\n" + "File-Creator:"+fileCreatorsArr.get(numPressed);
	    whatToSend = whatToSend + "\n" + "UserName:"+username;
        System.out.println(whatToSend);
        outToServer.writeBytes(whatToSend+"\n");
        reply = inFromServer.readLine();
        System.out.println("FROM SERVER: " + reply);
        clientSocket.close();
	}
	
	public static void updateFromServer() throws Exception {
		openConnection();
		String whatToSend= "Function:update";
    	whatToSend = whatToSend + "\n" + "File-Name:Nothing";
        whatToSend = whatToSend + "\n" + "File-Creator:Nothing";
	    whatToSend = whatToSend + "\n" + "UserName:"+username;
        System.out.println(whatToSend);
        outToServer.writeBytes(whatToSend+"\n");
        
		String sentence = inFromServer.readLine();
		String[] Header= sentence.split( "\\r?\\n|\\r"  );
		String[] names= Header[0].split(":");
		System.out.println(sentence);
		String[] totalNames = names[1].split(" ");
		
		sentence = inFromServer.readLine();

		Header= sentence.split( "\\r?\\n|\\r"  );
		String[] creators= Header[0].split(":");
		String[] totalCreators = creators[1].split(" ");
		fileNameArr.clear();
		fileCreatorsArr.clear();
		for (int i=0;i<totalNames.length;i++) {
			if (!fileNameArr.contains(totalNames[i])) {
			fileNameArr.add(totalNames[i]);

			fileCreatorsArr.add(totalCreators[i]);
		}
		}
		fileNameArr.remove(0);
		fileCreatorsArr.remove(0);
		

        clientSocket.close();
        
        openConnection();
		whatToSend= "Function:update2";
    	whatToSend = whatToSend + "\n" + "File-Name:Nothing";
        whatToSend = whatToSend + "\n" + "File-Creator:Nothing";
	    whatToSend = whatToSend + "\n" + "UserName:"+username;
        System.out.println(whatToSend);
        outToServer.writeBytes(whatToSend+"\n");
        messages =  inFromServer.lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("FROM SERVER: " + messages);
        clientSocket.close();
		
	}
	

	
	public static void loginToServer( ) throws Exception{
		openConnection();
		String whatToSend = "Function:login";
		whatToSend = whatToSend + "\n" + "File-Name:Nothing";
        whatToSend = whatToSend + "\n" + "File-Creator:Nothing";
	    whatToSend = whatToSend + "\n" + "UserName:"+username;
	    whatToSend = whatToSend + "\n" + "Password:"+passwordInput;
	    outToServer.writeBytes(whatToSend+"\n");
	    loginReply = inFromServer.readLine();
        System.out.println("FROM SERVER: " + loginReply);
        clientSocket.close();
        
        if (loginReply.equals("Success")){
    		panel.setVisible(false);
    		fileLocation();	
        }
        else {
        	userName.setText(loginReply);
        }
	    
		
	}
	
	public static void openConnection() throws Exception {
		InetAddress local = InetAddress.getLocalHost();
		clientSocket = new Socket(local, 12347); 
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static void main(String[] args) throws Exception {
		frontPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {	

		if (e.getSource()==login) {
		username = userName.getText();
		passwordInput = userPass.getText();
		try {
			loginToServer();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		}
		
		if (e.getSource()==createFile) {
			createFilePressed();
		}
		
		if (e.getSource()==accCreate) {
			filename = fileName.getText();
			try {
				createToServer();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if (reply.equals("File Has Been Succesfully Made")) {
				fileNameArr.add(filename);
				fileCreatorsArr.add(username);
			}
			panel2.removeAll();
			panel2.setVisible(false);
			fileLocation();
			
		}
		if (e.getSource()==cancelCreate) {
			panel2.removeAll();
			panel2.setVisible(false);
			fileLocation();
		}
		if (openFileArr.length != 0) {
			for (int i = 0;i<openFileArr.length;i++) {
				if (e.getSource()==openFileArr[i]) {
					try {
						numPressed=i;
						openToServer();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					panel2.removeAll();
					panel2.setVisible(false);
					fileLocation();
					openFilePressed();
				}
			}
		}
		if (e.getSource()==back) {
			panel2.removeAll();
			panel2.setVisible(false);
			reply = "Welcome Back";
			fileLocation();
		}
		
		if (e.getSource()==save) {
			try {
				editToServer();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			replyLabel.setText(reply);
		}	
		if (deleteFileArr.length!=0) {
			for (int i = 0;i<deleteFileArr.length;i++) {
				if (e.getSource()==deleteFileArr[i]) {
					try {
						numPressed=i;
						if (username.equals(fileCreatorsArr.get(numPressed))) {
							deleteToServer();
							fileNameArr.remove(numPressed);
							fileCreatorsArr.remove(numPressed);
						}
						else {
							requestDel();
						}
						
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					panel2.removeAll();
					panel2.setVisible(false);
					fileLocation();	
				}	
		}
	}
}
}

