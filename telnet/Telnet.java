import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

class TelnetReceiver implements Runnable {
    private InputStreamReader reader;
    public TelnetReceiver(InputStreamReader reader) {
        this.reader = reader;
    }
    @Override
    public void run() {
        while (true) {
            try {
                int c = reader.read();
                if(c != 0 ) {
                    System.out.print((char)c);
                } else {
                    System.out.print("empty");
                }
            } catch (IOException e) {
                System.out.println("Receive error");
            }
        }
    }
}

class TelnetSender implements Runnable {
    private OutputStreamWriter writer;
    public TelnetSender(OutputStreamWriter writer) {
        this.writer = writer;
    }
    protected void send(String str) {
        try {
            for(int i = 0; i < str.length(); i++) {
                int c = (int) str.charAt(i);
                if (c == '\n') writer.write('\r');
                else if (c == 0) writer.write('\n');
                else writer.write(c);
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Send error");
        }
    }
    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("连接中, 请稍等...");
            String info = scanner.next() + "\n";
            System.out.println(info.indexOf('\n'));
            send(info);
        }
    }
}

public class Telnet {
    public void Telnet() { }
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("bbs.pku.edu.cn", 23);
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(),"GBK");
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(),"GBK");
            new Thread(new TelnetSender(writer)).start();
            new Thread(new TelnetReceiver(reader)).start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
