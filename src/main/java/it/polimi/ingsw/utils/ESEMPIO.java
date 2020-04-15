package it.polimi.ingsw.utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class ESEMPIO {


    // must implement Serializable in order to be sent
    public class Message implements Serializable{
        private final String text;

        public Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }



    public class Client {

        public void main(String[] args) throws IOException {
            // need host and port, we want to connect to the ServerSocket at port 7777
            Socket socket = new Socket("localhost", 7777);
            System.out.println("Connected!");

            // get the output stream from the socket.
            OutputStream outputStream = socket.getOutputStream();
            // create an object output stream from the output stream so we can send an object through it
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // make a bunch of messages to send.
            List<Message> messages = new ArrayList<>();
            messages.add(new Message("Hello from the other side!"));
            messages.add(new Message("How are you doing?"));
            messages.add(new Message("What time is it?"));
            messages.add(new Message("Hi hi hi hi."));

            System.out.println("Sending messages to the ServerSocket");
            objectOutputStream.writeObject(messages);

            System.out.println("Closing socket and terminating program.");
            socket.close();
        }
    }




    public class Server {
        public void main(String[] args) throws IOException, ClassNotFoundException {
            // don't need to specify a hostname, it will be the current machine
            ServerSocket ss = new ServerSocket(7777);
            System.out.println("ServerSocket awaiting connections...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Connection from " + socket + "!");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            // read the list of messages from the socket
            List<Message> listOfMessages = (List<Message>) objectInputStream.readObject();
            System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
            // print out the text of every message
            System.out.println("All messages:");
            //listOfMessages.forEach((msg)-> System.out.println(msg.getText()));

            System.out.println("Closing sockets.");
            ss.close();
            socket.close();
        }
    }

// Server output
/*
ServerSocket awaiting connections...
Connection from Socket[addr=/127.0.0.1,port=62360,localport=7777]!
Received [4] messages from: Socket[addr=/127.0.0.1,port=62360,localport=7777]
All messages:
Hello from the other side!
How are you doing?
What time is it?
Hi hi hi hi.
Closing sockets.
*/

// Client output
/*
Connected!
Sending messages to the ServerSocket
Closing socket and terminating program.
*/
}
