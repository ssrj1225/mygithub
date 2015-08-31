package tools;

import android.util.Log;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 
 * @description 第三方平台
 * @author surj
 * @update 2015年7月22日 下午5:32:56
 */
public class ThirdPartyPlatform{

	private final String TAG = this.getClass().getSimpleName();
	private static ThirdPartyPlatform instance = null;

	private Socket socket;
	private int heart = 0;

	private String host = "192.168.1.211";//
	private int port = 8082;
	private String authKey = "313233343536";//鉴权码123456
	
	private long interval = 50000;//位置汇报间隔
	
	private ThirdPartyPlatform() {
		new CommThread().start();
		new RecvThread().start();
		new SendThread().start();
	}

	public static ThirdPartyPlatform getInstance() {
		if (instance == null) {
			instance = new ThirdPartyPlatform();
		}
		return instance;
	}

	class CommThread extends Thread {
		@Override
		public void run() {
			super.run();
			Log.i("ThirdParty", "CommThread start");
						
			while (!isStop) {
				try {
					sleep(5000);  
				} catch (Exception e) {
				}
				
				// onnect
				if (socket == null || !socket.isConnected()) {
					try {
						Log.d("ThirdParty", "to connect " + host + ":" + port);
						socket = new Socket();
						socket.connect(new InetSocketAddress(host, port), 15000);
						socket.setSendBufferSize(500000);

						Log.d("ThirdParty", host + " isConnected=" + socket.isConnected());
						
						String msg = "7e010200060111111111110001313233343536d87e";
	
						write(Convert.getHex2Bytes(msg));
						
					} catch (SocketTimeoutException e) {
						Log.e("ThirdParty", "SocketTimeoutException");
						socket = null;
					} catch (Exception e) {
						e.printStackTrace();
						socket = null;
						Log.e("ThirdParty", "Exception:" + e.getMessage());
					}
				}
			}//while

			if (socket == null) {
				
			}
			else if (socket != null || !socket.isConnected()) {
				try {
					socket.close();
					socket.getInputStream().close();
					socket.getOutputStream().close();
					Log.e("ThirdParty", "socket close");
				} catch (Exception e) {
					e.printStackTrace();
				}
				socket = null; 
			}
			
			Log.i("ThirdParty", "CommThread end");
		}
	}

	class RecvThread extends Thread {
		@Override
		public void run() {
			super.run();
			int sleep = 1000;
			Log.d("ThirdParty", "RecvThread start getId=" + getId());
			while (!isStop) {
				if (socket != null && socket.isConnected()) {
					try {
						int count = socket.getInputStream().available();
						if (count > 0) {
							byte[] b = new byte[count];
							socket.getInputStream().read(b);
							Log.i("ThirdParty R", Convert.bytesToHexString(b));
							heart = 0;
						} else {
							// Log.d("ThirdParty Recv", "heart="+heart);
							if (sleep * heart++ > 120000) {//超时时间
								heart = 0;
								Log.e("ThirdParty R", "heart stop to reconnect==========================");
								try {
									socket.close();
								} catch (Exception e) {
								}
								socket = null;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				try {
					sleep(sleep);
				} catch (Exception e) {
				}
			}
			
			Log.d("ThirdParty", "RecvThread end getId=" + getId());
		}
	}

	class SendThread extends Thread {
		@Override
		public void run() {
			super.run();
			Log.d("ThirdParty", "SendThread start getId=" + getId());
			try {
				sleep(10000);
			} catch (Exception e) {
			}
			
			//String msg = "7e010200060145469525690001313233343536d87e";
			//String msg = "7e0200007901454695256900020000012000040003018e600d071b931200000000000015071316434901040000000002020000030200002504000001002b0400000000300100310100e0020072e13100000000000000000000000000000000000000000000000000000000000000000000000000000000000000020000000000e20400008ed2147e";
			
			while (!isStop) {
				try {
					write(ByteUtil.getBytes(System.currentTimeMillis()));					
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					/*interval = AlertingHandlerThread.getAlertingHandlerThread().getCurReportInterval();
					if (interval < 30000) {
						interval = 30000;
					}*/
					sleep(interval);
				} catch (Exception e) {
				}
			}
			
			Log.d("ThirdParty", "SendThread end getId=" + getId());
		}
	}

	private synchronized void write(byte[] bytes) {
		try {
			// write socket
			if (socket != null && socket.isConnected() && bytes != null) {
				socket.getOutputStream().write(bytes);
				socket.getOutputStream().flush();
				Log.i("ThirdParty send", Convert.bytesToHexString(bytes));				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ThirdParty S", "write Exception reconnect");
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			socket = null;
		}
	}

	private boolean isStop = false;

	public void stop() {
		isStop = true;
	}

	private void reStart() {
		new Thread(new Runnable() {			
			@Override
			public void run() {				
				try {
					stop();
					Thread.sleep(20000);
					isStop = false;
					new CommThread().start();
					new RecvThread().start();
					new SendThread().start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void setHostAndPort(String host ,int port) {}
	
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
}
