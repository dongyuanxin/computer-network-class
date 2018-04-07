package expriment_1;
/**
 * author : 董沅鑫
 * id : 2016150127
 * Duty:
 * 1、本地name和ip
 * 2、远程name和所有ip
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UseInetAddress {
    public static void main(String[] args) throws UnknownHostException{
        // 获得本机ip 和 name
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println( "Local IP is " + inetAddress.getHostAddress() );
        System.out.println( "Local name is " + inetAddress.getHostName() );

        // 获得CSDN的全部ip地址
        String url = "www.csdn.net";
        System.out.println( "\nCSDN's ips and names:" );
        InetAddress[] inetAddresses = InetAddress.getAllByName( url );
        for( InetAddress inet : inetAddresses ) {
            System.out.println( "+  ip is " + inet.getHostAddress() );
            System.out.println( "-  name is " + inet.getHostName() );
        }

        // 获得Baidu的全部ip地址
        url = "www.baidu.com";
        System.out.println( "\nBaidu's ips and names:" );
        inetAddresses = InetAddress.getAllByName( url );
        for( InetAddress inet : inetAddresses ) {
            System.out.println( "+  ip is " + inet.getHostAddress() );
            System.out.println( "-  name is " + inet.getHostName() );
        }
    }
}
