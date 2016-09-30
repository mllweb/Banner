package com.mllweb.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by AndyJeason on 2016/9/29.
 */

public class BannerAdapter extends PagerAdapter {
    private List<ImageView> mBannerImages;

    public BannerAdapter(List<ImageView> imageViews) {
        this.mBannerImages = imageViews;
    }

    @Override
    public int getCount() {
        return mBannerImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mBannerImages.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mBannerImages.get(position));
    }
}
