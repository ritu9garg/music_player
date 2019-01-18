package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailPage_Activity extends AppCompatActivity {

    TextView artist_name;
    TextView playcount;
    TextView listeners;
    TextView summary;
    String url="";
    String url2="";
    private SqliteDb sqliteDb;
    ImageView bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);
        artist_name = (TextView) findViewById(R.id.artist_name);
        playcount = (TextView) findViewById(R.id.playcount);
        listeners = (TextView) findViewById(R.id.listeners);
        summary = (TextView) findViewById(R.id.summary);
        String artist_name = getIntent().getStringExtra("artist_name");
        url = Url.detailUrl+artist_name;
        showArtistDtail();

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                startActivity(browserIntent);
            }
        });
    }


    private void showArtistDtail(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj, obj2;
                            obj2 = new JSONObject(response);

                            obj2 = obj2.getJSONObject("artist");
                            artist_name.setText(obj2.getString("name"));

                            obj = obj2.getJSONObject("bio");

                            summary.setText(Html.fromHtml(obj.getString("summary")));
                            obj = obj.getJSONObject("links");
                            obj = obj.getJSONObject("link");
                            url2 = obj.getString("href");

                            obj = obj2.getJSONObject("stats");
                            playcount.setText(obj.getString("playcount"));
                            listeners.setText(obj.getString("listeners"));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError){

                        }else if (error instanceof ServerError){

                        }else{

                        }
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());;
        requestQueue.add(stringRequest);
    }
}
