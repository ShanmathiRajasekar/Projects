import java.net.*;
// Classes supporting TCP/IP based client-server connections
import java.io.*;
import java.util.*;

import org.omg.Messaging.SyncScopeHelper;

public class myclient
{

	public static void main(String[] elements) 
{
		
		String hostname = elements[0]; 
		int port_number = Integer.parseInt(elements[1]);
		String command = elements[2];
		String file_name = elements[3];
		
try
{
Socket clientSocket = new Socket(hostname,port_number);            
InputStream inputSt = clientSocket.getInputStream();
BufferedReader br = new BufferedReader(new InputStreamReader(inputSt));
DataOutputStream outToServer = new DataOutputStream( clientSocket.getOutputStream() );          OutputStream os = clientSocket.getOutputStream();
PrintWriter out = new PrintWriter(os,true);  
           
 if (elements[2].equals("GET")){             	
	out.println("GET " +  "/" + file_name + " HTTP/1.1");
	out.println("Host: " + hostname);
	out.println("Connection: close");  
	out.println();
	while (true) {
            	    String line = br.readLine();
            	    if (line == null)
               {      	       		break; 
               }
				System.out.println(line);
                	}
            }
			else if (elements[2].equals("PUT"))
                {
                if (!new File(file_name).isFile()) {
                	System.out.println("give valid filename");
                	return;
                }

FileInputStream finput = new FileInputStream(file_name);
byte[] buffer = new byte[1024];
int bytesRead;
String message1 = "";
String temp_message = "";
int numoflines = 0;
BufferedReader brr = new BufferedReader(new FileReader(file_name));
    			

while ((temp_message=brr.readLine())!= null)
	{
       	message1 = message1+ temp_message;
 	   	numoflines++;
    	  }
    			brr.close();
    			String message = "PUT /"+file_name+" HTTP/1.1 ";
    		
out.println(message+Integer.toString(numoflines));
out.println("\r\n\r\n");
System.out.println("File successfully saved!");
    			
while ((bytesRead=finput.read(buffer))!=-1)
        		{
                	outToServer.write(buffer,0,bytesRead);
                }
System.out.println(file_name+" file sent");
finput.close(); 						
	}
            
else 
            {
System.out.println("Incorrect command (GET/PUT)");
return;
            }

			
br.close();
inputSt.close();	
outToServer.close();
out.close();
os.close();
clientSocket.close();            
}



catch (UnknownHostException e)
           {
	System.out.println("Unknown hostname");
		} 

catch (IOException e)
           {
			System.out.println("Not able to get I/O for the connection to hostname");
		}
	}
}