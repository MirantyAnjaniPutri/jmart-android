package mirantyJmartAK.jmart_android.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is class for representing a shipment
 *
 * @author Miranty Anjani
 */

public class Shipment {
    public final Plan INSTANT = new Plan ((byte) (1 << 0)); //0000 0001
    public final Plan SAME_DAY = new Plan ((byte)(1 << 1)); //0000 0010
    public final Plan NEXT_DAY = new Plan ((byte)(1 << 2)); //0000 0100
    public final Plan REGULER = new Plan ((byte)(1 << 3)); //0000 1000
    public final Plan KARGO = new Plan ((byte)(1 << 4)); //0001 0000
    public String address;
    public int cost;
    public static byte plan;
    public String receipt;
    public static final SimpleDateFormat ESTIMATION_FORMAT = new SimpleDateFormat("'Date Format' E, MM/dd/yyyy");

    //Constructor
    public Shipment(String address, int cost, byte plan, String receipt){
        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;
    }

    //Inner Class Duration --> Plan
    public static class Plan
    {
        public final byte bit;

        public Plan(byte bit) {
            this.bit = bit;
        }
    }

    public String getEstimatedArrival (Date reference) {
        Calendar temp = Calendar.getInstance();
        if(this.plan == 1<<0 || this.plan == 1<<1)
        {
            return ESTIMATION_FORMAT.format(reference.getTime());
        }
        else if(this.plan == 1<<2)
        {
            temp.setTime(reference);
            temp.add(Calendar.DATE,1);
            return ESTIMATION_FORMAT.format(temp);
        }
        else if(this.plan == 1<<3)
        {
            temp.setTime(reference);
            temp.add(Calendar.DATE,2);
            return ESTIMATION_FORMAT.format(temp);
        }
        else
        {
            temp.setTime(reference);
            temp.add(Calendar.DATE,5);
            return ESTIMATION_FORMAT.format(temp);
        }
    }

    public boolean isDuration (Plan reference) {
        return (reference.bit & this.plan) != 0;
    }
}
