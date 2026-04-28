import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void pop() {
        //null returned on pop empty
        Stack<Integer> stack = new Stack<Integer>(10);
        assertNull(stack.pop());
    }

    @Test
    void push() {
        //push and then pop is in correct order
        Stack<Integer> stack = new Stack<Integer>(10);
        stack.push(10);
        stack.push(12);
        assertEquals(12,stack.pop());
        assertEquals(10,stack.pop());
        assertNull(stack.pop());
    }

    @Test
    void empty() {
        //empty clears the stack
        Stack<Integer> stack = new Stack<Integer>(10);
        stack.push(10);
        stack.push(12);
        stack.empty();
        assertNull(stack.pop());
    }
}