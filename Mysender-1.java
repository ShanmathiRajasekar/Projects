
//Shanmathi Rajasekar
//800966697
//Implementation of Go Back N and Selective Repeat
//Mysender Program
//Udp
//***************************************************************


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.File;
import java.lang.*;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Mysender extends Thread

{
    public void run()
    {
        super.run();
        synchronized (send)
        {

            DatagramPacket datapkt = new DatagramPacket(send, send.length, host, port);

            try {
                sock = new DatagramSocket();
                sock.send(datapkt);
            }
            catch (IOException exp)
            {
                exp.printStackTrace();
            }
        }

        try {
            byte[] buffer = new byte[65536];

            sock.setSoTimeout(timeout);
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            if(size%5==2&&flg[size]==false)
            {
                System.out.println("ACK timed out");
                throw (new SocketException());
            }


            else if(size%10==1&&flg[size]==false)
            {
                System.out.println("Corrupted DataPacket");
                throw (new SocketException());
            }

            else {
                sock.receive(reply);
            }

            byte[] data = reply.getData();
            s = new String(data, 0, reply.getLength());
            if(s.equals("Packet lost")||(size%10==3&&flg[size]==false))
            {
                System.out.println("Packet lost");
                throw (new SocketException());
            }
            else     {
echo(size+" sequence \n");
            }
            ++packet_r;
            if(packet_r==window_size)
            {
                packet_r=0;
                byte[] Mysender=new byte[num_bits+1];
                int i=1;
                int byte_num=0;
                for(int j=byte_length+1;j<Math.min(b.length,(((byte_length+1)*2)));j++)
                {
                    Mysender[i] = b[j];
                    i++;
                    if ((j + 1) % num_bits == 0||((j+1)==b.length)) {
                        byte[] Mysender_temp=new byte[num_bits+1];
                        flg[size_length]=false;
                        bytes_list.add(Mysender);                                           System.arraycopy(Mysender,0,Mysender_temp,0,num_bits);
                        String check= calculateChecksum(Mysender_temp)+"";
                        int bits_t=num_bits+100;
                        byte[] Mysender_final=new byte[bits_t];
                        byte[] Mysender_check=check.getBytes();
                        int l=0;
                        for(int k=0;k<Mysender_check.length;k++)
                        {
                            Mysender_final[l]=Mysender_check[k];
                            l++;
                        }
                        for(int k=0;k<Mysender.length;k++)
                        {
                            Mysender_final[l]=Mysender[k];
                            l++;
                        }
                        threads[size_length] = new Mysender(Mysender_final,size_length);
                        threads[size_length].start();
                        Mysender = new byte[num_bits+1];
                        size_length++;
                        i=0;
                    }
                    byte_num=j;
                }
                byte_length=byte_num;
            }
        }
        catch (SocketException exp)
        {
            try {
                flg[size]=true;
                threads[size] = new Mysender(send,size);
            }
            catch (UnknownHostException exp1)
            {
                exp1.printStackTrace();
            }

            if(protocol.equals("SR"))
            {
                threads[size].start();
System.out.println("Resending segment " + (size+1));

}
                //if(size!=1);
               // for(int j=1 ; j<=size ; j++)
                   // System.out.println("Packets (from first):" +j );
             //}
            else 
            {
                packet_r=0;
                int sent=size-(size%window_size);
                for(int k=sent;k<Math.min((sent+window_size),threads.length);k++)
                {
                    if(threads[k]!=null)
                    {
                        threads[k].stop();
                    }
                }
                packet_r=0;
                System.out.println("\nResending " +sent);
                for(int k=sent;k<Math.min((sent+window_size),threads.length);k++)
                {
                    try {
                        if (threads[k] != null)
                        {
                            threads[k] = new Mysender(threads[k].send, k);
                            threads[k].start();
                        }
                    }
                    catch (UnknownHostException exp1)
                    {
                        exp1.printStackTrace();
                    }
                }
            }
            sock.close();
        }
        catch (IOException exp1)
        {
            exp1.printStackTrace();
        }
        finally {
            sock.close();
        }
    }
    static int k=0;
    int size;
    static int port;
    static int num_bits;
    static int size_length=0;
    static int window_size;
    String s=null;

    public Mysender(byte[] b,int size) throws UnknownHostException { this.size=size;
        this.send=b;
    }
    static String protocol;
    static int timeout;
    static int byte_length=0;
    static Mysender[] threads=new Mysender[1000];
    static int packet_r=0;
    static ArrayList<byte[]> bytes_list=new ArrayList<>();
    InetAddress host = InetAddress.getByName("localhost");
    DatagramSocket sock = null;
    static StringBuilder sb = new StringBuilder();
    static byte[] b;
    static Boolean[] flg;
    byte[] send = new byte[num_bits+1];


    public Mysender() throws UnknownHostException {
    }

    public void sendData(int byte_num) throws UnknownHostException {
    }


    public static long calculateChecksum(byte[] buf)
    {
        int length = buf.length;
        int k = 0;
        long checksum = 0;
        long data;
        long r1;
        long r2;


        while (length > 1)
        {
            r1 = ((buf[k] << 8) & 0xFF00) ;
            r2 = ((buf[k + 1]) & 0xFF);
            data = (r1| r2);
            checksum = checksum + data;


            if ((checksum & 0xFFFF0000) > 0)
            {
                checksum = checksum & 0xFFFF;
                checksum =checksum+ 1;
            }
            k = k+2;
            length =length - 2;
        }


        if (length > 0)
        {
            checksum =checksum + (buf[k] << 8 & 0xFF00);


            if ((checksum & 0xFFFF0000) > 0)
            {
                checksum = checksum & 0xFFFF;
                checksum =checksum + 1;
            }
        }
        checksum = ~checksum;
        checksum = checksum & 0xFFFF;
        return checksum;
    }


//**************************************************************
    public static void main(String args[]) throws IOException
    {
String[] a=new String[5];
int z=0;

     FileReader fr = new FileReader(args[0]);
           BufferedReader br1 = new BufferedReader(fr);
            String s1= "";
            s1=br1.readLine();
            
while (s1 != null)
          {
               
                //System.out.println(s1);
               a[z] = s1;
              if(s1 == " ")
                  
               sb.append(s1);
               sb.append(System.lineSeparator());
               s1= br1.readLine();
              z=z+1;
          }

 port= Integer.parseInt(args[1]);
	int num_packets = Integer.parseInt(args[2]);
System.out.println("Number of packets:"+ num_packets);;
//int port =port1;
	System.out.println("port:"+port);
	System.out.println("Protocol:"+a[0]);
System.out.println("Number of bits:"+a[1]);
	System.out.println("Window size:"+a[2]);
	System.out.println("Timeout:"+a[3]+" milli seconds");
	System.out.println("Segment Size:"+a[4]+"\n\n");
            protocol=a[0];
            num_bits=Integer.parseInt(a[2]);
            window_size=Integer.parseInt(a[1]);
            String s;
            timeout=Integer.parseInt(a[3]);
BufferedReader br = new BufferedReader(new FileReader("fileread.txt"));

        try {

            String line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            int i=1;
            int byte_num=0;

            b= sb.toString().getBytes();
            flg=new Boolean[b.length];
            byte[] Mysender=new byte[num_bits+1];
            for(int w=byte_length;w<Math.min(b.length,num_bits*window_size);w++)
            {
                Mysender[i] = b[w];
                i++;
                if ((w + 1) % num_bits == 0)
                {
                    byte[] Mysender_temp=new byte[num_bits+1];
                    flg[size_length]=false;
                    bytes_list.add(Mysender);
                    System.arraycopy(Mysender,0,Mysender_temp,0,Integer.parseInt(a[1]));
                    String check= calculateChecksum(Mysender_temp)+"";
                    int bits_t=num_bits+100;
                    byte[] Mysender_final=new byte[bits_t];
                    byte[] Mysender_check=check.getBytes();

                    int l=0;
                    for(int k=0;k<Mysender_check.length;k++)
                    {
                        Mysender_final[l]=Mysender_check[k];
                        l++;
                    }
                    for(int k=0;k<Mysender.length;k++)
                    {
                        Mysender_final[l]=Mysender[k];
                        l++;
                    }
                    threads[size_length] = new Mysender(Mysender_final,size_length);
                    threads[size_length].start();
                    Mysender = new byte[num_bits+1];
                    size_length++;
                    i=0;
                }
                byte_num=w;
            }
            byte_length=byte_num;

        } catch (IOException exp) {
            exp.printStackTrace();
        }



        finally {
            br.close();
        }


    }
public static void echo(String msg)
    {
        System.out.println(msg);
    }


}
