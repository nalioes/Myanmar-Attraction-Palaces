package com.syncsource.org.myanmarattractions.rest;

import com.syncsource.org.myanmarattractions.model.Attraction;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by SyncSource on 5/19/2016.
 */
public interface Apiinterface {

    @GET("myanmar_attractions/myanmar_attractions.json")
    Call<List<Attraction>> getPlaces();

}

