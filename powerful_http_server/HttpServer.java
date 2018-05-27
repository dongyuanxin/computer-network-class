package http_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
用于记录浏览器信息并且写入指定文件
 */
class Logger {
    public static final String logFile = "http-server.log";

    public Logger() {
    }

    public static void log(String info) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(logFile), true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            String now = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()); // 记录当前时间
            writer.write("/*****  " + now + "  *****/\n");
            writer.write(info + "\n");
            writer.close();
        } catch (Exception error) {
            System.out.println("Log fail");
        }
    }
}

/*
向浏览器返回信息
 */
class Response implements Runnable {
    private Socket socket;
    private byte[] bytes ;
    private String bytesStr ;
    private String [] bytesStrArr;
    private IndexHtml indexHtml;
    public Response(Socket socket , IndexHtml indexHtml) {
        this.socket = socket;
        this.indexHtml = indexHtml;
    }

    protected void input( InputStream in ) throws IOException{
        this.bytes = new byte[1024];
        in.read(this.bytes);
        this.bytesStr = new String(this.bytes);
        Logger.log(this.bytesStr.trim());
    }
    /*
    处理GET请求
     */
    protected void getHandle( OutputStream out ) throws IOException{
        Pattern pattern = Pattern.compile(" (/.*?) ");
        Matcher matcher = pattern.matcher(this.bytesStrArr[0]);
        if( matcher.find() == false ) {
            return ;
        }
        String path = matcher.group(1) ;
        if( path.equals("/test.png") || path.equals("/favicon.ico")) {
            out.write(this.indexHtml.srcBytes);
        } else {
            out.write(this.indexHtml.htmlBytes);
        }
    }
    /*
    处理POST请求
    目前仅支持post的默认application/x-www-form-urlencoded格式
    账户密码验证
     */
    protected void postHandle(OutputStream out) throws IOException{
        String[] dict = this.bytesStrArr[ this.bytesStrArr.length -1 ].trim().split("&") ;

        if( dict[0].split("=")[1].equals("dongyuanxin") && dict[1].split("=")[1].equals("123456") ) {
            out.write("HTTP/1.0 200 OK\r\n\r\n".getBytes());
        } else {
            out.write("HTTP/1.0 401 Unauthorized\r\n\r\n".getBytes());
        }
    }
    /*
    处理PUT请求
     */
    protected void putHandle(OutputStream out) throws IOException{
        out.write("Support PUT Request".getBytes());
    }
    /*
    处理HEAD请求
     */
    protected void headHandle(OutputStream out) throws IOException{
        out.write("This is a simple server.\nAnd it run at JVM.".getBytes());
    }

    /*
    处理不支持的请求协议
     */
    protected void unsupportHandle( OutputStream out ) throws IOException{
        // 对于不支持的method，在header中返回501状态码
        out.write("HTTP/1.0 501 Not Implemented\r\n\r\n".getBytes());
    }

    @Override
    public void run() {
        try {
            InputStream in = this.socket.getInputStream();
            this.input( in ); // 处理web的input-steam
            OutputStream out = this.socket.getOutputStream();
            this.bytesStrArr = this.bytesStr.split("\n"); // 识别http请求类型
            if (bytesStrArr[0].indexOf("GET") != -1) {
                this.getHandle( out );
            } else if ( bytesStrArr[0].indexOf("POST") != -1 ) {
                this.postHandle(out);
            } else if ( bytesStrArr[0].indexOf("HEAD") != -1 ) {
                this.headHandle(out);
            } else if ( bytesStrArr[0].indexOf("PUT") != -1 ) {
                this.putHandle(out);
            } else {
                // 其他协议
                this.unsupportHandle( out );
            }
            in.close();
            out.close();
        } catch (IOException error) {
        }
    }
}


public class HttpServer {
    private final static int port = 80;
    private ServerSocket serverSocket;
    private IndexHtml indexHtml ;

    public HttpServer() throws IOException {
        this.indexHtml = new IndexHtml("test.png");
        serverSocket = new ServerSocket(port);
        System.out.println("HttpServer listen at port : " + port);
    }

    // 利用多线程 + 回调支持多客户监听
    public void listen() {
        try {
            Socket client = serverSocket.accept();
            new Thread(new Response(client , this.indexHtml)).start();
        } catch (Exception e) {
            System.out.println("/***** 客户端连接失败 *****/");
        }
        this.listen();
    }

    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer();
            server.listen();
        } catch (IOException error) {
            System.out.println(error);
            System.out.println("服务器创建失败");
        } catch (Exception error) {
            System.out.println("未知错误：" + error);
        }
    }
}