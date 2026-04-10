import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient {
    public Patient(PatientIdentity ID){
        patientID = ID;
        prescriptions = new PrescriptionList();
    }

    private final PatientIdentity patientID; //"should NOT allow the PatientIdentity to be ‘set’ or modified"
    private final PrescriptionList prescriptions;

    public PatientIdentity getPatientID() {
        return patientID;
    }
    public PrescriptionList getPrescriptionList(){return prescriptions;}

    public String toString(){
        return "identity: " + patientID.toString();
    }

    private String formatDate(Date dob){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(dob);
    }

    public String toCSV(){
        Name name = patientID.getName();
        String first = name.getFirst();
        String last = name.getLast();
        String dob = formatDate(patientID.getDob());
        return last + "," + first + "," + dob; // uuid here when implemented
    }

    private static Date makeDate(String dateStr){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }
    }

    public static Patient makePatient(String line){
        try {
            String[] parts = line.split(",");

            if (parts.length < 3){return null;} // 4 if uuid included

            Date dob = makeDate(parts[2]);
            if (dob == null){ return null;}

            return new Patient(
                    new PatientIdentity(
                            new Name(parts[1],parts[0]),
                            dob
                    )
            );

        } catch (Exception e){
            return null;
        }
    }
}