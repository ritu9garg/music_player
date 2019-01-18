package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.List;


public class Artist_Adapter extends RecyclerView.Adapter<Artist_Adapter.MyAdapter> {

    private SqliteDb sqliteDb;
    Activity activity;
    List<GetterSetter> getterSetterList;


    public Artist_Adapter(Activity activity, List<GetterSetter> getterSetterList) {
        this.activity = activity;
        this.getterSetterList = getterSetterList;
        sqliteDb = new SqliteDb(activity);
    }

    @NonNull
    @Override
    public Artist_Adapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, null);
        return new MyAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Artist_Adapter.MyAdapter holder, int position) {
        final GetterSetter gs = getterSetterList.get(position);

        holder.artist_name.setText(gs.getArtist_Name());
        holder.listeners.setText(gs.getListeners()+" Listeners");
        holder.streaming.setText(gs.getStreaming()+" Streaming");
        try {
            Picasso.get().load(gs.getArtist_Image()).error(R.drawable.splash_pic).into(holder.search_list_img);
        }catch (Exception e){}


        holder.search_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, DetailPage_Activity.class);
                i.putExtra("artist_name", gs.getArtist_Name());
                activity.startActivity(i);
            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkSave(gs.getArtist_Image(), gs.getArtist_Image(), gs.getStreaming(), gs.getListeners());
            }
        });
    }


    private void bookmarkSave(String Name, String Img, String Streaming, String listener) {
        long id = sqliteDb.BookmarkDetail(Name,Img,Streaming,listener);
        Toast.makeText(activity, "Your choice is save", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return getterSetterList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {

        LinearLayout search_item;
        TextView artist_name, streaming, listeners;
        ImageView search_list_img, bookmark;
        public MyAdapter(View itemView) {
            super(itemView);
            search_list_img =(ImageView) itemView.findViewById(R.id.image);
            artist_name = (TextView) itemView.findViewById(R.id.name);
            streaming = (TextView) itemView.findViewById(R.id.streaming);
            listeners = (TextView) itemView.findViewById(R.id.listeners);
            search_item = (LinearLayout) itemView.findViewById(R.id.show_item);
            bookmark = (ImageView) itemView.findViewById(R.id.bookmark);
        }
    }
}
