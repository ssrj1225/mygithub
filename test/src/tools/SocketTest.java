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
			 //���������յ��ͻ��˵����ݺ󣬴�����˿ͻ��˶Ի���Socket   
			 Socket socket = ss.accept();   
			 //������ͻ��˷������ݵ������   
			 DataOutputStream dos = new DataOutputStream(socket.getOutputStream());   
			 //���ڽ��տͻ��˷��������ݵ�������   
			 DataInputStream dis = new DataInputStream(socket.getInputStream());   
			 System.out.println("���������յ��ͻ��˵���������" + dis.readUTF());   
			 
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
							System.out.println("���������յ��ͻ���num:" + num);
						}
					} catch (IOException e) {
						break;
					}
				
			}
			 //��������ͻ��˷������ӳɹ�ȷ����Ϣ   
			 dos.writeUTF("���������������ӳɹ�!");   
			 //����Ҫ����ʹ�ô�����ʱ���ر�����   
			 socket.close();   
			 ss.close();   
		  } catch (IOException e) {   
				 e.printStackTrace();   
		  }   
	 }
}
