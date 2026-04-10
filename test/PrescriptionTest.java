import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionTest {

    private Date makeDate(String dateStr){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }
    }

    Prescription p1 = new Prescription("medname",makeDate("2000-10-25"),50,"prescribername");

    @Test
    void getName() {
        assertEquals("medname", p1.getName());
    }

    @Test
    void getDate_of_issue() {
        assertEquals(makeDate("2000-10-25"),p1.getDate_of_issue());
    }

    @Test
    void getPrescriber() {
        assertEquals("prescribername",p1.getPrescriber());
    }

    @Test
    void comesBefore() {
        Prescription p0 = new Prescription("medname",makeDate("1999-10-25"),50,"prescribername");
        assertTrue(Prescription.comesBefore(p1,p0));
        assertFalse(Prescription.comesBefore(p0,p1));
    }

    @Test
    void makePrescription() {
        //expected good case
        Prescription pe = new Prescription("expected",makeDate("1999-10-25"),50,"prescribername");
        String line = "person,name,1900-10-25,expected,1999-10-25,50,prescribername";
        Prescription made = Prescription.makePrescription(line);
        assertNotNull(made);
        assertEquals(pe.getName(),made.getName());
        assertEquals(pe.getDate_of_issue(),made.getDate_of_issue());
        assertEquals(pe.getPrescriber(),made.getPrescriber());

        //not enough parts
        String badline1 = "bad,1900-10-25,expected,1999-10-25,50,prescribername";
        Prescription made2 = Prescription.makePrescription(badline1);
        assertNull(made2);

        //bad issued date
        String badline2 = "person,name,1900-10-25,expected,1999-1025,50,prescribername";
        Prescription made3 = Prescription.makePrescription(badline2);
        assertNull(made3);

        //bad dosage
        String badline3 = "person,name,1900-10-25,expected,1999-10-25,dosage,prescribername";
        Prescription made4 = Prescription.makePrescription(badline3);
        assertNull(made4);
    }
}