package mirantyJmartAK.jmart_android.model;

import android.content.res.Resources;

import mirantyJmartAK.jmart_android.R;

/**
 * This class is about prodyct.
 *
 * @author Miranty Anjani Putri
 */

public class Product extends Serializable {
    public int accountId;
    public String name;
    public int weight;
    public boolean conditionUsed;
    public double price;
    public double discount;
    public ProductCategory category;
    public byte shipmentPlans;

    @Override
    public String toString() {
        return this.name;
    }
}