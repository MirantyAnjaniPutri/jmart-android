package mirantyJmartAK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mirantyJmartAK.jmart_android.LoginActivity;

/**
 * This account is used to request to top-up the balance of the user.
 *
 * @author Miranty Anjani Putri
 */
public class TopUpRequest extends StringRequest {
    private static final String URL =  "http://10.0.2.2:8080/account/" + LoginActivity.getLoggedAccount().id + "/topUp";
    private final Map<String , String> params;

    public TopUpRequest(int id, double balance, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        String sId = String.valueOf(id);
        params.put("id", sId);
        String sBalance = String.valueOf(balance);
        params.put("balance",sBalance);
    }

    public Map<String,String> getParams(){return params;}
}
