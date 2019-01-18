package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Bookmark_Activity extends Activity {
    private SqliteDb sqliteDb;
    List<SqliteCreateTable> sqliteCreateTableList;
    RecyclerView recyclerView;
    BookmarkAdapter bookmarkAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        recyclerView = (RecyclerView)findViewById(R.id.recycler) ;

        sqliteDb = new SqliteDb(getApplicationContext());

        sqliteCreateTableList = new ArrayList<>();
        sqliteCreateTableList = sqliteDb.getAllSaveBookmark();

        bookmarkAdapter = new BookmarkAdapter(this, sqliteCreateTableList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(bookmarkAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
