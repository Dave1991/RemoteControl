package com.xanxus;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.Enumeration;
import java.util.StringTokenizer;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import json.JSONArray;
import json.JSONException;
import json.JSONObject;

public class RemoteServer {

	public static final int PORT = 43026;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out
					.println("-----------------------------------------------");
			// 打印java library的位置，把sigar的库放到对应路径下。
			String librayPath = System.getProperty("java.library.path");
			System.out
					.println("请再使用前把lib文件夹下的sigar-amd64-winnt.dll和sigar-x86-winnt.dll放在\n"
							+ librayPath.substring(0, librayPath.indexOf(";")));
			System.out
					.println("-----------------------------------------------");
			Enumeration allNetInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				// System.out.println(netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof InetAddress) {
						System.out.println("本机的IP = " + ip.getHostAddress());
					}
				}
			}
			System.out.println("等待客户端连接……");
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(new ServerRunnable(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void keyClick(Robot robot, int keyCode) {
		robot.keyPress(keyCode);
		robot.keyRelease(keyCode);
	}

	public static void keyClickWithShift(Robot robot, int keyCode) {
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(keyCode);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(keyCode);
	}

	public static void keyClickWithCtrlAlt(Robot robot, int keyCode) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(keyCode);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(keyCode);
	}

	public static void keyClickWithAlt(Robot robot, int keyCode) {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(keyCode);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(keyCode);
	}

	public static void keyClickWithCtrl(Robot robot, int keyCode) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(keyCode);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(keyCode);
	}

	public static void zoom(Robot robot, float zoomValue) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.mouseWheel((int) zoomValue);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public static void keyPress(Robot robot, int keyCode) {
		robot.keyPress(keyCode);
	}

	public static void keyRelease(Robot robot, int keyCode) {
		robot.keyRelease(keyCode);
	}

	public static void closeApplication(Robot robot) {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_F4);
	}

	public static void mouseMove(Robot robot, int dx, int dy) {
		robot.mouseMove(dx, dy);
	}

	public static void mouseClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void mouseRightClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}

	public static void mouseLeftLongPress(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void mouseLeftLongPressUp(Robot robot) {
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void mouseWheelSlide(Robot robot, int dy) {
		robot.mouseWheel((int) (dy * 0.3));
	}
}

class ServerRunnable implements Runnable {

	Socket socket = null;
	Robot robot = null;

	public ServerRunnable(Socket socket) throws AWTException {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		robot = new Robot();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String content = null;
		InputStream inputStream;
		try {
			inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			while ((content = bufferedReader.readLine()) == null)
				;
			// while ((content = bufferedReader.readLine()) != null) {
			// System.out.println(content);
			switch (content.substring(0, 2)) {
			// pageup键
			case "pu":
				RemoteServer.keyClick(robot, KeyEvent.VK_PAGE_UP);
				break;
			// pagedown键
			case "pd":
				RemoteServer.keyClick(robot, KeyEvent.VK_PAGE_DOWN);
				break;
			// 鼠标移动
			case "mm":
				StringTokenizer tokenizer = new StringTokenizer(content);
				tokenizer.nextToken();
				int dx = Integer.parseInt(tokenizer.nextToken());
				int dy = Integer.parseInt(tokenizer.nextToken());
				Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				mousePoint.x += dx * 1.8;
				mousePoint.y += dy * 1.8;
				RemoteServer.mouseMove(robot, mousePoint.x, mousePoint.y);
				break;
			// 左键
			case "mc":
				RemoteServer.mouseClick(robot);
				break;
			// 右键
			case "rc":
				RemoteServer.mouseRightClick(robot);
				break;
			// 左键按下
			case "lp":
				RemoteServer.mouseLeftLongPress(robot);
				break;
			// 左键释放
			case "lu":
				RemoteServer.mouseLeftLongPressUp(robot);
				break;
			// 滚轮移动
			case "ms":
				StringTokenizer tokenizer2 = new StringTokenizer(content);
				// 第一个是命令
				tokenizer2.nextToken();
				int dy2 = Integer.parseInt(tokenizer2.nextToken());
				RemoteServer.mouseWheelSlide(robot, dy2);
				break;
			// 幻灯片从当前页开始播放
			case "pl":
				RemoteServer.keyClickWithShift(robot, KeyEvent.VK_F5);
				break;
			// 幻灯片从头开始播放
			case "fp":
				RemoteServer.keyClick(robot, KeyEvent.VK_F5);
				break;
			// esc,幻灯片退出播放
			case "es":
				RemoteServer.keyClick(robot, KeyEvent.VK_ESCAPE);
				break;
			// 默认音乐音量增大
			case "vu":
				RemoteServer.keyClickWithCtrlAlt(robot, KeyEvent.VK_UP);
				break;
			// 默认音乐音量减小
			case "vd":
				RemoteServer.keyClickWithCtrlAlt(robot, KeyEvent.VK_DOWN);
				break;
			// 默认下一首歌曲
			case "ns":
				RemoteServer.keyClickWithCtrlAlt(robot, KeyEvent.VK_RIGHT);
				break;
			// 默认上一首歌曲
			case "ps":
				RemoteServer.keyClickWithCtrlAlt(robot, KeyEvent.VK_LEFT);
				break;
			// 默认音乐播放暂停
			case "pa":
				RemoteServer.keyClickWithCtrlAlt(robot, KeyEvent.VK_F5);
				break;
			// 缩放
			case "zm":
				tokenizer2 = new StringTokenizer(content);
				// 第一个是命令
				tokenizer2.nextToken();
				float zoomValue = Float.parseFloat(tokenizer2.nextToken());
				int wheelAnt = Math.round(((zoomValue - 1) * 5));// 放大倍数
				RemoteServer.zoom(robot, -wheelAnt);
				break;
			// 关闭当前程序
			case "f4":
				RemoteServer.keyClickWithAlt(robot, KeyEvent.VK_F4);
				break;
			// 任意键，便于扩展
			case "ay":
				StringTokenizer stringTokenizer = new StringTokenizer(content);
				stringTokenizer.nextToken();
				while (stringTokenizer.hasMoreTokens()) {
					String nextToken = stringTokenizer.nextToken();
					switch (nextToken) {
					case "ctrl":
						RemoteServer.keyPress(robot, KeyEvent.VK_CONTROL);
						break;
					case "shift":
						RemoteServer.keyPress(robot, KeyEvent.VK_SHIFT);
						break;
					case "alt":
						RemoteServer.keyPress(robot, KeyEvent.VK_ALT);
						break;
					default:
						RemoteServer.keyPress(robot,
								KeyConverter.convert(nextToken));
						break;

					}
				}
				stringTokenizer = new StringTokenizer(content);
				stringTokenizer.nextToken();
				while (stringTokenizer.hasMoreTokens()) {
					String nextToken = stringTokenizer.nextToken();
					switch (nextToken) {
					case "ctrl":
						RemoteServer.keyRelease(robot, KeyEvent.VK_CONTROL);
						break;
					case "shift":
						RemoteServer.keyRelease(robot, KeyEvent.VK_SHIFT);
						break;
					case "alt":
						RemoteServer.keyRelease(robot, KeyEvent.VK_ALT);
						break;
					default:
						RemoteServer.keyRelease(robot,
								KeyConverter.convert(nextToken));
						break;
					}
				}
				break;
			case "fe":
				// 获取本机的文件系统
				tokenizer2 = new StringTokenizer(content);
				tokenizer2.nextToken();
				// 第二个参数为路径
				String filePath = tokenizer2.nextToken();
				// 防止路径中带有空格，预先把空格变为问号
				filePath = filePath.replace("?", " ");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("fun", "explorer");
				// 获取根目录
				if (filePath.equals("/")) {
					jsonObject.put("hup", false);
					Sigar sigar = new Sigar();
					FileSystem[] fileSystems = sigar.getFileSystemList();
					JSONArray array = new JSONArray();
					for (int i = 0; i < fileSystems.length; i++) {
						FileSystem system = fileSystems[i];
						// 只读取本地磁盘
						if (system.getTypeName().equals("local")) {
							JSONObject json = new JSONObject();
							json.put("partitionName", system.getDevName());
							array.put(json);
						}
					}
					jsonObject.put("files", array);
				} else {
					File file = new File(filePath);
					File[] files = file.listFiles();
					jsonObject.put("hup", true);
					JSONArray array = new JSONArray();
					for (int i = 0; i < files.length; i++) {
						JSONObject json = new JSONObject();
						json.put("fileName", files[i].getName());
						json.put("filePath", files[i].getPath());
						json.put("isDirectory", files[i].isDirectory());
						json.put("lastModified", files[i].lastModified());
						array.put(json);
					}
					jsonObject.put("files", array);
				}
				socket.shutdownInput();
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println(jsonObject.toString());
				writer.flush();
				socket.shutdownOutput();
				writer.close();
				break;
			// 打开某个文件
			case "of":
				tokenizer2 = new StringTokenizer(content);
				tokenizer2.nextToken();
				// 第二个参数为路径
				filePath = tokenizer2.nextToken().replace("?", " ");
				File file = new File(filePath);
				Desktop.getDesktop().open(file);
				break;
			}
			if (!socket.isInputShutdown())
				socket.shutdownInput();
			if (!socket.isOutputShutdown()) {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("end");
				writer.flush();
				socket.shutdownOutput();
				writer.close();
			}
			socket.close();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}