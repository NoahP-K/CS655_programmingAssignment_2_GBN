import java.util.List;

public class Packet
{
    private int seqnum;
    private int acknum;
    private int checksum;
    private String payload;

    //Added for GBN implementation. Stores up to 5 seqNums for SACK
    private int[] sack;
    private int sackCount;  //Number of usable numbers in sack
    
    public Packet(Packet p)
    {
        seqnum = p.getSeqnum();
        acknum = p.getAcknum();
        checksum = p.getChecksum();
        payload = new String(p.getPayload());
        sack = new int[5];
        sackCount = 0;
    }
    
    public Packet(int seq, int ack, int check, String newPayload)
    {
        seqnum = seq;
        acknum = ack;
        checksum = check;
        if (newPayload == null)
        {
            payload = "";
        }        
        else if (newPayload.length() > NetworkSimulator.MAXDATASIZE)
        {
            payload = null;
        }
        else
        {
            payload = new String(newPayload);
        }
        sack = new int[5];
        sackCount = 0;
    }
    
    public Packet(int seq, int ack, int check)
    {
        seqnum = seq;
        acknum = ack;
        checksum = check;
        payload = "";
        sack = new int[5];
        sackCount = 0;
    }    
        

    public boolean setSeqnum(int n)
    {
        seqnum = n;
        return true;
    }
    
    public boolean setAcknum(int n)
    {
        acknum = n;
        return true;
    }
    
    public boolean setChecksum(int n)
    {
        checksum = n;
        return true;
    }
    
    public boolean setPayload(String newPayload)
    {
        if (newPayload == null)
        {
            payload = "";
            return false;
        }        
        else if (newPayload.length() > NetworkSimulator.MAXDATASIZE)
        {
            payload = "";
            return false;
        }
        else
        {
            payload = new String(newPayload);
            return true;
        }
    }

    //new set method for sack array.
    //Pulls from a given list in FIFO order.
    //Added argument to pick whether to append to sack list or overwrite it.
    public void setSack(List<Integer> nums, boolean overwrite) {
        if(overwrite) {
            sack = new int[5];
            sackCount = 0;
        }
        setSack(nums);
    }
    //I can also call this directly for default no-overwrite
    public void setSack(List<Integer> nums) {
        while(sackCount < 5 && !nums.isEmpty()) {
            sack[sackCount] = nums.removeFirst();
            sackCount++;
        }
    }

    
    public int getSeqnum()
    {
        return seqnum;
    }
    
    public int getAcknum()
    {
        return acknum;
    }
    
    public int getChecksum()
    {
        return checksum;
    }
    
    public String getPayload()
    {
        return payload;
    }
    
    public String toString()
    {
        return("seqnum: " + seqnum + "  acknum: " + acknum + "  checksum: " +
               checksum + "  payload: " + payload);
    }

    //new get methods for the sack info
    public int[] getSack(){
        return sack;
    }
    public int getSackCount(){
        return sackCount;
    }
}
