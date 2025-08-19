import java.io.IOException;
import java.net.Socket;

public class ClienteHandler implements Runnable {
    private Socket clientSocket = null;
    public ClienteHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                byte[] buffer = new byte[1024];

                clientSocket.getInputStream().read(buffer);

                var stringInput = new String(buffer);
                if (stringInput.isEmpty()) return;
                if (!stringInput.contains("PING\r\n")) return;

                var response = this.commandPing("");

                clientSocket.getOutputStream().write(response.getBytes());

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private String commandPing(String argument){
        if (argument.isEmpty()) return "+PONG\r\n";
        return argument+"\r\n";
    }
}
