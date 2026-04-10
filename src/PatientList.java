import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class PatientList {
    private Patient[] patientArray;
    public PatientList(int maxPatients) { patientArray = new Patient[maxPatients]; } // using nextAvailableIndex for iteration so no +1 to length needed
    // ^ made maxPatients an argument so that if it ever needs to be changed, tests that use a full list will function normally (could also add a second constructor without if needed)
    private int nextAvailableIndex = 0;
    private int indexOfIteration = -1;

    // Adds a patient to the database. Returns true if this succeeds, false otherwise.
    public boolean add( Patient pat){
        return addOrdered(pat);
    }

    // Returns Patient matching the given identity, null if not found.
    public Patient find( PatientIdentity id ) {
        return binarySearch(id);
    }

    public void initIteration(){
        if (nextAvailableIndex>0){indexOfIteration = 0;}
    }

    public Patient next(){
        if (indexOfIteration==-1){return null;}
        int i = indexOfIteration++;
        if (indexOfIteration>=nextAvailableIndex){indexOfIteration = -1;} // reset if null/end
        return patientArray[i];
    }



    private boolean addOrdered( Patient pat ) {
        if (nextAvailableIndex>= patientArray.length) {return false;}
        int currentIndex = nextAvailableIndex-1;
        while (currentIndex>=0 && pat.getPatientID().isLessThan(patientArray[currentIndex].getPatientID())){
            patientArray[currentIndex+1] = patientArray[currentIndex--];
        }
        patientArray[currentIndex+1] = pat;
        nextAvailableIndex++;
        return true;
    }

    private Patient binarySearch(PatientIdentity patientIDToFind){
        if (nextAvailableIndex==0){return null;}
        int upper = nextAvailableIndex-1;
        int lower = 0;
        while (upper>=lower){
            int mid = (upper+lower) / 2;
            if (patientArray[mid].getPatientID().match(patientIDToFind)){
                return patientArray[mid];
            }
            if (patientArray[mid].getPatientID().isLessThan(patientIDToFind)){
                lower = mid + 1;
            }
            else{
                upper = mid - 1;
            }
        }
        return null;
    }



    public boolean saveToFile(String filename){
        File file = new File(filename);

        initIteration();
        Patient pat;
        try {
            FileWriter writer = new FileWriter(file);

            while ((pat = next()) != null) {
                writer.write(pat.toCSV() + "\n");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean importFromFile(String filename){
        File file = new File(filename);

        try{
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                Patient pat = Patient.makePatient(scanner.nextLine());

                if (pat != null){
                    if (!addOrdered(pat)){
                        scanner.close();
                        return false; // reached last index, cant import.
                    }
                }
            }
            scanner.close();

            // mergesort would go here (would have to swap addOrdered for addUnOrdered)

            //
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Date makeDate(String dateStr){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }
    }

    private PatientIdentity IDFromLine(String line){
        String[] parts = line.split(",");
        if (parts.length<7){return null;}

        Date dob = makeDate(parts[2]);
        if (dob == null){return null;}

        return new PatientIdentity(new Name(parts[1],parts[0]),dob);
    }

    public boolean importPrescriptionsFromFile(String filename){
        File file = new File(filename);

        try{
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                PatientIdentity id = IDFromLine(line);
                Prescription pr = Prescription.makePrescription(line);

                if (id != null && pr != null){
                    Patient pat = find(id);
                    if (pat!=null){
                        pat.getPrescriptionList().add(pr);
                    }
                }

            }
            scanner.close();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}