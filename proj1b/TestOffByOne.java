import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('0', 'A'));
        assertFalse(offByOne.equalChars('A', 'A'));
        assertTrue(offByOne.equalChars('&', '%'));

    }


    // Your tests go here.

}
