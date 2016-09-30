package com.mllweb.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements BannerView.onItemClickListener {
    BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerView = (BannerView) findViewById(R.id.bannerView);
        bannerView.addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .addBannerPage(new BannerPage(R.mipmap.ic_launcher))
                .setOnItemClickListener(this)
                .show();
    }

    @Override
    public void onItemClick(int position, BannerPage bannerPage) {
    }
}
