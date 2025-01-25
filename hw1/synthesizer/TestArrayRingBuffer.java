package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Character> arb = new ArrayRingBuffer<>(10);
//        arb.dequeue();
//        arb.peek();
        String temp = "abcdefghij";
        for (char a : temp.toCharArray()) {
            arb.enqueue(a);
        }

        for (int i = 0; i < 9; i++) {
            arb.dequeue();
        }
        System.out.println(arb.peek());


    }

    @Test
    public void testEnqueueDequeue() {
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(3);
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.enqueue(3);
        assertTrue(buffer.isFull());
        assertEquals(1, (int) buffer.dequeue());
        assertEquals(2, buffer.fillCount);
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(3);
        buffer.enqueue(1);
        buffer.enqueue(2);

        for (Integer i : buffer) {
            System.out.println(i);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
