import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome(){
        String a = "cat";
        String b = "aca";
        String c = "a";
        String d = null;
        String e = "";
        assertFalse(palindrome.isPalindrome(a));
        assertTrue(palindrome.isPalindrome(b));
        assertTrue(palindrome.isPalindrome(c));
        assertTrue(palindrome.isPalindrome(d));
        assertTrue(palindrome.isPalindrome(e));
    }

    @Test
    public void testIsPalindrome2(){
        CharacterComparator t = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake",t));
        assertTrue(palindrome.isPalindrome("acdb",t));
        assertFalse(palindrome.isPalindrome("apple",t));
    }
}
