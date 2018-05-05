
import org.apache.log4j.Logger;

public class Log4jTest {
    private static final Logger LOGGER = Logger.getLogger( Log4jTest.class );
    public static void main(String[]args) {
        long start = System.currentTimeMillis();
        for( int i = 0; i<10000000; i++ ) {
            LOGGER.debug("This is a debug message") ;
        }
        System.out.println( "Use " + ( System.currentTimeMillis() - start)/1000 + " seconds");
    }
}
