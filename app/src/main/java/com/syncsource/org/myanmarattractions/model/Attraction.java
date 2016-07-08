package com.syncsource.org.myanmarattractions.model;
/**
 * Created by SyncSource on 7/6/2016.
 */

import java.util.List;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attraction {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("images")
    @Expose
    private List<String> images = new ArrayList<String>();

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    public Attraction(String title, String desc, List<String> images) {
        this.title = title;
        this.desc = desc;
        this.images = images;
    }
}