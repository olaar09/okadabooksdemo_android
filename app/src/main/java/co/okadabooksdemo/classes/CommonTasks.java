package co.okadabooksdemo.classes;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import co.okadabooksdemo.adapter.BookListAdapter;
import co.okadabooksdemo.util.ServerHelper;

/**
 * Created by olaar on 8/23/16.
 */
public class CommonTasks {

    private Context context;

    public CommonTasks(Context context) {
        this.context = context;
    }

    public CommonTasks() {

    }

    public void getItems(final ProgressBar loading, final LinearLayout errLayout, final List itemsList, final GetItemCallBack getItemCallBack, int type) {
        toggleErr(loading, errLayout, false);
        toggleLoading(loading, errLayout, true);

        ServerHelper serverHelper = new ServerHelper(context);
        serverHelper.getData(type, new ServerHelper.ServerHelperCallbacks() {
            @Override
            public void onSuccess(JSONArray jsonArr) {
                toggleErr(loading, errLayout, false);
                toggleLoading(loading, errLayout, false);
                getItemCallBack.callBack(itemsList, jsonArr);
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {

            }

            @Override
            public void onFail(String err) {
                toggleLoading(loading, errLayout, false);
                toggleErr(loading, errLayout, true);
            }
        }, new ServerHelper.DataStatusCallBacks() {
            @Override
            public void onDataNotAvailable() {
                toggleErr(loading, errLayout, false);
                toggleLoading(loading, errLayout, false);
            }
        });
    }

    public void deleteItem(String id, String deletingString, final String deletedString, final String couldntDeleteString, final int pos, final List itemsList, final BookListAdapter adapter, int type) {
        Toast.makeText(context, deletingString, Toast.LENGTH_SHORT).show();
        ServerHelper serverHelper = new ServerHelper(context);
        serverHelper.rmData(type, id, new ServerHelper.ServerHelperCallbacks() {
            @Override
            public void onSuccess(JSONArray jsonArr) {

            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status") == 200) {
                        Toast.makeText(context, deletedString, Toast.LENGTH_LONG).show();
                        itemsList.remove(pos);
                        adapter.notifyItemRemoved(pos);
                    } else {
                        Toast.makeText(context, couldntDeleteString, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFail(String err) {

            }
        });
    }

    public void addItem(final Activity activity, final ProgressBar loading, final LinearLayout errLayout, final String addedString, final String couldntAddString, String data1, String data2, int type) {
        ServerHelper serverHelper = new ServerHelper(context);
        serverHelper.addData(type, data1.trim(), data2.trim(), new ServerHelper.ServerHelperCallbacks() {
            @Override
            public void onSuccess(JSONArray jsonArr) {
                // finish();
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Toast.makeText(context, addedString, Toast.LENGTH_LONG).show();
                activity.finish();
            }

            @Override
            public void onFail(String err) {
                Toast.makeText(context, couldntAddString, Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
                errLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    public void toggleErr(ProgressBar loading, LinearLayout errLayout, boolean toggle) {
        if (toggle) {
            setLoadVisibility(loading, false);
            setErrVisibility(errLayout, true);
        } else {
            setLoadVisibility(loading, false);
            setErrVisibility(errLayout, false);
        }
    }

    public void toggleLoading(ProgressBar loading, LinearLayout errLayout, boolean toggle) {
        if (toggle) {
            setLoadVisibility(loading, true);
            setErrVisibility(errLayout, false);
        } else {
            setLoadVisibility(loading, false);
            setErrVisibility(errLayout, false);
        }
    }

    public void setLoadVisibility(ProgressBar loading, boolean toggle) {
        if (toggle) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.INVISIBLE);
        }
    }

    public void setErrVisibility(LinearLayout errLayout, boolean toggle) {
        if (toggle) {
            errLayout.setVisibility(View.VISIBLE);
        } else {
            errLayout.setVisibility(View.INVISIBLE);
        }
    }

    public interface GetItemCallBack {
        void callBack(List itemsList, JSONArray jsonArr);
    }
}
