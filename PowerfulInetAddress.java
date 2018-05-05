import java.net.*;
import java.util.Enumeration;
import java.util.List;
public class PowerfulInetAddress {
    public static void main(String[] args) throws Exception {
        // 返回本机的所有网络接口
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();

        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement(); // 得到其中一个网络接口
            Enumeration<InetAddress> addresses = nif.getInetAddresses(); // 获取与该网络接口绑定的IP地址
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr instanceof Inet4Address ) {
                    System.out.println("网卡接口名称：" + nif.getName());
                    System.out.println("网卡接口地址：" + addr.getHostAddress());
                    System.out.println();
                }
            }
        }
    }
}
