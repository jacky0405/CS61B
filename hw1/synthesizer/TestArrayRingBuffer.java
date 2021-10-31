package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer<Integer>(10);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        assertEquals(3,arb.dequeue());
        assertEquals(4, arb.peek());
        assertEquals(2, arb.fillCount());
        assertEquals(10, arb.capacity());


    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
