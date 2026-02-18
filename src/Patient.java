public class Patient {
    public Patient(PatientIdentity ID){
        patientID = ID;
    }

    private final PatientIdentity patientID; //"should NOT allow the PatientIdentity to be ‘set’ or modified"

    public PatientIdentity getPatientID() {
        return patientID;
    }

    public String toString(){
        return "identity: " + patientID.toString();
    }
}