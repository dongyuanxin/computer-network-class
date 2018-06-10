import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    private DatagramPacket dp;
    private DatagramSocket ds;
    private int port;
    private String hostName;
    public Client() {
        this.hostName = "localhost";
        this.port = 5000;
    }
    public Client(String hostName, int port) {
        this.port = port;
        this.hostName = hostName;
    }

    public DatagramPacket getDp() {
        return dp;
    }

    public void setDp(DatagramPacket dp) {
        this.dp = dp;
    }

    public void send() {
        try {
            ds = new DatagramSocket();
            System.out.print(">>> ");
            Scanner scanner = new Scanner(System.in);
            String info = scanner.next();
            if (info.length() == 0 || info.equals("\n")) {
               info = "test message";
            }
            byte[] bytes = info.getBytes("UTF-8");
            dp = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(hostName), port);
            ds.send(dp);
            System.out.println("<<< Send info success");

        } catch (Exception e) {
            System.out.println("*** Can't send info to server ***");
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 5000);
        client.send();
    }

}
