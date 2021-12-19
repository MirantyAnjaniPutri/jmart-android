package mirantyJmartAK.jmart_android.request;

import mirantyJmartAK.jmart_android.LoginActivity;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is for registering a store.
 *
 * @author Miranty Anjani Putri
 */
public class StoreRequest extends StringRequest {
    private static final String URL =  "http://10.0.2.2:8080/account/" + LoginActivity.getLoggedAccount().id + "/storeRegister";
    private final Map<String , String> params;

    public StoreRequest
            (
                    int id,
                    String name,
                    String address,
                    String phoneNumber,
                    Response.Listener<String> listener,
                    Response.ErrorListener errorListener
            )
    {
        super(Method.POST, URL, listener, errorListener);
        Integer  i = id;
        params = new HashMap<>();
        params.put("id", i.toString());
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    public Map<String , String> getParams() {
        return params;
    }
}