import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientIdentityTest {

    public Date makeDate(int year, int month, int day){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = year + "-" + month + "-" + day;
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){ // dont really need to handle here since is only used for testing and wont be throwing null, and test would fail anyway
            return null;
        }
    }

    @Test
    void toStringTest(){
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );
        String out = p1.toString();
        assertTrue(out.contains("Bob, Jimmy"));
        assertTrue(out.contains("2000"));
        assertTrue(out.contains("name:"));
        assertTrue(out.contains("dob:")); //since the dob toString() is long and contains things like timezone, i just made it check for key elements of the date
    }

    @Test
    void getName(){
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );

        assertTrue(p1.getName().match(new Name("Jimmy","Bob"))); //test getName
    }

    @Test
    void getDob(){
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );

        assertEquals(p1.getDob(), makeDate(2000, 9, 12)); //test getDob
    }

    @Test
    void match() {
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );
        PatientIdentity p2 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );
        assertTrue(p1.match(p2)); //test identical

        PatientIdentity p3 = new PatientIdentity(
                new Name("Bob", "Neutron"),
                makeDate(2000,9,12)
        );
        assertFalse(p1.match(p3)); //test different name + same dob

        PatientIdentity p4 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(1999,10,1)
        );
        assertFalse(p1.match(p4)); //test same name + different dob

        PatientIdentity p5 = new PatientIdentity(
                new Name("Bob", "Neutron"),
                makeDate(1999,10,11)
        );
        assertFalse(p1.match(p5)); //test different name + different dob

        PatientIdentity p6 = new PatientIdentity(
                new Name("JiMmY", "boB"),
                makeDate(2000,9,12)
        );
        assertTrue(p1.match(p6)); //test case
    }

    @Test
    void isLessThan() { // sorts by dob before name
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );
        PatientIdentity p2 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2000,9,12)
        );
        assertFalse(p1.isLessThan(p2)); //test identical

        PatientIdentity p3 = new PatientIdentity(
                new Name("Jimmy", "Cob"),
                makeDate(2000,9,12)
        );
        assertTrue(p1.isLessThan(p3)); //test name sorting

        PatientIdentity p4 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(2001,9,12)
        );
        assertTrue(p1.isLessThan(p4)); //test older is less
        assertFalse(p4.isLessThan(p1)); //test younger is not less

        PatientIdentity p5 = new PatientIdentity(
                new Name("Jimmy", "A"),
                makeDate(2001,9,12)
        );
        assertTrue(p1.isLessThan(p5)); //test dob sorted before name
    }
}