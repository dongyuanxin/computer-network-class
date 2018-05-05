import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* https://segmentfault.com/q/1010000008812322?_ea=1752917 */
public class BigFileReader {
    public static void main( String []args ) throws IOException{
        // 内存映射
        String file = "F:\\logs\\debug.log";
        long start = System.currentTimeMillis();
        RandomAccessFile randomAccessFile = new RandomAccessFile( file , "r") ;
        long length = randomAccessFile.length();
        System.out.println(length);
        MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(
                FileChannel.MapMode.READ_ONLY , 0 , length
        );
        for(long i = 0 ;i < length; ++i) {
            mappedByteBuffer.get();
        }
        System.out.println( "Use " + ( System.currentTimeMillis() - start) + " milliseconds");
        randomAccessFile.close();
    }
}
