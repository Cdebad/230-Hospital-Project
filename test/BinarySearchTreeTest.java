import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    private Date makeDate(int month, int day, int year){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String dateStr = month + "/" + day + "/" + year;
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }

    }

    @Test
    void add() {
        // add patient then find
        BinarySearchTree singleTree = new BinarySearchTree();

        PatientIdentity id = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );

        Patient pat = new Patient(id);
        singleTree.add(pat);

        assertEquals(pat,singleTree.find(id));



        //add a bunch then find
        BinarySearchTree multiTree = new BinarySearchTree();

        PatientIdentity id1 = new PatientIdentity(new Name("Jimmy","Bob"),makeDate(9,12,1980));
        PatientIdentity id2 = new PatientIdentity(new Name("Ralph","Will"),makeDate(8,11,1990));
        PatientIdentity id3 = new PatientIdentity(new Name("Sponge","Bob"),makeDate(7,10,1970));
        PatientIdentity id4 = new PatientIdentity(new Name("John","Gilbert"),makeDate(6,9,2000));
        Patient pat1 = new Patient(id1);
        Patient pat2 = new Patient(id2);
        Patient pat3 = new Patient(id3);
        Patient pat4 = new Patient(id4);

        multiTree.add(pat1);
        multiTree.add(pat2);
        multiTree.add(pat3);
        multiTree.add(pat4);

        assertEquals(pat1,multiTree.find(id1));
        assertEquals(pat2,multiTree.find(id2));
        assertEquals(pat3,multiTree.find(id3));
        assertEquals(pat4,multiTree.find(id4));




        //find fake patient in filled tree (null)
        PatientIdentity fakeID = new PatientIdentity(new Name("John","Wilkins"),makeDate(9,12,2000));
        assertNull(multiTree.find(fakeID));
    }

    @Test
    void find() {
        // find on empty tree (null)
        BinarySearchTree emptyTree = new BinarySearchTree();

        PatientIdentity id = new PatientIdentity(
                new Name("Jimmy", "Bob"),
                makeDate(9,12,2000)
        );

        assertNull(emptyTree.find(id));
    }
}