package hr.ferit.zvonimirpavlovic.factorynewsreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    private RecyclerView recycler;
    private RecyclerAdapter adapter;
    private ArrayList<Article> articleList;
    private Call<NewsCell> apiCall;
    private ProgressBar progressBar;
    private Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        initProgressBar();
        showProgress();           //Pri kreiranju Activitya prvo vidimo loader
        startRepeatingTask();    //Pocinje se obavljati funkcija svakih 5 minuta
    }

    private void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Greška");
        builder.setMessage("Ups, dogodila se pogreška.");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setUpApiCall() {
        apiCall = NetworkUtils.getApiInterface().getNews("6946d0c07a1c4555a4186bfcade76398", "top", "bbc-news");
        apiCall.enqueue(new Callback<NewsCell>() {
            @Override
            public void onResponse(Call<NewsCell> call, Response<NewsCell> response) {
                articleList=new ArrayList<Article>();
                articleList.addAll(response.body().getArticles());                  //Iz API-a se povlaci samo jedan objekt, a iz njega nam je potrebna samo lista Articla, koju onda predajemo adapteru
                adapter=new RecyclerAdapter(MainActivity.this,articleList,MainActivity.this);
                recycler.setAdapter(adapter);
                hideProgress();                                                     //Skrivamo loader nakon sto dobijemo potrebne podatke iz API-a te ih prikazemo
            }

            @Override
            public void onFailure(Call<NewsCell> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG);
                hideProgress();
                popUp();
            }
        });
    }


    private void initRecycler() {
        recycler=findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,NewsSingle.class);
        Article currentArticle=articleList.get(position);

        detailIntent.putParcelableArrayListExtra("articleList",articleList);
        detailIntent.putExtra("int",position);
        detailIntent.putExtra("title",currentArticle.getTitle());

        startActivity(detailIntent);
    }

    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout=new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout,params);
    }

    public void showProgress() {
        recycler.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    Runnable statusChecker=new Runnable() {
        @Override
        public void run() {
            handler=new Handler();
            try{
                setUpApiCall();                 //Pozivamo funkciju setUpApiCall svakih 5 minuta, kako bi osvjezili vijesti
                showProgress();
            }
            finally {
                handler.postDelayed(statusChecker,300000);
            }
        }
    };

    private void startRepeatingTask() {
        statusChecker.run();
    }

    private void stopRepeatingTask(){
        handler.removeCallbacks(statusChecker);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        if (apiCall != null)
            apiCall.cancel();                   //Otkazujemo request
    }

}
