package hr.ferit.zvonimirpavlovic.factorynewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsSingleFragment extends Fragment {

    private TextView title;
    private TextView description;
    private ImageView imageView;
    private ArrayList<Article> articleList=new ArrayList<Article>();



    public NewsSingleFragment() {
        // Required empty public constructor
    }


    public static NewsSingleFragment newInstance(int i) {       // Fragmentu predajemo poziciju vijesti iz RecyclerViewa kako bi znali koju tocno vijest prikazati u Fragmentu
        NewsSingleFragment fragment = new NewsSingleFragment();
        Bundle args = new Bundle();
        args.putInt("position",i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        int pos = bundle.getInt("position");
        Log.d("POSITION",Integer.toString(pos));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title=view.findViewById(R.id.tvDetailedTitle);
        description=view.findViewById(R.id.tvDetailedDescription);
        imageView=view.findViewById(R.id.ivDetailImage);
        putText();
    }

    private void putText() {
        Bundle bundle =getArguments();
        Intent i=getActivity().getIntent();
        int position= bundle.getInt("position");                                // Povlacimo poziciju koju smo predali u newInstance
        Log.d("BUNDLE INT",Integer.toString(position));
        articleList = i.getParcelableArrayListExtra("articleList");           //Povlacimo listu vijesti koja se nalazi u RecyclerViewu
        title.setText(articleList.get(position).getTitle());                        //Ovisno o predanoj poziciji, povlacimo title, description i URL odredene vijesti i postavljamo ih u Textview i ImageView
        description.setText(articleList.get(position).getDescription());
        Picasso.with(getContext()).load(articleList.get(position).getUrlToImage()).fit().centerInside().into(imageView);
        getActivity().setTitle(i.getStringExtra("title"));                    // Mijenjamo naslov ActionBar-a ovisno o Fragmentu u kojem se nalazimo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_single, container, false);
    }

}
