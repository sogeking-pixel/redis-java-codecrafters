public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Logs from your program will appear here!");

    int port = 6379;
    var host = "192.168.1.37";

    ServerTcp server = new ServerTcp();
    server.start();

  }
}
