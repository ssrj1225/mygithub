package tools;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

public class NetServer {
	private static final String TAG = "NetServer";

	private int mPort = 0;
	private ServerSocket mServerSocket;
	private List<Socket> mConnectList = new ArrayList<Socket>();
	private ExecutorService mExecutorService = null; // 线程池

	public int setPort(int iPort) {
		mPort = iPort;
		return mPort;
	}

	public int start() {
		try {
			mServerSocket = new ServerSocket(mPort);
			mExecutorService = Executors.newCachedThreadPool(); // 创建线程池

			while (true) {
				Socket sConnect = mServerSocket.accept();
				mConnectList.add(sConnect);

				Log.i(TAG, "accept new client addr:" + sConnect.getLocalAddress()
						+ ", connects total=" + mConnectList.size());

				mExecutorService.execute(new Service(sConnect)); // 新开线程处理新链接
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		// return 0;
	}

	class Service implements Runnable {
		private Socket mConnectSocket;
		private BufferedInputStream mBufIn = null;

		public Service(Socket sConnect) {
			mConnectSocket = sConnect;
			try {
				mBufIn = new BufferedInputStream(mConnectSocket.getInputStream());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			byte[] buf = new byte[1024];
			int iRead = 0;

			try {
				while (true) {
					iRead = mBufIn.read(buf);
					if (mListener != null) {
						mListener.onRecv(mConnectSocket, buf, iRead);
					}

					if (iRead == -1) {
						mConnectList.remove(mConnectSocket);
						Log.i(TAG, "client disconnect! addr:" + mConnectSocket.getLocalAddress()
								+ ", connects total=" + mConnectList.size());
						mConnectSocket.close();
						break;
					}
					
					send(mConnectSocket, buf, iRead);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int send(Socket s, byte[] buf, int len) {
		try {
			s.getOutputStream().write(buf, 0, len);
			s.getOutputStream().flush();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int stop() {
		try {
			for (Socket sConnect : mConnectList) {
				sConnect.close();
			}
			mServerSocket.close();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	// call back ///////////////////////////////
	private OnRecvCallback mListener;

	public void setListener(OnRecvCallback listener) {
		mListener = listener;
	}

	public static interface OnRecvCallback {
		public void onRecv(Socket s, byte[] buf, int len);
	}

	public static void main(String[] args) {
		
		ThirdPartyPlatform.getInstance();
		
		NetServer server = new NetServer();
		server.setPort(8082);
		server.start();
		
	}
}
