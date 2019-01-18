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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyAdapter> {

    Activity activity;
    List<SqliteCreateTable> sqliteCreateTableList;
    private SqliteDb sqliteDb;
    TextView empty;

    public BookmarkAdapter(Activity activity, List<SqliteCreateTable> sqliteCreateTableList) {
        this.activity = activity;
        this.sqliteCreateTableList = sqliteCreateTableList;
        sqliteDb = new SqliteDb(activity);
    }

    @NonNull
    @Override
    public BookmarkAdapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_item, null);
        return new MyAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookmarkAdapter.MyAdapter holder, final int position) {
        final SqliteCreateTable tb = sqliteCreateTableList.get(position);
        try {
            Picasso.get().load(tb.getImg()).error(R.drawable.splash_pic).into(holder.img);
        }catch (Exception e){}
        holder.name.setText(tb.getName());
        holder.listener.setText(tb.getListeners()+" Listeners");
        holder.streaming.setText(tb.getStreaming()+" Streaming");

    }

    @Override
    public int getItemCount() {
        return sqliteCreateTableList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView name, streaming, listener;
        ImageView img, bookmark;
        public MyAdapter(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            streaming = itemView.findViewById(R.id.streaming);
            listener = itemView.findViewById(R.id.listener);
            bookmark = itemView.findViewById(R.id.bookmark);
        }
    }
}
