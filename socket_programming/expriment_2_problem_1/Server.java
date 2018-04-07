package expriment_2_problem_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

// 服务器响应类
class ServerHandler implements Runnable {
    private Socket socket;
    public ServerHandler ( Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            SimpleDateFormat df; // 获得消息的准确时间戳
            String now ;
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] bytes ;
            int offset ;
            String order;
            while( true ) {
                bytes = new byte[ 1024 ];
                offset = in.read( bytes );
                if( offset!=0 && offset != -1 ) {
                    order = new String( bytes ).trim();
                    System.out.println(">>> 客户端传来命令：" + order );
                    if( order.trim().equals( "Time" ) ) { // 传递时间
                        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        now = df.format(new Date());
                        out.write( now.getBytes() );
                    } else if ( order.trim().equals("Exit")) { // 关闭连接
                        out.write( "Bye".getBytes() );
                        socket.close();
                    }
                }
            }
        } catch (IOException error) {
            System.out.println("<<< 连接关闭") ;
        }
    }
}

public class Server {
    private final static int port = 8000 ;
    private ServerSocket serverSocket ;
    public Server() {
        try{
            serverSocket = new ServerSocket( port ) ;
            System.out.println( "<<< 服务端启动完毕");
        } catch (IOException error) {
            System.out.println("XXX 服务端启动失败 ");
        }
    }
    public void service(){
        Socket client = null;
        while (true) {
            try {
                client = serverSocket.accept();
                System.out.println( "<<< 创建客户链接 Comes from :" + client.getInetAddress() );
                new Thread( new ServerHandler( client ) ).start();
            } catch (Exception e ) {
                System.out.println( "XXX 客户链接失败" ) ;
            }
        }
    }

    public static void main(String []args) throws IOException {
        new Server().service();
    }
}
