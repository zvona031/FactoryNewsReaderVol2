package hr.ferit.zvonimirpavlovic.factorynewsreader;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


public class NewsSingle extends AppCompatActivity {


    private ViewPager viewPager;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single);
        initViews();
        setupPager();
        setupToolbar();

        
    }

    private void setupToolbar() {               //Ovdje postavljamo "back" strelicu na actionBar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupPager() {
        PagerAdapter pagerAdapter= new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        Intent i=getIntent();
        int position= i.getIntExtra("int",0);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Intent intent=getIntent();
                ArrayList<Article> articleList=intent.getParcelableArrayListExtra("articleList");
                getSupportActionBar().setTitle(articleList.get(i).getTitle());                          //Postavljamo naslov Fragmenta u ActionBar-u ovisno o tome koja vijest je trenutno u Fragmentu
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void initViews() {
        viewPager=findViewById(R.id.viewPager);
    }
}
