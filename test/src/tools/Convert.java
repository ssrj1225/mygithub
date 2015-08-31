package tools;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * 类型转换
 */
public class Convert
{
	/**
	 * 字符编码
	 */
	public static final String CHAR_SET_NAME="GB2312";
	/**
	 * 十六进制字符串
	 */
	private static String hexString="0123456789ABCDEF";
	
	/**
	 * char数组，包含0~9，A~Z
	 */
	private static char[] digits = new char[]
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 功能描述 : 将long型转换成16进制表现形式
	 */
	public String parse16(long num)
	{
		return parse16(num, 16);
	}

	/**
	 * 功能描述 : 将int型转换成16进制表现形式，函数中传8位，加上ox,转换后的字符共10位
	 */
	public static String parse16(int num)
	{
		return parse16(num, 8);
	}

	/**
	 * 功能描述 : 将short型转换成16进制表现形式
	 */
	public static String parse16(short num)
	{
		return parse16(num, 4);
	}

	/**
	 * 功能描述 : 将byte型转换成16进制表现形式
	 */
	public static String parse16(byte num)
	{
		return parse16(num, 2);
	}

	/**
	 * 功能描述 : 将数值转换成指定长度的16进制表现形式
	 */
	public static String parse16(long num, int index)
	{
		char[] chs = new char[index + 2];
		chs[0] = '0';
		chs[1] = 'x';
		for (int i = index - 1; i >= 0; i--)
		{
			chs[index + 1 - i] = digits[(int) ((num >>> (i * 4) & 0x0f))];
		}
		return new String(chs);
	}

	/**
	 * long转化为byte[]
	 */
	public static byte[] getByteArray(final long value) {
		return getByteArray(value, 8);
	}
	/**
	 * int转化为byte[]
	 */
	public static byte[] getByteArray(final int value) {
		return getByteArray(value, 4);
	}
	
	/**
	 * short转化为byte[]
	 */
	public static byte[] getByteArray(final short value) {
		return getByteArray(value, 2);
	}
	
	/**
	 * 转化为byte[],大端
	 * byteNum字节个数 
	 */
	private static byte[] getByteArray(final long value, int byteNum) {
		byte[] byteArray = new byte[byteNum];

		for (int n = 0; n < byteNum; n++){
			byteArray[n] = (byte)((value >>> (((byteNum-1) - n) * 8)) & 0xFF);
		}

		return (byteArray);
	}
		
	public static byte[] intToByteArray1(int i) {   
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
	}
	
	/**
	 * 大端byte[]转化为INT,
	 * offset起始byte
	 */
	public static int byteArrayToInt(byte[] b, int offset) {
	       int value= 0;
	       for (int i = 0; i < 4; i++) {
	           int shift= (4 - 1 - i) * 8;
	           value +=(b[i + offset] & 0x000000FF) << shift;
	       }
	       return value;
	 }
	
	/**
	 * 小端byte[]转化为short,
	 * offset起始byte
	 */
	public static short byteArray2short(byte[] b,int offset) {
	       return (short)byteArray2Long(b,2,offset);
	 }
	/**
	 * 小端byte[]转化为INT,
	 * offset起始byte
	 */
	public static int byteArray2Int(byte[] b, int offset) {
	       /*int value= 0;
	       for (int i = 0; i < 4; i++) {
	           int shift= i * 8;
	           value +=(b[i + offset] & 0x000000FF) << shift;
	       }
	       return value;*/
		return (int)byteArray2Long(b,4,offset);
	 }
	/**
	 * 小端byte[]转化为Long,
	 * offset起始byte
	 */
	public static long byteArray2Long(byte[] b,int offset) {
	       return byteArray2Long(b,8,offset);
	 }
	/**
	 * 小端byte[]转化为Long,
	 * offset起始byte
	 * byteNum 字节个数
	 */
	public static long byteArray2Long(byte[] b, int byteNum, int offset) {
	       long value= 0;
	       for (int i = 0; i < byteNum; i++) {
	           int shift= i * 8;
	           value +=(b[i + offset] & 0x000000FF) << shift;
	       }
	       return value;
	 }
	
	/**
	 * 大端byte[]转化为short,
	 * offset起始byte
	 */
	public static short byteArrayToshort(byte[] b,int offset) {
	       return (short)byteArrayToLong(b,2,offset);
	 }

	/**
	 * 大端byte[]转化为Long,
	 * offset起始byte
	 * byteNum 字节个数
	 */
	public static long byteArrayToLong(byte[] b, int byteNum, int offset) {
	       long value= 0;
	       for (int i = 0; i < byteNum; i++) {
	           int shift= (byteNum-1-i) * 8;
	           value += (long)(b[i + offset] & 0x000000FF) << shift;
	       }
	       return value;
	 }
	
