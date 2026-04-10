public class PrescriptionList {
    public PrescriptionList() { head = null; }

    private class ListRecord {
        public Prescription data;
        public ListRecord next;
        public ListRecord( Prescription pr ) { data = pr; next = null; }
    }

    private ListRecord head;

    public void add( Prescription pr ) {
        ListRecord padd = new ListRecord(pr);
        ListRecord pbefore = head;

        //empty list
        if (head==null){
            head=padd;
            return;
        }

        //record before head
        if (Prescription.comesBefore(padd.data,head.data)){
            padd.next=head;
            head=padd;
            return;
        }

        while (pbefore.next != null && Prescription.comesBefore(pbefore.next.data,padd.data)){//needs .next.data
            pbefore=pbefore.next;
        }
        padd.next=pbefore.next;
        pbefore.next=padd;
    };

    private ListRecord iter_record = null;

    public void init(){iter_record=head;}

    public Prescription next(){
        if (iter_record == null) {return null;}
        Prescription current = iter_record.data;
        iter_record = iter_record.next;
        return current;
    }
}
