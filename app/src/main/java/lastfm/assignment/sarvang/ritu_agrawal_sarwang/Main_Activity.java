package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main_Activity extends AppCompatActivity implements Connection_Testing.ConnectivityReceiverListener {

    EditText et_search;
    RecyclerView recyclerView;
    TextView txt_search;

    RequestQueue requestQueue;
    String url = "";
    List<GetterSetter> getterSetterList;
    Artist_Adapter artistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_search = (EditText) findViewById(R.id.edt_search);
        recyclerView = (RecyclerView) findViewById(R.id.search_list);
        txt_search = (TextView) findViewById(R.id.txt_search);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        getterSetterList = new ArrayList<>();
        artistAdapter = new Artist_Adapter(this, getterSetterList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setAdapter(artistAdapter);
        recyclerView.setLayoutManager(lm);

        checkConnection();
    }

    public void onClickSearch(View view) {
        hideKeyboard(view);
        url = Url.searchUrl + et_search.getText().toString();
        searchArtist();
    }

    private void searchArtist() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            showList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {

                        } else if (error instanceof ServerError) {

                        } else {

                        }
                    }
                });

        requestQueue.add(stringRequest);
    }

    private void showList(String res) {
        try {

            JSONObject object, object1;
            object = new JSONObject(res);
            object = object.getJSONObject("results");

            if (object.getString("opensearch:totalResults").equals("0")) {
                txt_search.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                txt_search.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            object = object.getJSONObject("artistmatches");
            JSONArray array, arr_img;
            array = object.getJSONArray("artist");
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                GetterSetter gs = new GetterSetter();
                gs.setArtist_Name(object.getString("name"));
                gs.setStreaming(object.getString("streamable"));
                gs.setListeners(object.getString("listeners"));
                arr_img = object.getJSONArray("image");
                object1 = arr_img.getJSONObject(2);
                gs.setArtist_Image(object1.getString("#text"));
                getterSetterList.add(gs);
            }
            artistAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            txt_search.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void checkConnection() {
        boolean isConnected = Connection_Testing.isConnected();
        internetStatus(isConnected);
    }

    private void internetStatus(boolean isConnected) {

        Toast.makeText(this, "Please check your internet connection....", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Connection.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        internetStatus(isConnected);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.bookmark:
                Intent i = new Intent(this, Bookmark_Activity.class);
                startActivity(i);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}