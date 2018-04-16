package breakpoint_download;
/*
断点续传
董沅鑫
2016150127
https://blog.csdn.net/fightplane/article/details/1890845
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class BreakpointDownload {
    public String url ;
    public String path ;
    private int timeout = 5000 ;
    public BreakpointDownload(String url , String path ) throws IOException{
        this.url = url;
        this.path = path ;
    }
    private long readProcess() throws IOException{
        File file = new File( this.path );
        // 没有下载
        if(file.exists() == false) {
            return 0;
        }
        return file.length();
    }

    private HttpURLConnection getHttpConnection(String url , String method , int timeout) {
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL( url )).openConnection() ;
            connection.setRequestMethod(method);
            connection.setConnectTimeout(timeout);
            return connection;
        } catch (IOException error) {
            System.out.println("Fail to open connection with " + url );
        } catch (Exception error) {
            System.out.println("Unknown error when open connection with " + url );
        }
        return null;
    }

    public void start() throws IOException{
        System.out.println(">>> Start read size");
        HttpURLConnection connection = getHttpConnection(this.url , "GET" , this.timeout) ;
        int length = connection.getContentLength();
        System.out.println("<<< Size is " + length + " bytes. ");
        System.out.println(">>> Start download");
        long startTime = System.currentTimeMillis(); // 开始时间
        long lastProcess = this.readProcess(); // 结束的地方
        if( lastProcess == length ) {
            System.out.println("<<< You have finish downloading it");
        } else {
            System.out.println(">>> You have download " + lastProcess + " bytes.");
        }
        connection = getHttpConnection(this.url , "GET" , this.timeout) ;
        connection.setRequestProperty("Range" , "bytes=" + lastProcess + "-"  ); // 部分获取

        if( connection.getResponseCode() == 206 ){
            FileOutputStream fout = new FileOutputStream( this.path , true );
            InputStream in = connection.getInputStream();
            int b =  0;
            while ( ( b = in.read() ) > -1 ) {
                fout.write( b );
            }
            fout.close();
            in.close();
            System.out.println("<<< Finish download. Using " + (System.currentTimeMillis() - startTime) / 1000 + " seconds.");
        } else {
            System.out.println("Server can't stand 206 status");
        }

    }
    public static void main(String []args) throws IOException{
        BreakpointDownload breakpointDownload = new BreakpointDownload("https://dldir1.qq.com/music/clntupate/QQMusicSetup.exe" , "QQMusicSetup.exe") ;
        breakpointDownload.start();
    }
}


