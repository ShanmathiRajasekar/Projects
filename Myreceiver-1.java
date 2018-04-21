
//Shanmathi Rajasekar
//800966697
//Implementation of Go Back N and Selective Repeat
//Myreceiver Program
//Udp
//***************************************************************


import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Myreceiver
{
//static st1='null';    
static int num=0;
    static ArrayList<String> failed_pkts=new ArrayList<>();
    static int count=0;
static ArrayList<String> pkts=new ArrayList<>();
public static void main(String args[])
{   DatagramSocket sock = null;
// try block
         try
        {
       sock = new DatagramSocket(Integer.parseInt(args[0]));
       echo("\n1Port number: "+args[0]);
       echo("...");

       byte[] buffer = new byte[65536];
       DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
       echo("\nSocket created...");
echo("Waiting for data");
       echo("...");

       while(true)
            {
//echo ("All packets Received"); 

            sock.receive(incoming);
            byte[] data = incoming.getData();
            String st = new String(data, 0, incoming.getLength());
//String st2 = new String( incoming.getLength());
            if(!(pkts.contains(st)))
{
	pkts.add(st);
}

                echo(pkts.indexOf(st) +" Packet Recieved");
            
           if(num%10==1 && !(failed_pkts.contains(st)))
            {
             failed_pkts.add(st);
             st="Packet lost";
            }
         else 
            {
             failed_pkts.add(st);
             st = "OK : " + num + " " + st;
//echo(st);
if(st=="                        ")
echo ("All packets Received"); 
//st1 = stringBuilder.append(st);  
            }
//echo (st1); 

num++;
//echo(num);
DatagramPacket datapkt = new DatagramPacket(st.getBytes() , st.getBytes().length , incoming.getAddress() , incoming.getPort());
sock.send(datapkt);

if(st==null)
echo ("All packets Received"); 

            }
        }

//catch block
        catch(IOException exp)
        {
            //System.err.println("IOException " + exp);
        }

    }

//************************************************************
public static void echo(String msg)
    {
        System.out.println(msg);
    }

}
//************************************************************