import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Date makeDate(int month, int day, int year){
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
    void getPatientID(){
        PatientIdentity ID = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );

        Patient pat1 = new Patient(ID);

        assertTrue(ID.match(pat1.getPatientID())); // test that it returns correct ID
    }

    @Test
    void toStringTest(){
        // same idea as dob tostring, only looking for specific keys instead of matching entire string
        PatientIdentity ID = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(8,11,2001)
        );
        Patient pat1 = new Patient(ID);
        String out = pat1.toString();
        assertTrue(out.contains("Bob, Jimmy"));
        assertTrue(out.contains("name:"));
        assertTrue(out.contains("dob:"));
        assertTrue(out.contains("2000"));
        assertTrue(out.contains("identity:"));
    }
}