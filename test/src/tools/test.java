package tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class test {

	 public static void main(String[] args) throws Exception { 
		 
		 //5E B0 E5 3F
		 //1.7944448
		 byte[] array = ByteUtil.getBytes(65416);
		 byte[] array1 = {-120,-1,0,0};
		 byte[] array2 = {0x5e,(byte) 0xb0,(byte) 0xe5,0x3f};
		 
		 for (int i = 0; i < array.length; i++) {
			 System.out.println(array[i]);
		 }
		 
		 
		 System.out.println(ByteUtil.getInt(array));
		 System.out.println(ByteUtil.getInt(array1));
		 System.out.println(ByteUtil.getInt(array2));
	 }
	 
		/**
		 * 浮点转换为字节
		 * 
		 * @param f
		 * @return
		 */
		public static byte[] float2byte(float f) {
			
			// 把float转换为byte[]
			int fbit = Float.floatToIntBits(f);
			
			byte[] b = new byte[4];  
		    for (int i = 0; i < 4; i++) {  
		        b[i] = (byte) (fbit >> (24 - i * 8));  
		    } 
		    
		    // 翻转数组
			int len = b.length;
			// 建立一个与源数组元素类型相同的数组
			byte[] dest = new byte[len];
			// 为了防止修改源数组，将源数组拷贝一份副本
			System.arraycopy(b, 0, dest, 0, len);
			byte temp;
			// 将顺位第i个与倒数第i个交换
			for (int i = 0; i < len / 2; ++i) {
				temp = dest[i];
				dest[i] = dest[len - i - 1];
				dest[len - i - 1] = temp;
			}
		    
		    return dest;
		    
		}
		
		/**
		 * 字节转换为浮点
		 * 
		 * @param b 字节（至少4个字节）
		 * @param index 开始位置
		 * @return
		 */
		public static float byte2float(byte[] b, int index) {  
		    int l;                                           
		    l = b[index + 0];                                
		    l &= 0xff;                                       
		    l |= ((long) b[index + 1] << 8);                 
		    l &= 0xffff;                                     
		    l |= ((long) b[index + 2] << 16);                
		    l &= 0xffffff;                                   
		    l |= ((long) b[index + 3] << 24);                
		    return Float.intBitsToFloat(l);                  
		}

}
