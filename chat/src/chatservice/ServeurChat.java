package chatservice;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurChat {
    private static final int PORT = 12345;
    private static List<PrintWriter> clientOutputStreams = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Server is running...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
            	Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();

                System.out.println("New connection from " + clientSocket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler  implements Runnable  {
        private Socket clientsocket;
        private BufferedReader reader;

        public ClientHandler(Socket socket) {
        	try {
                clientsocket = socket;
                reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }  
        
        }
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String message) {
            for (PrintWriter writer : clientOutputStreams) {
                writer.println(message);
                writer.flush();
            }
        }
    }
}
