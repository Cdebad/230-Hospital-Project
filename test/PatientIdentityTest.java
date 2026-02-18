import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientIdentityTest {

    public Date makeDate(int month, int day, int year){
        // the tutorial link in the notes wasnt working, but the description sounded like SimpleDateFormat since we are using java.util.Date - please let me know if I should use something else
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String dateStr = month + "/" + day + "/" + year;
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }

    }

    @Test
    void toStringTest(){
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
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
                makeDate(9,12,2000)
        );

        assertTrue(p1.getName().match(new Name("Jimmy","Bob"))); //test getName
    }

    @Test
    void getDob(){
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );

        assertEquals(p1.getDob(), makeDate(9, 12, 2000)); //test getDob
    }

    @Test
    void match() {
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );
        PatientIdentity p2 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );
        assertTrue(p1.match(p2)); //test identical

        PatientIdentity p3 = new PatientIdentity(
                new Name("Bob", "Neutron"),
                makeDate(9,12,2000)
        );
        assertFalse(p1.match(p3)); //test different name + same dob

        PatientIdentity p4 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(10,11,1999)
        );
        assertFalse(p1.match(p4)); //test same name + different dob

        PatientIdentity p5 = new PatientIdentity(
                new Name("Bob", "Neutron"),
                makeDate(10,11,1999)
        );
        assertFalse(p1.match(p5)); //test different name + different dob

        PatientIdentity p6 = new PatientIdentity(
                new Name("JiMmY", "boB"),
                makeDate(9,12,2000)
        );
        assertTrue(p1.match(p6)); //test case
    }

    @Test
    void isLessThan() { // sorts by dob before name
        PatientIdentity p1 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );
        PatientIdentity p2 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );
        assertFalse(p1.isLessThan(p2)); //test identical

        PatientIdentity p3 = new PatientIdentity(
                new Name("Jimmy", "Cob"),
                makeDate(9,12,2000)
        );
        assertTrue(p1.isLessThan(p3)); //test name sorting

        PatientIdentity p4 = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2001)
        );
        assertTrue(p1.isLessThan(p4)); //test older is less
        assertFalse(p4.isLessThan(p1)); //test younger is not less

        PatientIdentity p5 = new PatientIdentity(
                new Name("Jimmy", "A"),
                makeDate(9,12,2001)
        );
        assertTrue(p1.isLessThan(p5)); //test dob sorted before name
    }
}