package hr.ferit.zvonimirpavlovic.factorynewsreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mIvImage;
    public TextView mTvTitle;
    public TextView mTvDescription;
    private OnItemClickListener itemClickListener;


    public NewsViewHolder(@NonNull View itemView,OnItemClickListener listener) {
        super(itemView);
        mIvImage=itemView.findViewById(R.id.ivImage);
        mTvTitle=itemView.findViewById(R.id.tvTitle);
        mTvDescription=itemView.findViewById(R.id.tvDescription);
        itemClickListener=listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onItemClick(getAdapterPosition());
    }
}
