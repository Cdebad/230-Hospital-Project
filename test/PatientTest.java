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
        assertTrue(out.contains("2001"));
        assertTrue(out.contains("identity:"));
    }

    @Test
    void toCSVTest(){
        PatientIdentity ID = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(8,11,2001)
        );
        Patient pat = new Patient(ID);

        // make sure it is in the correct format
        assertEquals("Bob,Jimmy,2001-08-11",pat.toCSV());
    }

    @Test
    void makePatientTest(){
        // test for normal line
        Patient realPat = Patient.makePatient("Bob,Jimmy,2001-08-11");
        assertNotNull(realPat);
        assertEquals("Jimmy", realPat.getPatientID().getName().getFirst());
        assertEquals("Bob", realPat.getPatientID().getName().getLast());
        assertEquals(makeDate(8,11,2001),realPat.getPatientID().getDob());

        // make sure incorrect/invalid data wont create a patient
        assertNull(Patient.makePatient("Bob,Jimmy"));
        assertNull(Patient.makePatient("Bob,Jimmy,8"));
        assertNull(Patient.makePatient("Bob,Jimmy,fake-da-te"));

        // patient to csv line to patient
        PatientIdentity ID = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(8,11,2001)
        );
        Patient pat = new Patient(ID);
        Patient pat2 = Patient.makePatient(pat.toCSV());
        assertNotNull(pat2);
        assertTrue(pat.getPatientID().match(pat2.getPatientID()));
    }
}