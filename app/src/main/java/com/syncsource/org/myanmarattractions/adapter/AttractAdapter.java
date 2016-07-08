package com.syncsource.org.myanmarattractions.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.myanmarattractions.activities.AttractPlaceDetailActivity;
import com.syncsource.org.myanmarattractions.model.Attraction;
import com.syncsource.org.myanmarattractions.R;

import java.util.List;

/**
 * Created by SyncSource on 7/7/2016.
 */
public class AttractAdapter extends RecyclerView.Adapter<AttractAdapter.ImageViewHolder> {

    private List<Attraction> attractionList;
    private Context context;
    private final String BASEIMGURL = "http://www.aungpyaephyo.xyz/myanmar_attractions/";

    public AttractAdapter(Context context, List<Attraction> itemList) {
        this.context = context;
        this.attractionList = itemList;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

      public ImageView imageContent;
        TextView txtTitle;
        TextView txtDescrition;
        Button btnMoreDetail;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageContent = (ImageView) itemView.findViewById(R.id.imaSE);
            txtTitle = (TextView) itemView.findViewById(R.id.ftitle);
            txtDescrition = (TextView) itemView.findViewById(R.id.fcontent);
            btnMoreDetail = (Button) itemView.findViewById(R.id.btn_detail);
        }

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        view.setVisibility(view.VISIBLE);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        final Attraction myAttractionPlace = attractionList.get(position);
        holder.txtTitle.setText(myAttractionPlace.getTitle());
        holder.txtDescrition.setText(myAttractionPlace.getDesc());
        String image = myAttractionPlace.getImages().get(0);

        Glide.with(context)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imageContent);

        holder.btnMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new AttractPlaceDetailActivity().newInstance(myAttractionPlace.getTitle());
//                context.startActivity(intent);
                Intent intent = new Intent(context, AttractPlaceDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", myAttractionPlace.getTitle());
                context.startActivity(intent);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttractPlaceDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", myAttractionPlace.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractionList.size();
    }
}

