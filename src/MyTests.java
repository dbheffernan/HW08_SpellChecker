import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.*;

/** Put your OWN test cases in this file, for all classes in the assignment. */
public class MyTests {
    
    @Test public void testEmpty() throws IOException {
        Reader in = new StringReader(""); 
       TokenScanner d = new TokenScanner(in);
       try {
         assertFalse("has next", d.hasNext());
       } finally {
             in.close();
       }
     }
    
    @Test public void testGetNextTokenWord() throws IOException {
        Reader in = new StringReader("Word"); 
       TokenScanner d = new TokenScanner(in);
       try {
         assertTrue("has next", d.hasNext());
         assertEquals("Word", d.next());

       } finally {
             in.close();
       }
     }
    
    @Test public void testGetNextTokenEndsWord() throws IOException {
        Reader in = new StringReader(" !!!! 1 !!Word"); 
       TokenScanner d = new TokenScanner(in);
       try {
         assertTrue("has next", d.hasNext());
         assertEquals(" !!!! 1 !!", d.next());
         assertTrue("has next", d.hasNext());
         assertEquals("Word", d.next());
       } finally {
             in.close();
       }
     }
    
    @Test public void testGetNextTokenEndsNonWord() throws IOException {
        Reader in = new StringReader("Word!"); 
       TokenScanner d = new TokenScanner(in);
       try {
         assertTrue("has next", d.hasNext());
         assertEquals("Word", d.next());
         assertTrue("has next", d.hasNext());
         assertEquals("!", d.next());

       } finally {
             in.close();
       }
     }
    
    @Test(timeout=500) public void testDictionarygetNumWords() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertEquals(32, d.getNumWords());
    }
    
    @Test(timeout=500) public void testDictionaryDoesNotContainEmpty() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertFalse("'(empty)' -> should be false", d.isWord(""));
      }
    
    @Test(timeout=500) public void testDictionaryBlankLineInMiddle() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertFalse("'(new line)' -> should be false", d.isWord("\n"));
      }
    @Test(timeout=500) public void testDictionaryMixedCase() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertTrue("'aPpLe' -> should be true", d.isWord("aPpLe"));
      }
    @Test(timeout=500) public void testDictionaryWhitespaceAroundWord() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertTrue("'  aye   ' -> should be true", d.isWord("aye"));
      }
    @Test(timeout=500) public void testDictionarygetNumWordsWithRepeats() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertEquals(32, d.getNumWords());
    }
}