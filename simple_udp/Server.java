import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server implements Runnable{
    private DatagramPacket dp;
    private DatagramSocket ds;
    private int port;

    public DatagramPacket getDp() {
        return dp;
    }

    public void setDp(DatagramPacket dp) {
        this.dp = dp;
    }

    public Server() {
        this.port = 5000;
    }
    public Server(int port) {
        this.port = port;
    }
    public void listen() {
        try {
            ds = new DatagramSocket(port);
            int len;
            while (true) {
                byte[] bytes = new byte[1024];
                dp = new DatagramPacket(bytes, bytes.length);
                ds.receive(dp);
                String clientLoc = dp.getAddress().getHostAddress()+":"+dp.getPort();
                String info = new String(dp.getData(),0,dp.getLength());
                len = info.length();
                if (len == 0) {
                    continue;
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String now = df.format(new Date());
                clientLoc = "At: " + now + "\nFrom: " + clientLoc;
                System.out.println(clientLoc);
            }
        } catch (Exception e) {
            System.out.println("Can't receive info from client");
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        this.listen();
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.listen();
    }
}
