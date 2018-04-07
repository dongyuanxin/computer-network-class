package expriment_2_problem_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

// 客户端发送消息的类
class ClientSender implements Runnable{
    private Socket socket;
    public ClientSender( Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run(){
        String info ;
        Scanner scanner = new Scanner( System.in ) ;
        while ( socket.isClosed() == false ) {
            info = scanner.nextLine() ;
            try {
                OutputStream out = socket.getOutputStream();
                out.write( info.getBytes() );
                System.out.println( ">>> 向服务器发送命令 : " + info);
            } catch (IOException error) {
                System.out.println( "XXX 向服务器发送命令失败" );
            }
        }

    }
}

// 客户端从服务器接受消息类
class ClientReceiver implements Runnable {
    private Socket socket;
    public ClientReceiver ( Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            byte[] bytes ;
            int offset ;
            String answer ;
            while( true ) {
                bytes = new byte[ 1024 ];
                offset = in.read( bytes );
                if( offset!=0 && offset != -1 ) {
                    answer = new String( bytes ).trim();
                    if( answer.equals("Bye") ) {
                        socket.close();
                        break;
                    }
                    System.out.println("<<< 服务器应答：" + answer );

                }
            }
        } catch (IOException error) {
            System.out.println("XXX 获取服务器命令失败") ;
        }
        System.out.println("<<< 关闭连接");
    }
}

public class Client {
    private final static String host = "localhost" ;
    private final static int port = 8000 ;
    private Socket socket;
    public Client(){
        try{
            this.socket = new Socket( host , port );
            System.out.println("<<< 连接服务器成功");
        } catch ( IOException error) {
            System.out.println( "Can't connect server ");
        }
    }
    public void start(){
        new Thread( new ClientSender( this.socket ) ).start();
        new Thread( new ClientReceiver( this.socket ) ).start();
    }
    public static void main(String []args) {
        new Client().start();
    }
}
