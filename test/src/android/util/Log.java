package android.util;

public class Log {
	public static void i(String tag, String string) {
		System.out.println(tag+" "+string);
	}
	
	public static void d(String tag, String string) {
		System.out.println(tag+" "+string);
	}
	
	public static void e(String tag, String string) {
		System.err.println(tag+" "+string);
	}
}
