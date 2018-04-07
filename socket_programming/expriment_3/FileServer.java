package expriment_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// 服务器响应类
class ServerFileHandler implements Runnable {
    private Socket socket;
    public ServerFileHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            int offset = 0;
            String filename ;
            // 1、接收文件名
            while( true ) {
                byte [] bytes = new byte[1024];
                offset = in.read( bytes );
                if( offset!=0 || offset!=-1) {
                    filename = new String( bytes ).trim() ;
                    System.out.println(" <<< 接收 文件名 ：" + filename + " 成功");
                    out.write( "succeed".getBytes() );
                    break;
                }
            }

            // 2、接收文件
            byte [] fileBytes = in.readAllBytes();
            System.out.println(">>> 接收到文件为 " + filename) ;
            String newFileName = "FileServer-" + filename ;
            File file = new File( newFileName ) ;
            FileOutputStream fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( fileBytes );
            System.out.println("<<< 保存的文件为 " + newFileName );
            System.out.println("<<< 连接关闭") ;
            socket.close();
        } catch (IOException error) {
            System.out.println("XXX 关闭出错") ;
        }
    }
}

public class FileServer {
    private final static int port = 8000 ;
    private ServerSocket serverSocket ;
    public FileServer() {
        try{
            serverSocket = new ServerSocket( port ) ;
            System.out.println( "<<< 服务端启动完毕");
        } catch (IOException error) {
            System.out.println("XXX 服务端启动失败 ");
        }
    }
    public void service(){
        Socket client = null;
        int threadNum = 1;
        while (true) {
            try {
                client = serverSocket.accept();
                System.out.println( "<<< 服务器线程 " + threadNum + " 启动" );
                new Thread( new ServerFileHandler( client ) ).start();
            } catch (Exception e ) {
                System.out.println( "XXX 服务器线程启动失败" ) ;
            }
        }
    }

    public static void main(String []args) throws IOException {
        new FileServer().service();
    }
}
