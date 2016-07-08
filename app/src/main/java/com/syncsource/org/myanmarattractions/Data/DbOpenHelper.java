package com.syncsource.org.myanmarattractions.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.syncsource.org.myanmarattractions.R;

import java.sql.SQLException;

/**
 * Created by SyncSource on 7/7/2016.
 */
public class DbOpenHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "db_place";
    private static final int DATABASE_VERSION = 1;
    private Dao<PlaceORM, Integer> placeDao;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource,PlaceORM.class);
        }catch (SQLException e){
            Log.i(DbConfigHelper.class.getName(), "onCreate");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource,PlaceORM.class,true);
            onCreate(database,connectionSource);
        }catch(SQLException e){
            Log.e(DbConfigHelper.class.getName(),
                    "Unable to upgrade database from version " + oldVersion + " to new " + newVersion, e);
            e.printStackTrace();
        }
    }

    public Dao<PlaceORM, Integer> getPlaceDao() throws SQLException {

        if (placeDao == null) {
            placeDao = getDao(PlaceORM.class);
        }
        return placeDao;

    }
}
