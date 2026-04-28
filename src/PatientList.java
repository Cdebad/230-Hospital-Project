import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class PatientList {
    private BinarySearchTree patientTree;
    public PatientList() { patientTree = new BinarySearchTree(); }

    // Adds a patient to the database. Returns true if this succeeds, false otherwise.
    public boolean add( Patient pat){
        return patientTree.add(pat);
    }

    // Returns Patient matching the given identity, null if not found.
    public Patient find( PatientIdentity id ) {
        return (Patient) patientTree.find(id);
    }

    public void initIteration(){
        patientTree.init();
    }

    public Patient next(){
        return (Patient) patientTree.next();
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
                    if (!add(pat)){
                        scanner.close();
                        return false;
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