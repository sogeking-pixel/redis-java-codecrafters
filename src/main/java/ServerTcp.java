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
        var clientSocket = serverSocket.accept();
        while (true) {

            System.out.println("Client connected: "+ clientSocket.getInetAddress());

            this.handleClient(clientSocket);
        }

    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private void handleClient(Socket clientSocket) throws Exception {
        byte[] buffer = new byte[1024];
        clientSocket.getInputStream().read(buffer);

        var stringInput = new String(buffer);
        if (stringInput.isEmpty()) return;
        if(!stringInput.contains("PING\n")) return;

        var response = this.commandPing ("");
        clientSocket.getOutputStream().write(response.getBytes());;


    }

    private String commandPing(String argument){
        if (argument.isEmpty()) return "+PONG\r\n";
        return argument+"\r\n";
    }



}
