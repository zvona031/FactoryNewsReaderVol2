package hr.ferit.zvonimirpavlovic.factorynewsreader;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES=10;


    public ScreenSlidePagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return NewsSingleFragment.newInstance(0);
            case 1:
                return NewsSingleFragment.newInstance(1);
            case 2:
                return NewsSingleFragment.newInstance(2);
            case 3:
                return NewsSingleFragment.newInstance(3);
            case 4:
                return NewsSingleFragment.newInstance(4);
            case 5:
                return NewsSingleFragment.newInstance(5);
            case 6:
                return NewsSingleFragment.newInstance(6);
            case 7:
                return NewsSingleFragment.newInstance(7);
            case 8:
                return NewsSingleFragment.newInstance(8);
            case 9:
                return NewsSingleFragment.newInstance(9);
            default:
                return NewsSingleFragment.newInstance(0);
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
