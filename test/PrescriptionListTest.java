import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionListTest {

    private Date makeDate(String dateStr){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }
    }

    Prescription p0 = new Prescription("p0name",makeDate("1999-10-25"),50,"prescribername");
    Prescription p1 = new Prescription("p1name",makeDate("2000-10-25"),50,"prescribername");
    Prescription p2 = new Prescription("p2name",makeDate("2001-10-25"),50,"prescribername");


    @Test
    void add() {
        //testing that ordered add is correct
        PrescriptionList list = new PrescriptionList();
        list.add(p2);
        list.add(p0);
        list.add(p1);
        list.init();
        assertEquals("p2name",list.next().getName());
        assertEquals("p1name",list.next().getName());
        assertEquals("p0name",list.next().getName());
    }

    @Test
    void init() {
        //empty list init + next correctly
        PrescriptionList emptylist = new PrescriptionList();
        emptylist.init();
        assertNull(emptylist.next());
    }

    @Test
    void next() {
        //test if iteration works as expected (null at end, init re-inits)
        PrescriptionList list = new PrescriptionList();
        list.add(p1);
        list.init();
        assertEquals("p1name",list.next().getName());
        assertNull(list.next());
        assertNull(list.next());
        list.init();
        assertEquals("p1name",list.next().getName());
        assertNull(list.next());
    }
}