package com.mllweb.banner;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndyJeason on 2016/9/29.
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //点击回调
    private onItemClickListener mItemClickListener;
    //指示器
    private int mIndicatorSelect = R.drawable.banner_select;
    private int mIndicator = R.drawable.banner_select_no;
    private List<View> mIndicatorViews = new ArrayList<>();
    //适配器
    private ViewPager mBannerPager;
    private BannerAdapter mBannerAdapter;
    private List<ImageView> mBannerImages;
    private List<BannerPage> mBannerPages = new ArrayList<>();
    //轮播
    private boolean isLoop = true;
    private int mLoopTime = 5000;
    private Handler mLoopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = mBannerPager.getCurrentItem();
            mBannerPager.setCurrentItem(currentItem + 1);
        }
    };
    private Thread mLoopThread = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(mLoopTime);
                    mLoopHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBannerPager = new ViewPager(getContext());
    }

    public BannerView setOnItemClickListener(onItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
        return this;
    }

    /**
     * 是否开启轮播
     */
    public BannerView isLoop(boolean isLoop) {
        this.isLoop = isLoop;
        return this;
    }

    /**
     * 设置轮播时间
     */
    public BannerView setLoopTime(int loopTime) {
        mLoopTime = loopTime;
        return this;
    }

    /**
     * 设置页码指示器
     */
    public BannerView setIndicator(int selectRes, int normalRes) {
        mIndicatorSelect = selectRes;
        mIndicator = normalRes;
        return this;
    }

    /**
     * 设置轮播内容
     */
    public BannerView addBannerPage(BannerPage bannerPage) {
        mBannerPages.add(bannerPage);
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        int bannerPageSize;
        if (mBannerPages != null && (bannerPageSize = mBannerPages.size()) > 0) {
            mBannerPager = createBannerPager(bannerPageSize);
            mBannerImages = createBannerView(mBannerPages);
            mBannerAdapter = new BannerAdapter(mBannerImages);
            mBannerPager.setAdapter(mBannerAdapter);
            mBannerPager.setCurrentItem(1);
            addView(mBannerPager);
            createBottomView();
            if (isLoop) startLoop();
        }
    }

    /**
     * 创建轮播
     */
    private ViewPager createBannerPager(int bannerPageSize) {
        ViewPager viewPager = new ViewPager(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setOffscreenPageLimit(bannerPageSize + 2);
        viewPager.addOnPageChangeListener(this);
        return viewPager;
    }

    /**
     * 创建轮播视图集合
     */
    private List<ImageView> createBannerView(List<BannerPage> bannerPages) {
        int bannerPageSize = bannerPages.size();
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(createDraweeView(bannerPages.get(bannerPageSize - 1), bannerPageSize - 1));
        for (int i = 0; i < bannerPageSize; i++) {
            imageViews.add(createDraweeView(bannerPages.get(i), i));
        }
        imageViews.add(createDraweeView(bannerPages.get(0), 0));
        return imageViews;
    }

    /**
     * 创建主显图片
     */
    private SimpleDraweeView createDraweeView(BannerPage bannerPage, int tag) {
        SimpleDraweeView draweeView = new SimpleDraweeView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        draweeView.setLayoutParams(layoutParams);
        if (bannerPage.getUriRes() != 0) {
            String uriString = String.format("res://%s/%d", getContext().getPackageName(), bannerPage.getUriRes());
            Uri uri = Uri.parse(uriString);
            draweeView.setImageURI(uri);
        } else if (bannerPage.getUriString() != null) {
            draweeView.setImageURI(bannerPage.getUriString());
        }
        draweeView.setTag(tag);
        draweeView.setOnClickListener(this);
        return draweeView;
    }

    /**
     * 创建底部布局
     */
    private View createBottomView() {
        LinearLayout indicatorLayout = new LinearLayout(getContext());
        int bottomHeight = getWindowsMetrics().heightPixels / 20;
        LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, bottomHeight);
        indicatorLayout.setLayoutParams(indicatorParams);
        indicatorLayout.setHorizontalGravity(LinearLayout.VERTICAL);
        for (int i = 0; i < mBannerPages.size(); i++) {
            View indicator = new View(getContext());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, getWindowsMetrics());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.setMargins(width / 2, 0, width / 2, 0);
            indicator.setLayoutParams(params);
            if (i == 0) {
                indicator.setBackgroundResource(mIndicatorSelect);
            } else {
                indicator.setBackgroundResource(mIndicator);
            }
            indicatorLayout.addView(indicator);
            mIndicatorViews.add(indicator);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bottomHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(indicatorLayout, layoutParams);
        return indicatorLayout;
    }

    private DisplayMetrics getWindowsMetrics() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 轮播首尾切换
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset == 0) {
            if (position < 1) { //首位之前，跳转到末尾（N）
                mBannerPager.setCurrentItem(mBannerImages.size() - 2, false);
            } else if (position > mBannerImages.size() - 2) { //末位之后，跳转到首位（1）
                mBannerPager.setCurrentItem(1, false); //false:不显示跳转过程的动画
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mIndicatorViews.size(); i++) {
            if (i == position - 1) {
                mIndicatorViews.get(i).setBackgroundResource(mIndicatorSelect);
            } else {
                mIndicatorViews.get(i).setBackgroundResource(mIndicator);
            }
        }
    }

    /**
     * 滑动状态停止轮播
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (isLoop) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                mLoopThread.interrupt();
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                startLoop();
            }
        }
    }

    /**
     * 开始轮播
     */
    private void startLoop() {
        if (!mLoopThread.isAlive()) mLoopThread.start();
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            SimpleDraweeView draweeView = (SimpleDraweeView) view;
            int position = (int) draweeView.getTag();
            mItemClickListener.onItemClick(position, mBannerPages.get(position));
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position, BannerPage bannerPage);
    }
}
