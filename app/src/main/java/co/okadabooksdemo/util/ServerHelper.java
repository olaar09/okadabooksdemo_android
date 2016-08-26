package co.okadabooksdemo.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olaar on 8/24/16.
 */
public class ServerHelper {


    private Context context;
    // public ServerHelperCallbacks helperCallbacks;
    private static final String URL = "https://okadabooks.herokuapp.com/index.php";

    public ServerHelper(Context context) {
        this.context = context;
    }


    public JSONObject getDelParams(String id) {
        JSONObject params = new JSONObject();
        try {
            params.put("book_id", "2");
            params.put("rm_book", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    public void addData(final int type, final String data1, final String data2, final ServerHelperCallbacks ServerHelperCallbacks) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL + ((type ==1)? "?add_book=true&book_name=" + data1 + "&book_price=" + data2: "?add_user=true&firstname=" + data1 + "&lastname=" + data2), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObj = response;
                    Log.d("TAG-ADD", jsonObj.toString());
                    ServerHelperCallbacks.onSuccess(jsonObj);

                } catch (Exception e) {
                    e.printStackTrace();
                    ServerHelperCallbacks.onFail(e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                ServerHelperCallbacks.onFail(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public void rmData(int type, String id, final ServerHelperCallbacks ServerHelperCallbacks) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL + ((type == 1) ? "?rm_book=true" : "?rm_user=true") + ((type == 1) ? "&book_id=" + id : "&user_id=" + id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObj = response;
                    Log.d("TAG", jsonObj.toString());
                    if (jsonObj.getString("status").equals("200")) {
                        ServerHelperCallbacks.onSuccess(jsonObj);
                    } else {
                        ServerHelperCallbacks.onFail(jsonObj.get("status").toString());
                    }

                } catch (Exception e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                ServerHelperCallbacks.onFail(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public void getData(int type, final ServerHelperCallbacks helperCallbacks, final DataStatusCallBacks statusCallBacks) {

        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, URL + ((type == 1) ? "?get_books=true" : "?get_users=true"), null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;
                            Log.d("TAG-GET", jsonArray.toString());
                            helperCallbacks.onSuccess(jsonArray);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GGG", error.toString());
                        if (error.toString().contains("{\"status\":404}")){
                            statusCallBacks.onDataNotAvailable();
                        }else {
                            helperCallbacks.onFail(error.getMessage());
                        }

                    }
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }


    public interface ServerHelperCallbacks {

        void onSuccess(JSONArray jsonArr);

        void onSuccess(JSONObject jsonObject);

        void onFail(String err);
    }

    public interface DataStatusCallBacks {
        void onDataNotAvailable();
    }
}
