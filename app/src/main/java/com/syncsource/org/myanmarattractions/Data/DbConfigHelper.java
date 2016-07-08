package com.syncsource.org.myanmarattractions.Data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by SyncSource on 7/7/2016.
 */
public class DbConfigHelper extends OrmLiteConfigUtil {
    public static void main(String []args) throws SQLException,IOException {

        writeConfigFile("ormlite_config.txt");
    }
}
