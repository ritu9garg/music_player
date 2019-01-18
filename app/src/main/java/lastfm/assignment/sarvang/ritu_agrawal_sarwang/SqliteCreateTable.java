package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

public class SqliteCreateTable {
    public static final String TABLE_NAME = "tbl_detailsof_artist";

    public static final String COLUMN_ID = "tbl_id";
    public static final String COLUMN_NAME = "tbl_name";
    public static final String COLUMN_IMAGE = "tbl_img";
    public static final String COLUMN_STREAMING = "tbl_streaming";
    public static final String COLUMN_LISTENERS = "tbl_listeners";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_STREAMING + " TEXT,"
                    + COLUMN_LISTENERS + " TEXT"
                    + ")";

    private int id;
    private String name;
    private String img;
    private String streaming;
    private String listeners;

    public SqliteCreateTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public SqliteCreateTable(int id, String name, String img, String streaming, String listeners) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.streaming = streaming;
        this.listeners = listeners;
    }


}
