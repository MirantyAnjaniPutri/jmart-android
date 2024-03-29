package mirantyJmartAK.jmart_android.request;

import com.android.volley.Response;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.toolbox.StringRequest;

/**
 * Requesting so user can login
 *
 * @author Miranty Anjani Putri
 */

public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/login";
    private final HashMap<String, String> params;

    public LoginRequest (String email, String password, Response.Listener<String> listener,
                         Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password", password);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
