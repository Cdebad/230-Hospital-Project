import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientListTest {

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
    void add() { //d
        PatientList pList = new PatientList(10);
        PatientIdentity id = new PatientIdentity(new Name("John","Gilbert"),makeDate(2000,1,1));
        Patient pat = new Patient(id);
        for (int i = 0; i<10; i++){
            assertTrue(pList.add(pat)); // ensure adding works properly up to limit
        }
        assertFalse(pList.add(pat));


        // below tests that addOrdered works
        PatientList oList = new PatientList(10);
        PatientIdentity id1 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2000,1,1));
        Patient pat1 = new Patient(id1);
        PatientIdentity id2 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2001,1,1));
        Patient pat2 = new Patient(id2);
        PatientIdentity id3 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2002,1,1));
        Patient pat3 = new Patient(id3);
        oList.add(pat3); // add in a different order than what they will be stored in
        oList.add(pat1);
        oList.add(pat2);

        //ensure that oList is ordered old->young (pat1,pat2,pat3)
        oList.initIteration();
        assertEquals(pat1,oList.next());
        assertEquals(pat2,oList.next());
        assertEquals(pat3,oList.next());

    }

    @Test
    void find() { //d
        PatientList pList = new PatientList(1000);
        PatientIdentity id = new PatientIdentity(new Name("John","Gilbert"),makeDate(2000,1,1));
        assertNull(pList.find(id)); // ensure null returned when list is empty

        Patient pat = new Patient(id);
        pList.add(pat);
        assertEquals(pat,pList.find(id)); // ensure it finds itself

        PatientIdentity id2 = new PatientIdentity(new Name("Johnny","Gilbert"),makeDate(2000,1,1));
        assertNull(pList.find(id2)); // ensure null returned when list not empty



        // find at edges + middle
        PatientList pList2 = new PatientList(10);
        PatientIdentity i1 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2000,1,1));
        Patient pat1 = new Patient(i1);
        PatientIdentity i2 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2001,1,1));
        Patient pat2 = new Patient(i2);
        PatientIdentity i3 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2002,1,1));
        Patient pat3 = new Patient(i3);
        pList2.add(pat1);
        pList2.add(pat2);
        pList2.add(pat3);

        assertEquals(pat1,pList2.find(i1));
        assertEquals(pat2,pList2.find(i2));
        assertEquals(pat3,pList2.find(i3));

        // same when even count (mostly for middle checks / no inf loop)
        PatientIdentity i4 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2003,1,1));
        Patient pat4 = new Patient(i4);
        pList2.add(pat4);

        assertEquals(pat1,pList2.find(i1));
        assertEquals(pat2,pList2.find(i2));
        assertEquals(pat3,pList2.find(i3));
        assertEquals(pat4,pList2.find(i4));
    }

    @Test
    void initIteration() {
        PatientList empty = new PatientList(1000);
        empty.initIteration();
        assertNull(empty.next()); // make sure it doesnt actually initialize
    }

    @Test
    void next() { //d
        PatientList empty = new PatientList(1000);
        empty.initIteration();
        assertNull(empty.next()); // ensure null after init + empty list

        PatientIdentity id = new PatientIdentity(new Name("John","Gilbert"),makeDate(2000,1,1));
        Patient pat = new Patient(id);
        empty.add(pat);

        empty.initIteration();
        assertEquals(pat,empty.next()); // ensure next success
        assertNull(empty.next()); // ensure null at end of usable range


        PatientList pList = new PatientList(2);
        PatientIdentity id1 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2000,1,1));
        Patient pat1 = new Patient(id1);
        pList.add(pat1);
        PatientIdentity id2 = new PatientIdentity(new Name("John","Gilbert"),makeDate(2001,1,1));
        Patient pat2 = new Patient(id2);
        pList.add(pat2);

        pList.initIteration();
        assertEquals(pat1,pList.next());
        assertEquals(pat2,pList.next()); // ensure next works (x2)
        assertNull(pList.next()); // ensure null at end of full list
        assertNull(pList.next()); // ensure stays null (ensure it doesnt go into an invalid index or anything)

        //test re-init mid loop
        pList.initIteration();
        assertEquals(pat1,pList.next());
        pList.initIteration();
        assertEquals(pat1,pList.next());
    }
}