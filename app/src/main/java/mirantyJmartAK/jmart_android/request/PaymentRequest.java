package mirantyJmartAK.jmart_android.request;

import static mirantyJmartAK.jmart_android.FirstFragment.clickedProduct;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mirantyJmartAK.jmart_android.FirstFragment;
import mirantyJmartAK.jmart_android.LoginActivity;

/**
 * Requesting a payment to be created.
 *
 * @author Miranty Anjani Putri
 */
public class PaymentRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/payment/create";
    public final Map<String,String> params;

    public PaymentRequest
            (int productCount,
             String ShipmentAddress,
             byte ShipmentPlans,
             Response.Listener<String> listener,
             @Nullable Response.ErrorListener errorListener
            )
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        Integer i = LoginActivity.getLoggedAccount().id;
        params.put("buyerId", i.toString());
        params.put("productId", String.valueOf(clickedProduct.id));
        params.put("productCount",String.valueOf(productCount));
        params.put("shipmentAddress",ShipmentAddress);
        params.put("ShipmentPlan",String.valueOf(ShipmentPlans));
    }

    public Map<String,String> getParams(){return params;}
}
