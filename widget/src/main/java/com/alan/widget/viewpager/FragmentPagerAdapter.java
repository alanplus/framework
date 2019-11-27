package com.alan.widget.viewpager;

import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;


public class FragmentPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private T[] mFragmentList;

    public FragmentPagerAdapter(FragmentManager fm, T[] mFragments) {
        super(fm);
        mFragmentList = mFragments;
    }
    
    @Override
    public Parcelable saveState()
    {
        return null;
    }

    @Override
    public T getItem(int position) {
        return mFragmentList[position % getCount()];
    }

    @Override
    public int getCount() {
        return mFragmentList.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}