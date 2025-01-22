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
    }

    @Test
    public void testisPalindrome() {
//        assertTrue(palindrome.isPalindrome("a"));
//        assertTrue(palindrome.isPalindrome(""));
//        assertTrue(palindrome.isPalindrome("asssa"));
//        assertTrue(palindrome.isPalindrome("bijib"));
//        assertFalse(palindrome.isPalindrome("Amy"));
//        assertFalse(palindrome.isPalindrome("cat"));
        CharacterComparator obo = new OffByOne();
        assertFalse(palindrome.isPalindrome("a", obo));
        assertFalse(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("adscb", obo));
        assertTrue(palindrome.isPalindrome("bijkjc", obo));
        assertFalse(palindrome.isPalindrome("Amy", obo));
        assertFalse(palindrome.isPalindrome("cat", obo));

    }


}