	/**
	 * 功能描述 : 把byte类型数组转换成十六进制的字符串
	 */
	public static String getHexString(byte[] b)
	{
		String result = "";
		for (int i = 0; i < b.length; i++)
		{
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	/**
	 * 功能描述 : 把十六进制的字符串转换成byte数组
	 */
	public byte[] getByteArray(String hexString)
	{
		return new BigInteger(hexString, 16).toByteArray();
	}
	
	/**
	 * 功能描述 : 将字符串编码成16进制数字,适用于所有字符（包括中文） <p>
	 */
	public static String encode(String str)
	{
		// 根据默认编码获取字节数组
		byte[] bytes;
		StringBuilder sb=null;
		try
		{
			bytes = str.getBytes(CHAR_SET_NAME);
			sb=new StringBuilder(bytes.length*2);
			
			// 将字节数组中每个字节拆解成2位16进制整数
			for(int i=0;i<bytes.length;i++)		
			{
		
				sb.append(hexString.charAt((bytes[i]&0xf0)>>4));			
				sb.append(hexString.charAt((bytes[i]&0x0f)>>0));		
			}			
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return sb.toString();
	}

	//add by wg 2012-9-7
	public static String encode(String str, int length)
	{

		// 根据默认编码获取字节数组
		byte[] bytes;
		StringBuilder sb=null;
		try
		{
			bytes = str.getBytes(CHAR_SET_NAME);
			sb=new StringBuilder(bytes.length*2);
			
			//edit by wg 2012-10-31 太长截取
			int rLen = length * 2 > bytes.length*2 ? bytes.length : length;
			
			// 将字节数组中每个字节拆解成2位16进制整数
			for(int i=0;i<rLen;i++)		
			{		
				sb.append(hexString.charAt((bytes[i]&0xf0)>>4));			
				sb.append(hexString.charAt((bytes[i]&0x0f)>>0));		
			}					
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (sb.length() < length * 2){
			sb.append("00");
		}	
		return sb.toString();
	}

	//add by wg 2012-9-7
	public String getFixLength(String str, int length)
	{
		StringBuilder sb= new StringBuilder(str);		
		while (sb.length() < length * 2){
			sb.append("00");
		}	
		return sb.toString();
	}

	//add by wg 2012-9-7
	public static String getNumBCDFixLength(Integer num, int length)
	{
		StringBuilder sb= new StringBuilder(num.toString());
		
		while (sb.length() < length){
			sb.insert(0, 0);
		}	
		return sb.toString();
	}
	
	/**
	 *  功能描述 : 将16进制数字解码成字符串,适用于所有字符（包括中文）<p>
	 */
	public static String decode(String bytes)
	{
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
	
		// 将每2位16进制整数组装成一个字节， 指定gb2312编码
		for(int i=0;i<bytes.length();i+=2)
		{
			//edit by wg 2012-9-5 遇0结束
			if (bytes.charAt(i) == '0' && bytes.charAt(i + 1) == '0')
			{
				break;
			}
			baos.write(((byte)(hexString.indexOf(Character.toUpperCase(bytes.charAt(i)))<<4) | (byte)(hexString.indexOf(Character.toUpperCase(bytes.charAt(i+1))))));
		}
		try {
			return new String(baos.toByteArray(), "GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 *  功能描述 : 将指定字符串src，以每两个字符分割转换为16 进制形式  <p>
	 *  如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,0xD9} <p>
	 */
	public static byte[] getHex2Bytes(String hexStr)
	{
		byte[] ret = new byte[hexStr.length()/2];
		byte[] tmp = hexStr.getBytes();

		for (int i = 0; i < hexStr.length()/2; ++i){
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	
	/**
	 *  功能描述 :  将两个十六进制的字节数合并成一个字节（'a','b'='0xab'）<p>
	 */
	public static byte uniteBytes(byte src0, byte src1) 
	{
		byte _b0 = (byte) hexString.indexOf(Character.toUpperCase(src0));
		//byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
		_b0 = (byte) (_b0 << 4);
		
		byte _b1 = (byte) hexString.indexOf(Character.toUpperCase(src1));
		//byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}
	
	/**
	 *  功能描述 :  讲字节数组转换成十六进制字符串(如：“10,11,12,13,14,15”=="0a,0b,0d,0c,0e,0f")<p>
	 */
	public static String bytesToHexString(byte[] src)
	{
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return "";
		}
		for (int i = 0; i < src.length; i++) 
		{
			int v = src[i] & 0xFF;	
			String hv = Integer.toHexString(v);	
			if (hv.length() < 2) 			{
				stringBuilder.append(0);	
			}	
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/**
	 *  功能描述 : 吧字符转换成十六进制字节<p>
	 */
	public byte charToByte(char c) 
	{
		return (byte) "0123456789ABDCEF".indexOf(c);
	}
	
	/**
	 * 功能描述 : 将字符串数据转换成指定长度的字节数组 参数说明：
	 * @param data
	 *            字符串数据
	 * @param length
	 *            字符串长度
	 * @return 指定长度的字节数组 返回值： byte[] 字节数组 
	 *         <p>
	 */
	public byte[] DataToByte(String data, int length)
	{
		byte[] b = new byte[length];
		byte[] dataBytes = null;
		try
		{
			dataBytes = data.getBytes(CHAR_SET_NAME);
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < length; i++)
		{
			if (i < dataBytes.length)
			{
				b[i] = dataBytes[i];
			}
		}
		return b;
	}
	
	/**
	 *  功能描述 : 根据开始索引下标和结束索引下标，得到字节数组中的数据 <p>
	 */
	public byte[] datatoByte(byte[] data,int start,int end)
	{
		byte [] content=new byte[end-start];
		System.out.println("----------content.length-------------"+content.length);
		for(int i=0;i<content.length;i++)
		{
			content[i]=data[i+start];
		}
		
		return content;
	}
	
	/**
	 * 功能描述 : 将ByteBuffer中的数据保存到字节数组
	 */
	public byte[] ByteBufferToByte(ByteBuffer buffer)
	{
		byte[] bytes = new byte[buffer.position()];
		for (int i = 0; i < buffer.position(); i++)
		{
			bytes[i] = buffer.get(i);
		}
		return bytes;
	}
	
	/**
	 *  功能描述 :  将两个数组合并成一个数组<p>
	 */
	public static byte[] arrayMerge(byte [] array1,byte [] array2)
	{
		byte[] array=new byte[array1.length+array2.length];
		
		for(int i=0;i<array1.length;i++)
		{
			array[i]=array1[i];
		}
		
		for(int i=0;i<array2.length;i++)
		{
			array[array1.length+i]=array2[i];
		}
		
		return array;
	}
	
	/**
	 *  功能描述 : 把十六进制的字符串转换成十进制的整数值<p>
	 */
	public static int decStrToInt(String str)
	{
		str=str.toUpperCase();
		int num=0;
		int size=str.length();
		for(int i=0;i<size;i++)
		{
			if(i==0)
			{
				num=hexString.indexOf(str.substring(str.length()-1, str.length()));
			}
			else
			{
				int temp=hexString.indexOf(str.substring(str.length()-1, str.length()));
				for(int j=0;j<i;j++)
				{
					temp= temp *16;
					
				}
				num+=temp;
			}
			str=str.substring(0, str.length()-1);
		}
		return num;
	}
	
	/**
	 *  功能描述 : 把字符串时间转换成整型 <p>
	 */
	public static long dateStringToLong(String date)
	{
		String [] datePart=date.split(" ");
		String [] dateBefore=datePart[0].split("-");
		String [] dateAfter=datePart[1].split(":");
		Calendar calendar=Calendar.getInstance();
		calendar.set(Integer.valueOf(dateBefore[0]), Integer.valueOf(dateBefore[1]), Integer.valueOf(dateBefore[2]), 
				Integer.valueOf(dateAfter[0]), Integer.valueOf(dateAfter[1]));
		return calendar.getTimeInMillis();
	}
	/**
	 *  功能描述 : 把BCD字符串时间转换成整型 141023000000
	 */
	public static long BCDDateStringToLong(String date)
	{		
		try {
			Calendar calendar=Calendar.getInstance();
			calendar.set(Integer.valueOf(date.substring(0, 2))+2000, 
					Integer.valueOf(date.substring(2, 4))-1, 
					Integer.valueOf(date.substring(4, 6)), 
					Integer.valueOf(date.substring(6, 8)), 
					Integer.valueOf(date.substring(8, 10)),
					Integer.valueOf(date.substring(10,12)));			
			return calendar.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 *  功能描述 : 将日期和时间转成长度两位的字符串  如4月就要转成“04”
	 */
	public String dateTimetoString(int datetime)
	{
		if (String.valueOf(datetime).length()==1)
		{
			return "0"+datetime;
		}
		return String.valueOf(datetime);
	}
	
	/**
	 *  功能描述 : 字符串转成16进制字符串
	 */
	public String charStringToHexString(String charString){
	    String newString ="";
	    if(charString==null||"".equals(charString)){
	    	return newString;
	    }
	    
	    newString = bytesToHexString(charString.getBytes());	    
	    return newString;
	}
	
	/**
	 *  功能描述 :  字节数组转成10进制字符串 {99,145,66,71,1} TO "99145667101"
	 */
	public String BytesToDecString(byte[] b){
	    String s = "";	    
	    if(b!=null&&b.length>0){
			for(int i=0;i<b.length;i++){
			    String ss = ((int)b[i]) + "";
			    if(ss.length()==1){
			    	ss = "0" + ss;
			    }		    
			    s = s + ss;
			}
	    } 
	    return s;
	}
	
	public static String getSerialNumber(int serialNumber){
		return parse16(serialNumber).substring(6, 10);
	}
	
	public static String getHexWord(int data){
		return parse16(data).substring(6, 10);
	}
	
	public static String getHexByte(byte data){
		return parse16(data).substring(2, 4);
	}
	
	public static String getHexDWord(int data){
		return parse16(data).substring(2, 10);
	}
	
	public static void appendBytes(byte[] src, byte[] dest, int offset) {
		for (int i = 0; i < src.length; i++) {
			dest[i+offset] = src[i];
		}
	}
}

