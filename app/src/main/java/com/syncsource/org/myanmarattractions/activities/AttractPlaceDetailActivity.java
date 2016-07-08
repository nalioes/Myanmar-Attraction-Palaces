package com.syncsource.org.myanmarattractions.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.syncsource.org.myanmarattractions.Data.DbOpenHelper;
import com.syncsource.org.myanmarattractions.Data.PlaceORM;
import com.syncsource.org.myanmarattractions.R;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AttractPlaceDetailActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper = null;
    private static final String ARGS = "title";
    private ImageView detailImg;
    private TextView detailTitle;
    private TextView detailContent;
    private String deTitle ,contentDescription;
    private List<String>imgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailImg = (ImageView) findViewById(R.id.de_content_img);
        detailTitle = (TextView) findViewById(R.id.de_title);
        detailContent = (TextView) findViewById(R.id.de_txt_content);
        dbOpenHelper = getDbOpenHelper();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        getDataFromDb(intent.getStringExtra(ARGS));
        detailTitle.setText(deTitle);
        detailContent.setText(contentDescription);

        Glide.with(getApplicationContext())
                .load(imgList.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(detailImg);
    }

    public void getDataFromDb(String title) {
        try {
            if (dbOpenHelper.getPlaceDao().queryForAll().size() > 0) {
                List<PlaceORM> attaListORM = dbOpenHelper.getPlaceDao().queryForAll();
                for (int i = 0; i < dbOpenHelper.getPlaceDao().queryForAll().size(); i++) {
                    if (title.compareToIgnoreCase(attaListORM.get(i).getTitle()) == 0) {
                        String strImgUrl = attaListORM.get(i).getImgUrl();
                        String[] split = strImgUrl.split(Pattern.quote("*"));
                        imgList = Arrays.asList(split);
                        deTitle= attaListORM.get(i).getTitle();
                        contentDescription = attaListORM.get(i).getDescription();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DbOpenHelper getDbOpenHelper() {
        if (dbOpenHelper == null) {
            dbOpenHelper = OpenHelperManager.getHelper(getApplicationContext().getApplicationContext(), DbOpenHelper.class);
        }
        return dbOpenHelper;
    }

    public Intent newInstance(String passArgs) {
        Intent intent = new Intent(getApplicationContext(), AttractPlaceDetailActivity.class);
        intent.putExtra(ARGS, passArgs);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return intent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
