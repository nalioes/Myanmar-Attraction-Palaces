package com.syncsource.org.myanmarattractions.Data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SyncSource on 7/7/2016.
 */
@DatabaseTable(tableName = "tb_place")
public class PlaceORM {
    @DatabaseField(generatedId = true)
    private static int uid;

    @DatabaseField
    private String title;

    @DatabaseField
    private String Description;

    @DatabaseField
    private String imgUrl;

    public PlaceORM() {
    }

    public PlaceORM(String title, String description, String imgUrl) {
        this.title = title;
        Description = description;
        this.imgUrl = imgUrl;
    }

    public static int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
