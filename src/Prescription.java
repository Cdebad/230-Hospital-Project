import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescription {
    private String name;
    private Date date_of_issue;
    private Integer dosage;
    private String prescriber;

    public Prescription(String p_name, Date p_date_of_issue, Integer p_dosage, String p_prescriber){
        name = p_name;
        date_of_issue = p_date_of_issue;
        dosage = p_dosage;
        prescriber = p_prescriber;
    }

    public String getName(){return name;}
    public Date getDate_of_issue(){return date_of_issue;}
    public String getPrescriber(){return prescriber;}

    public static boolean comesBefore(Prescription pr1, Prescription pr2){
        return pr1.getDate_of_issue().compareTo(pr2.getDate_of_issue()) > 0;
    }


    private static Date makeDate(String dateStr){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e){
            return null;
        }
    }

    public static Prescription makePrescription(String line){ //factory for file loading
        try {
            String[] parts = line.split(",");
            if (parts.length < 7) {
                return null;
            }

            Date issuedDate = makeDate(parts[4]);
            if (issuedDate == null) {
                return null;
            }

            //dosage is int, parsing so wrapped in a try/catch
            Integer dosage = Integer.parseInt(parts[5]);

            return new Prescription(parts[3], issuedDate, dosage, parts[6]);//med name, date, dose, prescriber
        } catch (Exception e) {
            return null;
        }
    }
}
