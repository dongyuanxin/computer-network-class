package expriment_1;

//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author : 董沅鑫
 * id : 2016150127
 * Duty: 下载http://www.szu.edu.cn，并统计大小
 */

public class UseUrlDownload {
    public static void main(String []main) throws Exception{
        String szu = "http://www.szu.edu.cn" ;
        URL url = new URL( szu );

        System.out.println(">>> Open download stream ");

        InputStream in = url.openStream();
        File file = new File( "szu.html") ;
        FileOutputStream fout = new FileOutputStream( file );
        int a = 0;
        while( ( a = in.read()) > -1) {
            fout.write( a );
        }
        fout.close();
        in.close();

        System.out.println("<<< Download finish ");
        System.out.println( "File size is : " + file.length()/1024.0 + "  KB" );
    }
}
