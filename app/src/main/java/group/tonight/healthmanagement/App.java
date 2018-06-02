package group.tonight.healthmanagement;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

import group.tonight.healthmanagement.dao.DaoMaster;
import group.tonight.healthmanagement.dao.DaoSession;

public class App extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "users-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public static DaoSession getDaoSession() {
        daoSession.clear();
        return daoSession;
    }
}
