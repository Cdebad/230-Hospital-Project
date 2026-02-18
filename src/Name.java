public class Name {
    public Name(String p_first, String p_last){
        first = p_first;
        last = p_last;
    }

    private final String first;
    private final String last;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String fullName(){
        return last + ", " + first; //could also use formatting
    }

    public String toString(){
        return fullName();
    }

    public boolean match(Name other){
        return first.equalsIgnoreCase(other.getFirst()) && last.equalsIgnoreCase(other.getLast());
    }

    public boolean isLessThan(Name other){ //sort by last name first, then first name if the same.
        int compareLast = last.toLowerCase().compareTo(other.getLast().toLowerCase());

        if (compareLast > 0){
            return false;
        }
        if (compareLast < 0){
            return true;
        }

        //compare first names if last are the same
        return first.toLowerCase().compareTo(other.getFirst().toLowerCase()) < 0;
    }

}