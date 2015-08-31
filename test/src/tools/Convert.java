package tools;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * ����ת��
 */
public class Convert
{
	/**
	 * �ַ�����
	 */
	public static final String CHAR_SET_NAME="GB2312";
	/**
	 * ʮ�������ַ���
	 */
	private static String hexString="0123456789ABCDEF";
	
	/**
	 * char���飬����0~9��A~Z
	 */
	private static char[] digits = new char[]
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * �������� : ��long��ת����16���Ʊ�����ʽ
	 */
	public String parse16(long num)
	{
		return parse16(num, 16);
	}

	/**
	 * �������� : ��int��ת����16���Ʊ�����ʽ�������д�8λ������ox,ת������ַ���10λ
	 */
	public static String parse16(int num)
	{
		return parse16(num, 8);
	}

	/**
	 * �������� : ��short��ת����16���Ʊ�����ʽ
	 */
	public static String parse16(short num)
	{
		return parse16(num, 4);
	}

	/**
	 * �������� : ��byte��ת����16���Ʊ�����ʽ
	 */
	public static String parse16(byte num)
	{
		return parse16(num, 2);
	}

	/**
	 * �������� : ����ֵת����ָ�����ȵ�16���Ʊ�����ʽ
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
	 * longת��Ϊbyte[]
	 */
	public static byte[] getByteArray(final long value) {
		return getByteArray(value, 8);
	}
	/**
	 * intת��Ϊbyte[]
	 */
	public static byte[] getByteArray(final int value) {
		return getByteArray(value, 4);
	}
	
	/**
	 * shortת��Ϊbyte[]
	 */
	public static byte[] getByteArray(final short value) {
		return getByteArray(value, 2);
	}
	
	/**
	 * ת��Ϊbyte[],���
	 * byteNum�ֽڸ��� 
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
	 * ���byte[]ת��ΪINT,
	 * offset��ʼbyte
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
	 * С��byte[]ת��Ϊshort,
	 * offset��ʼbyte
	 */
	public static short byteArray2short(byte[] b,int offset) {
	       return (short)byteArray2Long(b,2,offset);
	 }
	/**
	 * С��byte[]ת��ΪINT,
	 * offset��ʼbyte
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
	 * С��byte[]ת��ΪLong,
	 * offset��ʼbyte
	 */
	public static long byteArray2Long(byte[] b,int offset) {
	       return byteArray2Long(b,8,offset);
	 }
	/**
	 * С��byte[]ת��ΪLong,
	 * offset��ʼbyte
	 * byteNum �ֽڸ���
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
	 * ���byte[]ת��Ϊshort,
	 * offset��ʼbyte
	 */
	public static short byteArrayToshort(byte[] b,int offset) {
	       return (short)byteArrayToLong(b,2,offset);
	 }

	/**
	 * ���byte[]ת��ΪLong,
	 * offset��ʼbyte
	 * byteNum �ֽڸ���
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
	 * �������� : ��byte��������ת����ʮ�����Ƶ��ַ���
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
	 * �������� : ��ʮ�����Ƶ��ַ���ת����byte����
	 */
	public byte[] getByteArray(String hexString)
	{
		return new BigInteger(hexString, 16).toByteArray();
	}
	
	/**
	 * �������� : ���ַ��������16��������,�����������ַ����������ģ� <p>
	 */
	public static String encode(String str)
	{
		// ����Ĭ�ϱ����ȡ�ֽ�����
		byte[] bytes;
		StringBuilder sb=null;
		try
		{
			bytes = str.getBytes(CHAR_SET_NAME);
			sb=new StringBuilder(bytes.length*2);
			
			// ���ֽ�������ÿ���ֽڲ���2λ16��������
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

		// ����Ĭ�ϱ����ȡ�ֽ�����
		byte[] bytes;
		StringBuilder sb=null;
		try
		{
			bytes = str.getBytes(CHAR_SET_NAME);
			sb=new StringBuilder(bytes.length*2);
			
			//edit by wg 2012-10-31 ̫����ȡ
			int rLen = length * 2 > bytes.length*2 ? bytes.length : length;
			
			// ���ֽ�������ÿ���ֽڲ���2λ16��������
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
	 *  �������� : ��16�������ֽ�����ַ���,�����������ַ����������ģ�<p>
	 */
	public static String decode(String bytes)
	{
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
	
		// ��ÿ2λ16����������װ��һ���ֽڣ� ָ��gb2312����
		for(int i=0;i<bytes.length();i+=2)
		{
			//edit by wg 2012-9-5 ��0����
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
	 *  �������� : ��ָ���ַ���src����ÿ�����ַ��ָ�ת��Ϊ16 ������ʽ  <p>
	 *  �磺"2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,0xD9} <p>
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
	 *  �������� :  ������ʮ�����Ƶ��ֽ����ϲ���һ���ֽڣ�'a','b'='0xab'��<p>
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
	 *  �������� :  ���ֽ�����ת����ʮ�������ַ���(�磺��10,11,12,13,14,15��=="0a,0b,0d,0c,0e,0f")<p>
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
	 *  �������� : ���ַ�ת����ʮ�������ֽ�<p>
	 */
	public byte charToByte(char c) 
	{
		return (byte) "0123456789ABDCEF".indexOf(c);
	}
	
	/**
	 * �������� : ���ַ�������ת����ָ�����ȵ��ֽ����� ����˵����
	 * @param data
	 *            �ַ�������
	 * @param length
	 *            �ַ�������
	 * @return ָ�����ȵ��ֽ����� ����ֵ�� byte[] �ֽ����� 
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
	 *  �������� : ���ݿ�ʼ�����±�ͽ��������±꣬�õ��ֽ������е����� <p>
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
	 * �������� : ��ByteBuffer�е����ݱ��浽�ֽ�����
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
	 *  �������� :  ����������ϲ���һ������<p>
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
	 *  �������� : ��ʮ�����Ƶ��ַ���ת����ʮ���Ƶ�����ֵ<p>
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
	 *  �������� : ���ַ���ʱ��ת�������� <p>
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
	 *  �������� : ��BCD�ַ���ʱ��ת�������� 141023000000
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
	 *  �������� : �����ں�ʱ��ת�ɳ�����λ���ַ���  ��4�¾�Ҫת�ɡ�04��
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
	 *  �������� : �ַ���ת��16�����ַ���
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
	 *  �������� :  �ֽ�����ת��10�����ַ��� {99,145,66,71,1} TO "99145667101"
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

