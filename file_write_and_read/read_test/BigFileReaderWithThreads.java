import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

class ReaderThread implements Runnable{
    public String file ;
    public int start ; // 开始读取的位置
    public int chunk; // 读取的数据块大小
    public ReaderThread( String file , int start , int chunk ){
        this.file = file;
        this.start = start;
        this.chunk = chunk;
    }
    @Override
    public void run() {
        long start = System.currentTimeMillis(); // 目前系统时间
        try {
            RandomAccessFile reader = new RandomAccessFile( file , "r");
            byte [] bytes = new byte[ this.chunk ];
            reader.seek( this.start ); // 从start位置开始读取
            int offset = reader.read( bytes );
            System.out.println( "Use " + ( System.currentTimeMillis() - start) + " millisecond");
            reader.close();
        } catch (FileNotFoundException error ){
            System.out.println("File unexist");
        }catch (IOException error ){
            System.out.println("Read fail");
        }
    }
}

public class BigFileReaderWithThreads {
    public static void main(String []args) {
        int threadNum = 3 ;// 测试3个线程
        String loc = "F:\\logs\\debug.log";
        File file = new File( loc );
        int length = (int)file.length(); // 文件长度
        int chunk = (int)(length/threadNum) ; // 每个线程读取的数据块大小
        for( int i = 0 ; i < threadNum ; ++i ) {
            new Thread( new ReaderThread( loc , i * chunk , chunk)).start();
        }
        return;
    }
}
