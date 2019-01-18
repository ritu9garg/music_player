package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

import android.app.Application;

public class Connection extends Application {

    private static Connection mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized Connection getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(Connection_Testing.ConnectivityReceiverListener listener) {
        Connection_Testing.receiverListener = listener;
    }
}
