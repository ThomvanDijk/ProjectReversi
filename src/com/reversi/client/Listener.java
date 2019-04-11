package com.reversi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Listener implements Runnable {

	private Client client;

	// IO streams
	private BufferedWriter toServer;
	private BufferedReader fromServer;

	private Socket socket;

	public Listener(Client client, String address) {
		this.client = client;

		try {
			Socket socket = new Socket(address, 7789);

			toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//System.out.println("Client connected to " + socket.getRemoteSocketAddress() + "\n");
		} catch (IOException e) {
			System.out.println(e.toString() + "\n");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		super.finalize();
	}

	public void sendMessage(String textToSend) throws IOException {
		toServer.write(textToSend);
		toServer.newLine();
		toServer.flush();
	}

	@Override
	public void run() {
		String messageServer;

		try {
			// Listening to server
			while (!Thread.currentThread().isInterrupted()) {
				// Receive text from the server
				messageServer = fromServer.readLine();
				client.notifyController(messageServer);
			}
		} catch (IOException e) {
			System.out.println(e.toString() + "\n");
		}
		
		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
