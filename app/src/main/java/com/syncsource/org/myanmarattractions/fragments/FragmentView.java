package com.syncsource.org.myanmarattractions.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.syncsource.org.myanmarattractions.adapter.AttractAdapter;
import com.syncsource.org.myanmarattractions.model.Attraction;
import com.syncsource.org.myanmarattractions.Data.DbOpenHelper;
import com.syncsource.org.myanmarattractions.Data.PlaceORM;
import com.syncsource.org.myanmarattractions.PersistData;
import com.syncsource.org.myanmarattractions.R;
import com.syncsource.org.myanmarattractions.rest.ApiClient;
import com.syncsource.org.myanmarattractions.rest.Apiinterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SyncSource on 7/7/2016.
 */
public class FragmentView extends Fragment {
    private AttractAdapter attractAdapter;
    private RecyclerView recyclerView;
    private DbOpenHelper dbOpenHelper = null;
    private final String BASEIMGURL = "http://www.aungpyaephyo.xyz/myanmar_attractions/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.canScrollVertically(0);

        dbOpenHelper = getDbOpenHelper();
        getPlace();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromDb();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void getDataFromDb() {
        List<Attraction> placeAttactionList = new ArrayList<>();
        try {
            if (dbOpenHelper.getPlaceDao().queryForAll().size() > 0) {
                List<PlaceORM> attaListORM = dbOpenHelper.getPlaceDao().queryForAll();
                for (int i = 0; i < dbOpenHelper.getPlaceDao().queryForAll().size(); i++) {
                    String strImgUrl = attaListORM.get(i).getImgUrl();
                    String[] split = strImgUrl.split(Pattern.quote("*"));
                    List<String> imgUrl = Arrays.asList(split);
                    placeAttactionList.add(new Attraction(attaListORM.get(i).getTitle(), attaListORM.get(i).getDescription(), imgUrl));
                }
                attractAdapter = new AttractAdapter(getActivity().getApplicationContext(), placeAttactionList);
                recyclerView.setAdapter(attractAdapter);
                attractAdapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DbOpenHelper getDbOpenHelper() {
        if (dbOpenHelper == null) {
            dbOpenHelper = OpenHelperManager.getHelper(getActivity().getApplicationContext(), DbOpenHelper.class);
        }
        return dbOpenHelper;
    }

    public void getPlace() {
        final Apiinterface apiService =
                ApiClient.getClient().create(Apiinterface.class);
        final PersistData persistData = new PersistData(getContext());
        Call<List<Attraction>> call = apiService.getPlaces();
        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {

                for (int i = 0; i < response.body().size(); i++) {
                    List<String> imgList = response.body().get(i).getImages();
                    StringBuilder sb = new StringBuilder();
                    String delimiter = "*";
                    for (String imgUrl : imgList) {
                        if (sb.length() > 0) {
                            sb.append(delimiter);
                        }
                        sb.append(BASEIMGURL + imgUrl);
                    }
                    String imgUrlStr = sb.toString();
                    if (!persistData.existDataFromDb(response.body().get(i))) {
                        try {
                            persistData.saveData(response.body().get(i).getTitle(), response.body().get(i).getDesc(), imgUrlStr);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                getDataFromDb();

            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "No network connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
