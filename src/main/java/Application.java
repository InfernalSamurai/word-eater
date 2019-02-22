import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Application
{
    public static void main( String[] args )
    {
        String text;

        List<String> argumentList = Arrays.asList( args );

        System.out.println( "You can give path to file as an argument for application." );

        if( argumentList.isEmpty() )
        {
            System.out.println( "Input you text here. "
                                + "When application sees empty line "
                                + "it will start processing text" );

            text = getTextFromConsole();
        }
        else
        {
            String fileName = argumentList.stream()
                                          .findAny()
                                          .orElse( "" );

            text = getTextFromFile( fileName );
        }

        String result = new WordEater( text, " : " ).countWordsAndGetOrderedDescending();

        System.out.println( result );
    }

    private static String getTextFromConsole()
    {
        StringBuilder result = new StringBuilder();
        try( BufferedReader reader = new BufferedReader( new InputStreamReader( System.in, StandardCharsets.UTF_8 ) ) )
        {
            String line;
            do
            {
                line = Optional.ofNullable( reader.readLine() )
                               .orElse( "" );
                result.append( line )
                      .append( System.lineSeparator() );
            }
            while( !line.isEmpty() );
        }
        catch( IOException e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }

        return result.toString();
    }

    private static String getTextFromFile( String fileName )
    {
        StringBuilder result = new StringBuilder();

        Path absolutePath = Paths.get( fileName )
                                 .toAbsolutePath();

        if( !new File( absolutePath.toString() ).canRead() )
        {
            System.err.println( "Can't read given file: " + absolutePath );
            System.exit( 1 );
        }

        try
        {
            Files.readAllLines( absolutePath, StandardCharsets.UTF_8 )
                 .forEach( s -> result.append( s )
                                      .append( System.lineSeparator() ) );
        }
        catch( IOException e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }

        return result.toString();
    }
}
