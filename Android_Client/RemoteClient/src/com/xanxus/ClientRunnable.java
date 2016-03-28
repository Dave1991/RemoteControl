package com.xanxus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ClientRunnable implements Runnable {

	Socket socket = null;
	OutputStream outputStream = null;
	PrintWriter printWriter = null;
	String message = null;
	BufferedReader reader = null;
	Handler handler = null;

	public ClientRunnable(String message, Handler handler) {
		this.message = message;
		this.handler = handler;
	}

	public void renewSocket() {
		try {
			socket = new Socket(MainActivity.serverIP, MainActivity.PORT);
			outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream);
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeSocket() {
		try {
			printWriter.close();
			outputStream.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		renewSocket();
		printWriter.println(message);
		printWriter.flush();
		String content;
		try {
			socket.shutdownOutput();
			while ((content = reader.readLine()) == null) {

			}
			reader.close();
			if (!content.equals("end")) {
				Message msg = new Message();
				msg.obj = content;
				msg.what = 0x110;
				handler.sendMessage(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeSocket();
	}

}
