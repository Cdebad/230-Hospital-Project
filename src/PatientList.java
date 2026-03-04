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
}