package tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {

	 public static void main(String[] args) throws Exception { 
		 ServerSocket ss = null;   
		 try {   
			 ss = new ServerSocket(8880);   
			 //服务器接收到客户端的数据后，创建与此客户端对话的Socket   
			 Socket socket = ss.accept();   
			 //用于向客户端发送数据的输出流   
			 DataOutputStream dos = new DataOutputStream(socket.getOutputStream());   
			 //用于接收客户端发来的数据的输入流   
			 DataInputStream dis = new DataInputStream(socket.getInputStream());   
			 System.out.println("服务器接收到客户端的连接请求：" + dis.readUTF());   
			 
			 int frame_size = 20000;
			 byte[] buffer = new byte[1024 * 64];
			 int num, number = 0;
				
			 while (true) {
				 try {
						int h264length = dis.readInt();
						
						number = 0;
						while (number < h264length) {
							int lost = h264length - number;
							num = dis.read(buffer, 0, frame_size < lost ? frame_size : lost);
							if (num == -1) {
								break;
							}
							number += num;
							System.out.println("服务器接收到客户端num:" + num);
						}
					} catch (IOException e) {
						break;
					}
				
			}
			 //服务器向客户端发送连接成功确认信息   
			 dos.writeUTF("接受连接请求，连接成功!");   
			 //不需要继续使用此连接时，关闭连接   
			 socket.close();   
			 ss.close();   
		  } catch (IOException e) {   
				 e.printStackTrace();   
		  }   
	 }
}
