import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class WordEater
{
    // Because for "\W" cyrillic words not a words
    private final static String REGEXP_LATIN_AND_CYRILLIC_NONWORDS = "[^aA-zZаА-яЯёЁ0-9]";

    private final String text;
    private String delimiter;

    WordEater( String text, String resultDelimiter )
    {
        this.text = text;
        delimiter = resultDelimiter;
    }

    String countWordsAndGetOrderedDescending()
    {
        StringBuilder resultString = new StringBuilder();

        HashMap<String, Integer> wordAndCountMap = getWordAndCountMap();

        wordAndCountMap.entrySet()
                       .stream()
                       .sorted( Map.Entry.comparingByValue( Comparator.reverseOrder() ) )
                       .map( this::getValueKeyStringFromEntry )
                       .forEach( resultString::append );

        return resultString.toString()
                           .trim();
    }

    private HashMap<String, Integer> getWordAndCountMap()
    {
        HashMap<String, Integer> result = new HashMap<>();

        String textWithoutLineSeparators = getTextWithoutLineSeparators();

        Arrays.stream( textWithoutLineSeparators
                           .split( " " ) )
              .map( this::getWordInLowerCase )
              .forEach( s -> {
                  if( result.containsKey( s ) )
                  {
                      result.put( s, result.get( s ) + 1 );
                  }
                  else
                  {
                      result.put( s, 1 );
                  }
              } );

        return result;
    }

    private String getTextWithoutLineSeparators()
    {
        return text.replaceAll( System.lineSeparator(), " " )
                   .trim();
    }

    private String getWordInLowerCase( String s )
    {
        return s.toLowerCase()
                .replaceAll( REGEXP_LATIN_AND_CYRILLIC_NONWORDS, "" );
    }

    private String getValueKeyStringFromEntry( Map.Entry<String, Integer> entry )
    {
        return entry.getValue()
               + delimiter
               + entry.getKey()
               + System.lineSeparator();
    }
}
