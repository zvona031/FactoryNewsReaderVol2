package hr.ferit.zvonimirpavlovic.factorynewsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {


    private Context mContext;
    private List<Article> articleList;
    private OnItemClickListener itemClickListener;

    public RecyclerAdapter(Context context, ArrayList<Article> dataList,OnItemClickListener listener){
        mContext=context;
        articleList=dataList;
        itemClickListener=listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.cell_news,viewGroup,false);
        return new NewsViewHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        final Article currentCell=articleList.get(i);

        newsViewHolder.mTvTitle.setText(currentCell.getTitle());
        newsViewHolder.mTvDescription.setText(currentCell.getDescription());
        Picasso.with(mContext).load(currentCell.getUrlToImage()).fit().centerInside().into(newsViewHolder.mIvImage);

        }


    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
