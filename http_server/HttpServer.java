package http_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
用于记录浏览器信息并且写入指定文件
 */
class Logger {
    public static final String logFile = "http-server.log" ;
    public Logger(){}
    public static void log( String info ){
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    new File( logFile ) ,
                                    true // 追加模式
                            ),
                            "UTF-8" // 兼容中文编码
                    )
            );
            String now = ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ).format( new Date() ) ; // 记录当前时间
            writer.write("/*****  " + now  + "  *****/\n");
            writer.write(info + "\n");
            writer.close();
        } catch (Exception error ) {
            System.out.println("Log fail");
        }
    }
}

/*
向浏览器返回信息
 */
class Response implements Runnable {
    private Socket socket;
    public Response ( Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            byte[] bytes = new byte[ 1024 ];
            in.read( bytes );
            Logger.log( new String( bytes ).trim());
            OutputStream out = socket.getOutputStream();
            out.write( IndexHtml.html.getBytes() );
            in.close();
            out.close();
        } catch (IOException error) {}
    }
}



public class HttpServer {
    private final static int port = 80 ;
    private ServerSocket serverSocket ;
    public HttpServer() throws IOException{
        serverSocket = new ServerSocket( port ) ;
        System.out.println("HttpServer listen at port : " + port );
    }
    // 利用回调支持多客户监听，并且节省服务器资源
    public void listen(){
        try {
            Socket client = serverSocket.accept();
            System.out.println( "/***** Comes from : " + client.getInetAddress() + " *****/");
            new Thread( new Response( client ) ).start();
        } catch (Exception e ) {
            System.out.println( "/***** 客户端连接失败 *****/" ) ;
        }
        this.listen();
    }

    public static void main(String []args) {
        try {
            HttpServer server = new HttpServer();
            server.listen();
        } catch ( IOException error ) {
            System.out.println("服务器创建失败");
        } catch ( Exception error ) {
            System.out.println("未知错误：" + error );
        }
    }
}