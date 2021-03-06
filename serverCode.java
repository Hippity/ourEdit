package asdfghjk;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io. *;
import java.net. *;


	

	public class OutEdit{
		
		private static String path ="C:\\Users\\User\\Downloads\\project 350 sources\\";
		private static	ArrayList<String> newFiles = new ArrayList<String>();
		private static  ArrayList<String> wantToDelete = new ArrayList<String>();
		private static  ArrayList<String> deletedFiles = new ArrayList<String>();

	
		private ServerSocket serverSocket;
		public OutEdit() throws ClassNotFoundException {
			Class.forName("com.mysql.jdbc.Driver");
		}
		
		public void acceptConnections() {
			
		try {	
		serverSocket = new ServerSocket(12347);
		}
		catch (IOException e) {
		System.err.println("ServerSocket instantiation failure");
		e.printStackTrace();
		System.exit(0);	
		}
		while (true) 
		{			
		try {
			System.out.println("Waiting");
		Socket newConnection = serverSocket.accept();
		System.out.println("accepted connection");		
		ServerThread st = new ServerThread(newConnection);
		new Thread(st).start();	
		}	
		catch (IOException ioe) {		
		System.err.println("server accept failed");}
		}}
		
		public String createFile(String fileName,String fileCreator) {
			String backToClient = "Boop";
			File newFile = new File(path+fileName+".txt");
			try {
				if (newFile.createNewFile()) {
					backToClient ="File Has Been Succesfully Made";
					FileWriter myWriter = new FileWriter(path+fileName+".txt");
					   myWriter.close();	
					}
				
					else {
						backToClient = "Change Your File Name";		
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			return backToClient;
		}
		
		public String openFile(String fileName) {
			String backToClient="File Opened Properly";
			File openFile = new File(path+fileName+".txt");
			try {
				Scanner myReader = new Scanner(openFile);
				while (myReader.hasNextLine()) {
					backToClient = backToClient +"\n"+ myReader.nextLine();
				}
				myReader.close();
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			return backToClient;
		}
		
		public String deleteFile(String fileName) {
			String backToClient = null;
			File deleteFile = new File(path+fileName+".txt");
			if (deleteFile.delete()) {
				backToClient = "File deleted succesfully";
			}
			else {
				backToClient = "File did not delete";
			}	

			return backToClient;	
		}
		
		public String editFile(String fileName,String fileCreator, String fileContent) {
			String backToClient = "Task Failed";
			File editFile = new File(path+fileName+".txt");
			try {
				new FileWriter(editFile,false).close();
				FileWriter myWriter = new FileWriter(editFile);
				myWriter.write(fileContent);
				myWriter.close();
				backToClient = "File editted succesfully";	
				} 
			catch (IOException e) {
				e.printStackTrace();
				}	
			
			return backToClient;
		}
		
			
		
		
		class ServerThread implements Runnable {
		
		private Socket socket;  
		private BufferedReader fileIn;
		private DataOutputStream fileOut;	
		public ServerThread(Socket socket) {
			
		this.socket = socket;
		}

		public void run(){ 
			
		try {
			
			fileIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			fileOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));	
		
			String sentence = null;  
			String toSend = null;
			
			int toDel = 0;
			
			
			sentence = fileIn.readLine();
			String[] Header= sentence.split( "\\r?\\n|\\r"  );
			String[] Function= Header[0].split(":");
			sentence = fileIn.readLine();
			Header= sentence.split( "\\r?\\n|\\r"  );
			String[] Name= Header[0].split(":");
			sentence = fileIn.readLine();
			Header= sentence.split( "\\r?\\n|\\r"  );
			String[] Creator= Header[0].split(":");
			sentence = fileIn.readLine();
			Header= sentence.split( "\\r?\\n|\\r"  );
			String[] userName= Header[0].split(":");
			
			if (Function[1].equals("update")){
				toSend="File-NameAre:";
				 try
				 {
				 
				 Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/usersinfo","root","");
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery("select filename,filecreator from filenamesandcreators");
				 while(rs.next())
				 {
				toSend= toSend +" "+ rs.getString("filename");

				 }
				 
				 toSend=toSend+"\n"+ "File-CreatorsArr:";
				 
				 rs = stmt.executeQuery("select filename,filecreator from filenamesandcreators");
				 while(rs.next())
				 {
				toSend= toSend +" "+ rs.getString("filecreator");
				 }
				 System.out.println(toSend);

				 con.close();
				 }
				 catch(Exception e)
				 {System.out.println(e);}
				

			}
			
			else if (Function[1].equals("update2")) {
				 toSend="Your updates are:";

				for (int i=0;i<newFiles.size();i=i+2) {
					if (!userName[1].equals(newFiles.get(i+1))) {
						toSend =toSend+"\n"+ newFiles.get(i+1)+ " has created a new file called "+newFiles.get(i);
					}
				}
				for (int i=0;i<wantToDelete.size();i=i+3) {
					if (userName[1].equals(wantToDelete.get(i))) {
						toSend = toSend+"\n"+ wantToDelete.get(i+1)+" wants to delete file "+wantToDelete.get(i+2);
					}
				}
				for (int i=0;i<deletedFiles.size();i=i+2) {
					if (!userName[1].equals(deletedFiles.get(i+1))){
						toSend = toSend + "\n"+deletedFiles.get(i+1)+ "has deleted file " + deletedFiles.get(i);
					}
				}
			}
			
			else if(Function[1].equals("login")) {
				sentence = fileIn.readLine();
				
				Header= sentence.split( "\\r?\\n|\\r"  );
				String[] passWord= Header[0].split(":");
				
				String username = userName[1];
				String password = passWord[1];
				 try {
					 	Class.forName("com.mysql.jdbc.Driver");
					 	Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/usersinfo","root","");
					 	Statement stmt = conn.createStatement();
					 	ResultSet rest = stmt.executeQuery("select * from passwordsandnames where username = '" + username +"' and passwords = '" + password+"'" );

					 	 if (rest.next()) {
					 		 // link back to GUI with login successful
					 		toSend = "Success";
							 	System.out.println("EXITS!!!");
					 	 }
					 	 else {
					 		 // back to GUI with username or password are invalid
							 	System.out.println("ERROR doesnot exist!!!");
							 	toSend = "Login Failed";
					 	 }}
					 catch (Exception e) {
					 	System.out.println("ERROR HANDLING SQL!!!");
					 	e.printStackTrace();
					 }
					 }

				

			
				 
			else if(Function[1].equals("create")) {
				toSend= createFile(Name[1],Creator[1]);
					 try {
					 Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/usersinfo","root","");
					 Statement stmt = conn.createStatement();
					 String sqlInsert = "insert into filenamesandcreators values"+ "("+ "'"+ Name[1]+"'"+","+"'"+Creator[1]+"'" +")";
					 System.out.println("The SQL statement is: " + sqlInsert + "\n"); // 
					 int countInserted = stmt.executeUpdate(sqlInsert);
					 System.out.println(countInserted + " records inserted.\n");

					 String strSelect = "select * from filenamesandcreators";
					 System.out.println("The SQL statement is: " + strSelect + "\n"); 
					 ResultSet rset = stmt.executeQuery(strSelect);
					 while(rset.next()) { 
					 System.out.println(rset.getString("filename") + ", "+ rset.getString("filecreator"));
					 }
					 } catch(Exception ex) {
					 ex.printStackTrace();
					 } 
					 
					

				
				if (!toSend.equals(" Change Your File Name")) {
					newFiles.add(Name[1]);
					newFiles.add(Creator[1]);
				}
			}
			
			else if (Function[1].equals("open")){
				toSend = openFile(Name[1]);
			}
			
			else if (Function[1].equals("delete")) {
				toSend = deleteFile(Name[1]);
				
					 try {
					 
					 Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/usersinfo","root","");

					 Statement stmt = conn.createStatement();
					 String sqlDelete = "DELETE FROM  filenamesandcreators WHERE filename =" +"'"+Name[1]+"'";
					 System.out.println("The SQL statement is: " + sqlDelete + "\n"); // 
					 int countDeleted = stmt.executeUpdate(sqlDelete);
					 System.out.println(countDeleted + " records deleted.\n");
					 String strSelect = "select * from filenamesandcreators";
					 System.out.println("The SQL statement is: " + strSelect + "\n"); 
					 ResultSet rset = stmt.executeQuery(strSelect);
					 while(rset.next()) { 
					 System.out.println(rset.getString("filename") + ", "+ rset.getString("filecreator"));
					 }
					 } catch(Exception ex) {
					 ex.printStackTrace();
					 } 
					 
				deletedFiles.add(Name[1]);
				deletedFiles.add(Creator[1]);
					

				for (int i=0;i<wantToDelete.size();i=i+3) {
					if (userName[1].equals(wantToDelete.get(i))) {
						toDel=i;
						wantToDelete.remove(toDel);
						wantToDelete.remove(toDel);
						wantToDelete.remove(toDel);
					}
				}
				for (int i=0; i<newFiles.size();i=i+2) {
					if (Name[1].equals(newFiles.get(i))) {
						toDel=i;
						newFiles.remove(toDel);
						newFiles.remove(toDel);
					}
				}
				
				
			}
			
			else if (Function[1].equals("edit")) { 
				sentence = fileIn.readLine();
				Header= sentence.split( "\\r?\\n|\\r"  );
				String[] lineCount= Header[0].split(":");
				sentence = fileIn.readLine();
				sentence = fileIn.readLine();
					 
				for (int i =0;i<Integer.parseInt(lineCount[1])-1;i++) {
					sentence =sentence+"\n"+  fileIn.readLine();
				}		 
				toSend = editFile(Name[1],Creator[1],sentence);
			}
			
			else if (Function[1].equals("deleteReq")) {
				wantToDelete.add(Creator[1]);
				wantToDelete.add(userName[1]);
				wantToDelete.add(Name[1]);
				
				toSend = "File Creator will be notified";
				
			}
				
			System.out.println("I am sending this: " +toSend);
			
			fileOut.writeBytes(toSend);	
			fileOut.close();
			socket.close();

			} catch (IOException e) {
				
				e.printStackTrace();

			}}}
	
		public static void main(String args[]) {
			OutEdit server =null;

			try {
				server = new OutEdit();

			}
			catch(ClassNotFoundException e) {
				System.out.println("Not Working");
				e.printStackTrace();
				System.exit(1);
			}
			
			server.acceptConnections();
		}
	}





