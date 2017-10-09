package com.aohanyao.ue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aohanyao.ue.fragment.CardFragment;
import com.aohanyao.ue.fragment.ContentFragment;
import com.aohanyao.ue.transformer.ScaleInAlphaTransformer;
import com.aohanyao.ue.ui.NoScrollViewPager;
import com.aohanyao.ue.util.ColorUtils;

import java.util.ArrayList;
import java.util.List;
///TODO 1 剩余左右颜色没有做完
///TODO 2 使用自定义View来实现
public class MainActivity extends AppCompatActivity {

    private ViewPager vpCard;
    private NoScrollViewPager vpContent;
    private int[] cardColors = {0xff1678c4, 0xffe6424a, 0xffb34dae, 0xffff8a2f};
    private int[] bgColors = {0xffff6b21, 0xff035a9e, 0xffba3139, 0xff8c4284, 0xffff6b21, 0xff035a9e};
    private View llbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initCard();
        initContent();
    }

    /**
     * 初始化内容
     */
    private void initContent() {
        final List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(ContentFragment.newInstance());
        mFragments.add(ContentFragment.newInstance());
        mFragments.add(ContentFragment.newInstance());
        mFragments.add(ContentFragment.newInstance());
        mFragments.add(ContentFragment.newInstance());
        mFragments.add(ContentFragment.newInstance());

        vpContent.setOffscreenPageLimit(mFragments.size() * 2);
        vpContent.setCurrentItem(1);
        vpContent.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

    }

    /**
     * 初始化顶部的卡片
     */
    private void initCard() {
        //C<->A<->B<->C<->A
        final List<CardFragment> mFragment = new ArrayList<>();
        mFragment.add(CardFragment.newInstance(cardColors[3]));
        mFragment.add(CardFragment.newInstance(cardColors[0]));
        mFragment.add(CardFragment.newInstance(cardColors[1]));
        mFragment.add(CardFragment.newInstance(cardColors[2]));
        mFragment.add(CardFragment.newInstance(cardColors[3]));
        mFragment.add(CardFragment.newInstance(cardColors[0]));

        //左右两个页面之间的间距
        vpCard.setPageMargin(40);
        //设置切换动画
        vpCard.setPageTransformer(true, new ScaleInAlphaTransformer());
        //缓存页大小
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
        //设置当前默认为1
        vpCard.setCurrentItem(1);
        //设置背景
        llbg.setBackgroundColor(bgColors[1]);

        vpCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            float lastPositionOffset = 0L;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //-----------------------------联动 下面的内容
                int width = vpContent.getWidth();
                //滑动内部Viewpager
                vpContent.scrollTo((int) (width * position + width * positionOffset), 0);
                //-----------------------------联动 下面的内容


                if (lastPositionOffset > positionOffset && positionOffset != 0) {
                    //右滑
                    try {
                        int lastReal = vpCard.getAdapter().getCount() - 2;
                        if (position > lastReal) {
                            position = 1;
                        }
                        int bgColor = ColorUtils.evaluate(positionOffset, bgColors[position], bgColors[position + 1]);
                        llbg.setBackgroundColor(bgColor);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (lastPositionOffset < positionOffset && positionOffset != 0) {
                    //左滑
                    try {
                        if (position == 0) {
                            position = vpCard.getAdapter().getCount() - 2;
                        }
                        int bgColor = ColorUtils.evaluate(positionOffset, bgColors[position], bgColors[position + 1]);
                        llbg.setBackgroundColor(bgColor);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                lastPositionOffset = positionOffset;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //无限滑动
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int curr = vpCard.getCurrentItem();
                    int lastReal = vpCard.getAdapter().getCount() - 2;
                    if (curr == 0) {
                        vpContent.setCurrentItem(lastReal, false);
                        vpCard.setCurrentItem(lastReal, false);
                    } else if (curr > lastReal) {
                        vpContent.setCurrentItem(1, false);
                        vpCard.setCurrentItem(1, false);
                    }
                }
            }
        });
    }

    private void initView() {
        vpCard = (ViewPager) findViewById(R.id.vp_card);
        vpContent = (NoScrollViewPager) findViewById(R.id.vp_content);
        llbg = findViewById(R.id.ll_bg);
    }


}
