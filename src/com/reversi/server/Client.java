package com.reversi.server;

import java.net.*;
import java.io.*;
import java.util.*;


public class Client  {

    private ObjectInputStream sInput;		
    private ObjectOutputStream sOutput;		
    private Socket socket;

    private ClientGUI cg;

    private String server, username;
    private int port;

    Client(String server, int port, String username) {
        this(server, port, username, null);
    }


    Client(String server, int port, String username, ClientGUI cg) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.cg = cg;
    }


    public boolean start() {
        try {
            socket = new Socket(server, port);
        }

        catch(Exception ec) {
            display("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        try
        {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        new ListenFromServer().start();
        try
        {
            sOutput.writeObject(username);
        }
        catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        return true;
    }


    private void display(String msg) {
        if(cg == null)
            System.out.println(msg);      
        else
            cg.append(msg + "\n");		
    }

 
    void sendMessage() {
       
    }

    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {} // not much else I can do
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {} // not much else I can do
        try{
            if(socket != null) socket.close();
        }
        catch(Exception e) {} // not much else I can do

        // inform the GUI
        if(cg != null)
            cg.connectionFailed();

    }
    
    public static void main(String[] args) {
        // default values
        int portNumber = 7789;
        String serverAddress = "localhost";
        String userName = "Anonymous";

        // depending of the number of arguments provided we fall through
        switch(args.length) {
            // > javac chatting.Client username portNumber serverAddr
            case 3:
                serverAddress = args[2];
                // > javac chatting.Client username portNumber
            case 2:
                try {
                    portNumber = Integer.parseInt(args[1]);
                }
                catch(Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java chatting.Client [username] [portNumber] [serverAddress]");
                    return;
                }
                // > javac chatting.Client username
            case 1:
                userName = args[0];
                // > java chatting.Client
            case 0:
                break;
            // invalid number of arguments
            default:
                System.out.println("Usage is: > java chatting.Client [username] [portNumber] {serverAddress]");
                return;
        }

        Client client = new Client(serverAddress, portNumber, userName);
 
        if(!client.start())
            return;

        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print("> ");
            String msg = scan.nextLine();
          
        }
       
    }

    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {

        public void run() {
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    // if console mode print the message and add back the prompt
                    if(true) {
                        System.out.println(msg);
                        System.out.print("> ");
                    }
                    else {
                        cg.append(msg);
                    }
                }
                catch(IOException e) {
                    display("chatting.Server has close the connection: " + e);
                    if(cg != null)
                        cg.connectionFailed();
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch(ClassNotFoundException e2) {
                }
            }
        }
    }
}

