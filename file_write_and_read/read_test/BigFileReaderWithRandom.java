import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BigFileReaderWithRandom {
    public static void main(String[]args) throws IOException {
        String file = "F:\\logs\\debug.log";
        long start = System.currentTimeMillis();
        RandomAccessFile reader = new RandomAccessFile( file , "r");
        long length = reader.length();
        System.out.println("文件大小 : " + length );
        byte [] bytes = new byte[ (int)length ];
        int offset = 0;
        while( ( offset = reader.read( bytes) )!= -1 ) ;
        System.out.println( "Use " + ( System.currentTimeMillis() - start) + " millisecond");
        reader.close();
    }
}
