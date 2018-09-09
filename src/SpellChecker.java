import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * A SpellChecker uses a Dictionary, a Corrector, and I/O to interactively
 * spell check an input stream.
 * It writes the corrected output to the specified output stream.
 * <p>
 * Note:
 * <ul>
 * <li> The provided partial implementation includes some I/O methods useful
 * for getting user input from a Scanner.
 * <li> All user prompts and messages should be output on System.out
 * </ul>
 * <p>
 * The SpellChecker object is used by SpellCheckerRunner; see the provided
 * code there.
 * @see SpellCheckerRunner
 */
public class SpellChecker {
  private Corrector corr;
  private Dictionary dict;
  
  /**
   * Constructs a SpellChecker
   * 
   * @param c a Corrector
   * @param d a Dictionary
   */
  public SpellChecker(Corrector c, Dictionary d) {
    corr = c;
    dict = d;
  }

  /**
   * Returns the next integer from the given scanner in the range [min, max].
   * Will re-prompt the user until a valid integer is provided.
   *
   * @param min
   * @param max
   * @param sc
   */
  private int getNextInt(int min, int max, Scanner sc) {
    while (true) {
      try {
        int choice = Integer.parseInt(sc.next());
        if (choice >= min && choice <= max) {
          return choice;
        }
      } catch (NumberFormatException ex) {
        // Was not a number. Ignore and prompt again.
      }
      System.out.println("Invalid input. Please try again!");
    }
  }

  /**
   * Returns the next string input from the Scanner.
   *
   * @param sc
   */
  private String getNextString(Scanner sc) {
    return sc.next();
  }

  

  /**
   * checkDocument interactively spell checks a given document.  
   * Internally, it should use a TokenScanner to parse the document.  
   * Word tokens that are not in the dictionary should be
   * corrected; non-word tokens and words that are in the dictionary should 
   * be output verbatim.
   * <p>
   * You may assume all of the inputs to this method are non-null.
   *
   * @param in the source document to spell check
   * @param input an InputStream from which user input is obtained
   * @param out the target document on which the corrected output is written
   * @throws IOException if error while reading
   */
  public void checkDocument(Reader in, InputStream input, Writer out) throws IOException {
    Scanner sc = new Scanner(input);

    TokenScanner word = new TokenScanner(in);
    //SwapCorrector swap = new SwapCorrector(dict);
    
    while(word.hasNext()){
        String token =           word.next();
        if(!TokenScanner.isWord(token))
        out.write(token);
        else{
            if(!dict.isWord(token)){
                Set<String> corrections = corr.getCorrections(token);
                String[] options = corrections.toArray(new String[0]);
                System.out.print("The word: \"" + token + "\" is not in the dictionary. "
                        + "Please enter the number corresponding with the appropriate action: \n"
                        + "0: Ignore and continue \n"
                        + "1: Replace with another word \n");
                for(int i = 2; i < (corrections.size()+2); i++)
                    System.out.println(i + ": Replace with \"" + options[i-2] + "\"");
                int number = getNextInt(0, corrections.size()+1, sc);
                System.out.println("%%" + number);
                if(number == 0){
                    out.write(token);
                    System.out.println("&&" + token);
                }
                else if(number == 1){
                    System.out.print("Enter word to replace with: ");
                    String correct =getNextString(sc);
                    out.write(correct);
                    System.out.println("&&" + correct);
                }
                else if(number > 1){
                    System.out.println("&&" + options[(number-2)]);
                    out.write(options[(number-2)]);}
                else
                    System.out.println("*"+number);
            }
            else
                out.write(token);
        }
            
    }
    // STUB
  }
}
