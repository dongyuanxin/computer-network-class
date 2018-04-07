package expriment_3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// 发送文件客户端
class ClientFileHandler implements Runnable{
    private Socket socket;
    public ClientFileHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            // 1、传输文件名
            System.out.println(">>> 请输入您要传输文件的文件名: ");
            String info ;
            Scanner scanner = new Scanner( System.in ) ;
            info = scanner.nextLine().trim() ;
            out.write( info.getBytes() );
            int offset = socket.getInputStream().read();
            if( offset == 0 || offset == -1 ) {
                System.out.println("XXX 文件名传输失败");
                socket.close();
            }
            System.out.println("<<< 文件名传输成功");

            // 2、传输文件
            System.out.println(">>> 开始传输文件 " + info);
            FileInputStream fileInputStream = new FileInputStream( new File( info )) ;
            out.write( fileInputStream.readAllBytes() ); // 读取文件二进制
            System.out.println( "<<< 文件传输完毕" + info);
            socket.close();
        } catch (IOException error) {
            System.out.println( "XXX 文件传输失败" );
        }
        System.out.println("<<< 关闭连接");
    }
}


public class FileClient {
    private final static String host = "localhost" ;
    private final static int port = 8000 ;
    private Socket socket;
    public FileClient(){
        try{
            this.socket = new Socket( host , port );
            System.out.println("<<< 连接服务器成功");
        } catch ( IOException error) {
            System.out.println( "Can't connect server ");
        }
    }
    public void start(){
        new Thread( new ClientFileHandler( this.socket ) ).start();
    }
    public static void main(String []args) {
        new FileClient().start();
    }
}
