import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void fullName() {
        Name n1 = new Name("Jimmy","Bob");
        assertEquals("Bob, Jimmy", n1.fullName()); // test correct fullName format
    }

    @Test
    void match() {
        Name n1 = new Name("Jimmy","Bob");
        Name n2 = new Name("Jimmy","Bob");
        assertTrue(n1.match(n2)); // test identical names match

        Name n3 = new Name("JIMMy","BoB");
        Name n4 = new Name("JImMy","bOB");
        assertTrue(n3.match(n4)); // test different cases

        Name n5 = new Name("Jimmy","Bobby");
        Name n6 = new Name("Jimmy","Bob");
        assertFalse(n5.match(n6)); //test different last names

        Name n7 = new Name("Jim","Bob");
        Name n8 = new Name("Jimmy","Bob");
        assertFalse(n7.match(n8)); //test different first names
    }

    @Test
    void isLessThan() {
        Name n1 = new Name("Jim","A");
        Name n2 = new Name("Jim","B");
        assertTrue(n1.isLessThan(n2));
        assertFalse(n2.isLessThan(n1)); //test alphabetical ordering

        Name n3 = new Name("Jim","A");
        Name n4 = new Name("Bob","A");
        assertTrue(n4.isLessThan(n3)); //test sorting by first name when last is same

        assertFalse(n1.isLessThan(n1)); //test false when identical names

        Name n5 = new Name("jIM","A");
        Name n6 = new Name("Jim","a");
        assertFalse(n5.isLessThan(n6)); //test different case
        assertFalse(n6.isLessThan(n5)); //both ways to make sure
    }
}