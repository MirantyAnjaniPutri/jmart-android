package mirantyJmartAK.jmart_android.request;

import androidx.annotation.Nullable;

import mirantyJmartAK.jmart_android.LoginActivity;
import mirantyJmartAK.jmart_android.model.ProductCategory;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Requesting to create a product.
 *
 * @author Miranty Anjani Putri
 */

public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String, String> params;

    public CreateProductRequest(String name, int weight, boolean conditionUsed, double price, double discount, ProductCategory category, byte shipmentPlans, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        Integer accountId = LoginActivity.getLoggedAccount().id;
        params.put("accountId", String.valueOf(accountId));
        params.put("name", name);
        params.put("weight", String.valueOf(weight));
        params.put("conditionUsed", String.valueOf(conditionUsed));
        params.put("price", String.valueOf(price));
        params.put("discount", String.valueOf(discount));
        params.put("category", String.valueOf(category));
        params.put("shipmentPlans", String.valueOf(shipmentPlans));
    }

    @Nullable
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}