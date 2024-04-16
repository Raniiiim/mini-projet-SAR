package chatservice;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        		
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
          
            
            System.out.println("Connected to chat server. Type your messages:");

        		
        		// Le thread est utilisé pour la lecture des messages provenant du serveur.
            Thread receivingThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.err.println("Error receiving message: " + e.getMessage());
                }
            });
            receivingThread.start();
            
            // La réception des messages de l'utilisateur et leur envoi au serveur.
            
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = userInputReader.readLine()) != null) {
                writer.println(userInput);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
