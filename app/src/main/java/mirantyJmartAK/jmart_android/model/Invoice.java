package mirantyJmartAK.jmart_android.model;

import static mirantyJmartAK.jmart_android.model.Payment.productCount;
import static mirantyJmartAK.jmart_android.model.Payment.shipment;

import java.util.Date;

/**
 * This class functions as an invoice.
 * It will record the date, buyerId, complaintId,
 * productId, rating, status of the order.
 *
 * @author Miranty Anjani Putri
 */

public abstract class Invoice extends Serializable {
    public final Date date = new Date();
    public int  buyerId;
    public int complaintId;
    public int productId;
    public Rating rating;
    public Status status;

    public static enum Rating {
        BAD, GOOD, NEUTRAL, NONE;
    }

    public enum Status {
        CANCELLED, COMPLAINT, FAILED, FINISHED, ON_DELIVERY, ON_PROGRESS, WAITING_CONFIRMATION;
    }

    public double getTotalPay(Product product) {
        return product.price;
    }
}