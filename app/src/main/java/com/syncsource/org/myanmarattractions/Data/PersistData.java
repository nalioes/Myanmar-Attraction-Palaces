package com.syncsource.org.myanmarattractions.Data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.syncsource.org.myanmarattractions.Data.DbOpenHelper;
import com.syncsource.org.myanmarattractions.Data.PlaceORM;
import com.syncsource.org.myanmarattractions.model.Attraction;
import com.syncsource.org.myanmarattractions.rest.ApiClient;
import com.syncsource.org.myanmarattractions.rest.Apiinterface;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SyncSource on 7/7/2016.
 */
public class PersistData {
    private Context context;
    private List<Attraction> attractions;
    private DbOpenHelper dbOpenHelper = null;
    private Dao<PlaceORM, Integer> placeDao;
    private final String BASEIMGURL = "http://www.aungpyaephyo.xyz/myanmar_attractions/";

    public PersistData(Context context) {
        this.context = context;
        dbOpenHelper = getDbOpenHelper();
    }

//    public void getPlace() {
//        final Apiinterface apiService =
//                ApiClient.getClient().create(Apiinterface.class);
//
//        Call<List<Attraction>> call = apiService.getPlaces();
//        call.enqueue(new Callback<List<Attraction>>() {
//            @Override
//            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
//
//                for (int i = 0; i < response.body().size(); i++) {
//                    List<String> imgList = response.body().get(i).getImages();
//                    StringBuilder sb = new StringBuilder();
//                    String delimiter = "*";
//                    for (String imgUrl : imgList) {
//                        if (sb.length() > 0) {
//                            sb.append(delimiter);
//                        }
//                        sb.append(BASEIMGURL+imgUrl);
//                    }
//                    String imgUrlStr = sb.toString();
//                    if (!existDataFromDb(response.body().get(i))) {
//                        try {
//                            saveData(response.body().get(i).getTitle(), response.body().get(i).getDesc(), imgUrlStr);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Attraction>> call, Throwable t) {
//
//            }
//        });
//    }

    public DbOpenHelper getDbOpenHelper() {
        if (dbOpenHelper == null) {
            dbOpenHelper = OpenHelperManager.getHelper(context, DbOpenHelper.class);
        }
        return dbOpenHelper;
    }

    public void saveData(String title, String description, String imgUrl) throws SQLException {
        dbOpenHelper = getDbOpenHelper();
        placeDao = dbOpenHelper.getPlaceDao();
        placeDao.create(new PlaceORM(title, description, imgUrl));
    }

    public boolean existDataFromDb(Attraction attraction) {
        String title = attraction.getTitle();
        boolean exist = false;
        try {
            if (dbOpenHelper.getPlaceDao().queryForAll().size() > 0) {
                List<PlaceORM> attaListORM = dbOpenHelper.getPlaceDao().queryForAll();
                for (int i = 0; i < dbOpenHelper.getPlaceDao().queryForAll().size(); i++) {
                    if (title.compareToIgnoreCase(attaListORM.get(i).getTitle()) == 0) {
                        exist = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }



}
