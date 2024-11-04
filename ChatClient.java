import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        try (Socket socket = new Socket(serverAddress, 12345)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Read messages from the server in a separate thread
            new Thread(new IncomingReader()).start();

            Scanner scanner = new Scanner(System.in);
            String userInput;

            while (true) {
                userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class IncomingReader implements Runnable {
        public void run() {
            String messageFromGroupChat;
            try {
                while ((messageFromGroupChat = in.readLine()) != null) {
                    System.out.println(messageFromGroupChat);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
