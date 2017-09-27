package com.aohanyao.ue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpCard;
    private CircleIndicator indicator;
    private int[] cardColors = {0xff1678c4, 0xffe6424a, 0xffb34dae, 0xffff8a2f};
    private int[] bgColors = {0xff035a9e, 0xffba3139, 0xff8c4284, 0xffff6b21};
    private View llbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        initView();
        initCard();
    }

    private void initCard() {
        final List<Fragment> mFragment = new ArrayList<>();
        mFragment.add(CardFragment.newInstance(cardColors[0]));
        mFragment.add(CardFragment.newInstance(cardColors[1]));
        mFragment.add(CardFragment.newInstance(cardColors[2]));
        mFragment.add(CardFragment.newInstance(cardColors[3]));

        vpCard.setPageMargin(40);
        vpCard.setPageTransformer(true, new ScaleInAlphaTransformer());
        vpCard.setOffscreenPageLimit(mFragment.size() * 2);
        vpCard.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        });

        indicator.setViewPager(vpCard);

        llbg.setBackgroundColor(bgColors[0]);

        vpCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                llbg.setBackgroundColor(bgColors[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        vpCard = (ViewPager) findViewById(R.id.vp_card);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        llbg = findViewById(R.id.ll_bg);
    }

}
