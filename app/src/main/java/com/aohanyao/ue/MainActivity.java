package com.aohanyao.ue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpCard;
    private int[] cardColors = {0xff1678c4, 0xffe6424a, 0xffb34dae, 0xffff8a2f};
    private int[] bgColors = {0xffff6b21, 0xff035a9e, 0xffba3139, 0xff8c4284, 0xffff6b21, 0xff035a9e};
    private View llbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        initView();
        initCard();
    }

    private void initCard() {
        //C<->A<->B<->C<->A
        final List<Fragment> mFragment = new ArrayList<>();
        mFragment.add(CardFragment.newInstance(cardColors[3]));
        mFragment.add(CardFragment.newInstance(cardColors[0]));
        mFragment.add(CardFragment.newInstance(cardColors[1]));
        mFragment.add(CardFragment.newInstance(cardColors[2]));
        mFragment.add(CardFragment.newInstance(cardColors[3]));
        mFragment.add(CardFragment.newInstance(cardColors[0]));

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
        vpCard.setCurrentItem(1);

        llbg.setBackgroundColor(bgColors[1]);

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
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int curr = vpCard.getCurrentItem();
                    int lastReal = vpCard.getAdapter().getCount() - 2;
                    if (curr == 0) {
                        vpCard.setCurrentItem(lastReal, false);
                    } else if (curr > lastReal) {
                        vpCard.setCurrentItem(1, false);
                    }
                }
            }
        });
    }

    private void initView() {
        vpCard = (ViewPager) findViewById(R.id.vp_card);
        llbg = findViewById(R.id.ll_bg);
    }

}
