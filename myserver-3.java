import java.net.*;
import java.io.*;
import java.util.*;

public class myserver extends Thread
{
	static final String HTML_START = "<html>" + "<title>HTTP     Server in java</title>" + "<body>";
	static final String HTML_END = "</body>" + "</html>";
	public String tempContent,file_name;
	Socket connectedClient = null;
	BufferedReader inFromClient = null;
	BufferedReader readFromClient = null;
	DataOutputStream outToClient = null;
	DataInputStream dis = null;
	public static final int BUFFER_SIZE = 200;	                    
	public String content = "";	
	
	
public void run()
	{
		try
		{										
System.out.println( "The Client " + connectedClient.getInetAddress() + ":"+ connectedClient.getPort() + " is connected");
readFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
inFromClient =new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));
dis =new DataInputStream(connectedClient.getInputStream());
outToClient=new DataOutputStream (connectedClient.getOutputStream());			
String requestString = inFromClient.readLine();
String headerLine = requestString;
StringTokenizer tokenizer = new StringTokenizer(headerLine);
String httpMethod = tokenizer.nextToken();
String httpQueryString = tokenizer.nextToken();
String[] temp2 = httpQueryString.split("/");   		    file_name = temp2[1];
StringBuffer responseBuffer = new StringBuffer();
responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
responseBuffer.append("The HTTP Client request is ....<BR>");			
if(httpMethod.equals("PUT"))
{
String messagelength = tokenizer.nextToken();
messagelength = tokenizer.nextToken();
File inputfile = new File(file_name);
if (!inputfile.exists())
		{
	      inputfile.createNewFile();
		}
FileWriter inputfilewriter = new FileWriter(inputfile.getName());
BufferedWriter brout = new BufferedWriter(inputfilewriter);
String line = readFromClient.readLine();
brout.write(line);			                
brout.close();
System.out.println("File saved");
sendResponse(200,"File saved at server side",false);
}
else if (httpMethod.equals("GET")) 
{
	if (httpQueryString.equals("/")) 
           {
		} 
else {
	if (new File(file_name).isFile())
           {
		sendResponse(200, file_name, true);
		}
					
else {
	sendResponse(404, "<b>The Requested resource not found ...." + "Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>", false);
	}
	//System.out.println(line);
	}
}

}


catch(SocketTimeoutException s)
            {
	       System.out.println("Socket timed out!");			
		  }
catch(IOException e)
             {
		  e.printStackTrace();
		  }
catch(Exception ex)
             {
		  ex.printStackTrace();
		  System.out.println("Caught Exception");			
		  }
	}


public myserver(Socket client)
        {
		connectedClient = client;
	   }	
 
	public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
		byte[] buffer = new byte[1024] ;
		int bytesRead;
		while ((bytesRead = fin.read(buffer)) != -1 ) {
			out.write(buffer, 0, bytesRead);
		}
		fin.close();
	}



public void sendResponse (int statusCode, String responseString, boolean isFile) throws Exception 
      {
  String statusLine = null;
  String serverdetails = "Server: Java HTTPServer";
  String contentLengthLine = null;
  String file_name = null;
  String contentTypeLine = "Content-Type: text/html" + "\r\n";
  FileInputStream fin = null;
  if (statusCode == 200)
	statusLine = "HTTP/1.1 200 OK" + "\r\n";

  else if(statusCode == 404)
	statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

  if (isFile) 
      {
file_name = responseString;
fin = new FileInputStream(file_name);
contentLengthLine="Content-Length:"+ Integer.toString(fin.available()) + "\r\n";

if (!file_name.endsWith(".htm") && !file_name.endsWith(".html"))
	contentTypeLine = "Content-Type: \r\n";
	}
		
  else 
     {
responseString = server.HTML_START + responseString + server.HTML_END;
contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
	}
		
outToClient.writeBytes(statusLine);
outToClient.writeBytes(serverdetails);
outToClient.writeBytes(contentTypeLine);
outToClient.writeBytes(contentLengthLine);
outToClient.writeBytes("Connection: close\r\n");
outToClient.writeBytes("\r\n");
if (isFile) 
		sendFile(fin, outToClient);
else 
		outToClient.writeBytes(responseString);

outToClient.close();
	}


	public static void main(String[] elements) throws Exception{
		int port_number = Integer.parseInt(elements[0]);
		ServerSocket serverSocket = new ServerSocket(port_number);
//System.out.println("Port: " + serverSocket.getLocalPort());		
System.out.println ("Server is waiting for client");
		while(true) {
//while(serverSo
		//{
System.out.println(" \nWaiting for client ");
//}
System.out.println("Port: " + serverSocket.getLocalPort());
			Socket connected = serverSocket.accept();
			(new server(connected)).start();
		}		
	}
}
