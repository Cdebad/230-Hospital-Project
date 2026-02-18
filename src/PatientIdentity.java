import java.util.Date;

public class PatientIdentity {
    public PatientIdentity(Name p_name, Date p_dob){
        name = p_name;
        dob = p_dob;
    }

    private final Date dob;
    private final Name name;

    public Name getName() {
        return name;
    }
    public Date getDob() {
        return dob;
    }


    public boolean match( PatientIdentity other){
        return (name.match(other.getName())) && (dob.equals(other.getDob()));
    }

    public String toString(){
        return "name: " + name.toString() + " dob: " + dob.toString();
    }

    public boolean isLessThan(PatientIdentity other){ // sort by dob and name
        // same idea as name.isLessThan()
        int compareDob = dob.compareTo(other.getDob());

        if (compareDob < 0){
            return true;
        }
        if (compareDob > 0){
            return false;
        }

        return name.isLessThan(other.getName());
    }

}
