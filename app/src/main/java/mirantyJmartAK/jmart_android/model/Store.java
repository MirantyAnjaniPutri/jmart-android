package mirantyJmartAK.jmart_android.model;

public class Store extends Serializable {
    public String address;
    public String name;
    public String phoneNumber;

    public Store (String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}