package http_server;
/*
简单的首页样式
为了方便测试和展示，没有单独写html文件
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class IndexHtml {
    private String html = "\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset=\"utf-8\" />\n" +
            "<title>首页</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<center><h1 style=\"color:red;\">Hello,My name is 董沅鑫.</h1>" +
            "<h2 style=\"color:blue;\">Post、Head和Put请求在Fiddler中进行了测试，实验报告中有截图。这里就不再写前端代码了</h2>" +
            "<h2>我的个人网站是：<a href=\"http://geyouneihan.com\" target=\"_blank\">geyouneihan.com</a>（自己写的博客系统、听歌平台、用户系统还有一些附加设计，欢迎来踩）</h2>" +
            "</center>\n" +
            "<center>\n" +
            "<p>下面这张图片是这个网站的logo。专门处理下 '/favicon.ico' 请求</p>" +
            "<img src=\"/test.png\"  alt=\"哈哈哈\" />\n" +
            "</center>\n" +
            "</body>\n" +
            "</html>";
    public byte[] srcBytes;
    public byte[] htmlBytes;
    public IndexHtml( String path ) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(path);
        this.srcBytes = fileInputStream.readAllBytes();
        this.htmlBytes = this.html.getBytes();
    }
}

