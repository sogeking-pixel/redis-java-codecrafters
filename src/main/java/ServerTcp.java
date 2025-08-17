import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTcp {
    private InetSocketAddress inetSocketAddress = null;
    private ServerSocket serverSocket;

    public ServerTcp(String host, int port) {
        this.inetSocketAddress = new InetSocketAddress(host, port);
    }
    public ServerTcp(){
        this.inetSocketAddress = new InetSocketAddress(6379);
    }

    public void start() throws Exception {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(this.inetSocketAddress);

        while (true) {
            var clientSocket = serverSocket.accept();
            System.out.println("Client connected: "+ clientSocket.getInetAddress());

            this.handleClient(clientSocket);
        }

    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private void handleClient(Socket clientSocket) throws Exception {
        byte[] buffer = new byte[1024];
        var in = clientSocket.getInputStream();
        int bytesRead = in.read(buffer);


        var stringInput = new String(buffer, 0, bytesRead);

        String[] parts = stringInput.split("\r\n", 2);
        String command = parts[0];

        String argument = (parts.length > 1 ? parts[1] : "").trim();

        switch (command.toUpperCase()) {
            case "PING" -> {
                var response = this.commandPing (argument);
                this.sendResponse(response, clientSocket);
            }
            case "QUIT" -> {
                clientSocket.close();
                this.stop();
            }
            default -> {}
        };


    }

    private void sendResponse(String response, Socket clientSocket) throws Exception {
        var out = clientSocket.getOutputStream();
        out.write(response.getBytes());
        out.flush();
        out.close();
    }

    private String commandPing(String argument){
        if (argument.isEmpty()) return "+PONG\r\n";
        return argument;
    }



}
