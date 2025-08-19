import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTcp {
    private InetSocketAddress inetSocketAddress = null;
    private ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(100);;

    public ServerTcp(String host, int port) {
        this.inetSocketAddress = new InetSocketAddress(host, port);
    }
    public ServerTcp(){
        this.inetSocketAddress = new InetSocketAddress(6379);
    }

    public void start() throws Exception {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(this.inetSocketAddress);
        while(true){
            var clientSocket = serverSocket.accept();
            threadPool.submit(new ClienteHandler(clientSocket));
        }

    }

    public void stop() throws Exception {
        serverSocket.close();
    }


}
