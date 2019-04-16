package com.reversi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Listener makes a connection with the server, sends messages to the server and
 * listens to incoming messages. All incoming messages are send to the Client.
 * 
 * @author Thom van Dijk
 * @version 1.0
 * @since 16-04-2019
 */
public class Listener implements Runnable {

	private Client client;

	// IO streams
	private BufferedWriter toServer;
	private BufferedReader fromServer;

	private Socket socket;

	/**
	 * This constructor tries to set up a connection to a certain server.
	 * 
	 * @param client  A reference to call functions in Client.
	 * @param address The server address as a String.
	 */
	public Listener(Client client, String address) {
		this.client = client;

		try {
			Socket socket = new Socket(address, 7789);

			toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// System.out.println("Client connected to " + socket.getRemoteSocketAddress() + "\n");
		} catch (IOException e) {
			System.out.println(e.toString() + "\n");
		}
	}

	/**
	 * Finalize is called at the end of this thread. Used to close the socket.
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		super.finalize();
	}

	/**
	 * Sends a message to the server.
	 * 
	 * @param textToSend The String containing the exact command for the server.
	 */
	public void sendMessage(String textToSend) throws IOException {
		toServer.write(textToSend);
		toServer.newLine();
		toServer.flush();
	}

	/**
	 * Overriding run in Runnable where incoming messages from the server are send
	 * to the Client.
	 */
	@Override
	public void run() {
		String messageServer;

		try {
			// Listening to server
			while (!Thread.currentThread().isInterrupted()) {
				// Receive text from the server and send it to the client
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
