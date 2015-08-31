package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * ��ȡ���ж��ļ�ͷ��Ϣ
 * 
 * @author Sud
 * 
 */
public class GetTypeByHead {
	// �����ļ�ͷ��Ϣ-�ļ�ͷ��Ϣ
	public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
	static {
		// images
		mFileTypes.put("FFD8FF", "jpg");
		mFileTypes.put("89504E47", "png");
		mFileTypes.put("47494638", "gif");
		mFileTypes.put("49492A00", "tif");
		mFileTypes.put("424D", "bmp");
		//
		mFileTypes.put("41433130", "dwg"); // CAD
		mFileTypes.put("38425053", "psd");
		mFileTypes.put("7B5C727466", "rtf"); // �ռǱ�
		mFileTypes.put("3C3F786D6C", "xml");
		mFileTypes.put("68746D6C3E", "html");
		mFileTypes.put("44656C69766572792D646174653A", "eml"); // �ʼ�
		mFileTypes.put("D0CF11E0", "doc");
		mFileTypes.put("5374616E64617264204A", "mdb");
		mFileTypes.put("252150532D41646F6265", "ps");
		mFileTypes.put("255044462D312E", "pdf");
		mFileTypes.put("504B0304", "docx");
		mFileTypes.put("52617221", "rar");
		mFileTypes.put("57415645", "wav");
		mFileTypes.put("41564920", "avi");
		mFileTypes.put("2E524D46", "rm");
		mFileTypes.put("000001BA", "mpg");
		mFileTypes.put("000001B3", "mpg");
		mFileTypes.put("6D6F6F76", "mov");
		mFileTypes.put("3026B2758E66CF11", "asf");
		mFileTypes.put("4D546864", "mid");
		mFileTypes.put("1F8B08", "gz");
		mFileTypes.put("4D5A9000", "exe/dll");
		mFileTypes.put("75736167", "txt");
	}

	/**
	 * �����ļ�·����ȡ�ļ�ͷ��Ϣ
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return �ļ�ͷ��Ϣ
	 */
	public static String getFileType(String filePath) {
		//System.out.println(getFileHeader(filePath));
		//System.out.println(mFileTypes.get(getFileHeader(filePath)));
		return mFileTypes.get(getFileHeader(filePath));
	}

	/**
	 * �����ļ�·����ȡ�ļ�ͷ��Ϣ
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return �ļ�ͷ��Ϣ
	 */
	public static String getFileHeader(String filePath) {
		FileInputStream is = null;
		String value = null;
		try {
			is = new FileInputStream(filePath);
			byte[] b = new byte[4];
			/*
			 * int read() �Ӵ��������ж�ȡһ�������ֽڡ� int read(byte[] b) �Ӵ��������н���� b.length
			 * ���ֽڵ����ݶ���һ�� byte �����С� int read(byte[] b, int off, int len)
			 * �Ӵ��������н���� len ���ֽڵ����ݶ���һ�� byte �����С�
			 */
			is.read(b, 0, b.length);
			value = bytesToHexString(b);
		} catch (Exception e) {
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return value;
	}

	/**
	 * ��Ҫ��ȡ�ļ�ͷ��Ϣ���ļ���byte����ת����string���ͱ�ʾ
	 * 
	 * @param src
	 *            Ҫ��ȡ�ļ�ͷ��Ϣ���ļ���byte����
	 * @return �ļ�ͷ��Ϣ
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// ��ʮ�����ƣ����� 16���޷���������ʽ����һ�������������ַ�����ʾ��ʽ����ת��Ϊ��д
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
		System.out.println(builder.toString());
		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		/*final String fileType = getFileType("D:\\Ry4S_JAVA.dll");
		System.out.println(fileType);*/
		
		File[] files = new File("G:/pic").listFiles();
    	for (int i = 0; i < files.length; i++) {
    		System.out.println(files[i]);
    		String type = getFileType(files[i].getAbsolutePath());
    		System.out.println(type);
    	} 
    	
	}
}
