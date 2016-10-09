package com.stone.transition;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.Random;

import tyrantgit.widget.HeartLayout;

/**
 * Created by xmuSistone on 2016/9/19.
 */
public class DetailActivity extends FragmentActivity {

    public static final String EXTRA_IMAGE_URL = "detailImageUrl";

    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String ADDRESS1_TRANSITION_NAME = "address1";
    public static final String ADDRESS2_TRANSITION_NAME = "address2";
    public static final String ADDRESS3_TRANSITION_NAME = "address3";
    public static final String ADDRESS4_TRANSITION_NAME = "address4";
    public static final String ADDRESS5_TRANSITION_NAME = "address5";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";

    private View address1, address2, address3, address4, address5;
    private ImageView imageView;
    private RatingBar ratingBar;

    private LinearLayout listContainer;
    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    private static final int[] imageIds = {R.drawable.head1, R.drawable.head2, R.drawable.head3, R.drawable.head4};

    private Random mRandom = new Random();

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = (ImageView) findViewById(R.id.image);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        address3 = findViewById(R.id.address3);
        address4 = findViewById(R.id.address4);
        address5 = findViewById(R.id.address5);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        listContainer = (LinearLayout) findViewById(R.id.detail_list_container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address1, ADDRESS1_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(address3, ADDRESS3_TRANSITION_NAME);
        ViewCompat.setTransitionName(address4, ADDRESS4_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);

        dealListView();
    }

    /**
     * 初始化样式
     *
     * @return
     */
    private Configuration initNiftyNotificationView() {
        Configuration cfg = new Configuration.Builder()
                .setAnimDuration(700)
                .setDispalyDuration(1500)
                .setBackgroundColor("#FFBDC3C7")
                .setTextColor("#FF444444")
                .setIconBackgroundColor("#FFFFFFFF")
                .setTextPadding(5)                      //dp
                .setViewHeight(48)                      //dp
                .setTextLines(2)                        //You had better use setViewHeight and setTextLines together
                .setTextGravity(Gravity.CENTER)         //only text def  Gravity.CENTER,contain icon Gravity.CENTER_VERTICAL
                .build();
        return cfg;

    }

    /**
     * 显示通知
     *
     * @param v
     */
    public void showNotify(int v) {
        String msg = "Today we’d like to share a couple of simple styles and effects for android notifications.";
        Effects effect = null;
        switch (v) {
            case 0:
                effect = Effects.scale;
                break;
            case 1:
                effect = Effects.thumbSlider;
                break;
            case 2:
                effect = Effects.jelly;
                break;
            case 3:
                effect = Effects.slideIn;
                break;
            case 4:
                effect = Effects.flip;
                break;
            case 5:
                effect = Effects.slideOnTop;
                break;
            case 6:
                effect = Effects.standard;
                break;
            default:
                return;
        }
        NiftyNotificationView.build(this, msg, effect, R.id.mLyout, initNiftyNotificationView())
                .setIcon(R.drawable.head1)         //You must call this method if you use ThumbSlider effect
                .show();
    }

    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final LoadToast lt = new LoadToast(this);
        lt.setTranslationY(100);//向下位移
        lt.setText("恭喜您，点赞成功!");
        for (int i = 0; i < 20; i++) {
            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
            listContainer.addView(childView);
            ImageView headView = (ImageView) childView.findViewById(R.id.head);
            final HeartLayout mHeartLayout = (HeartLayout) childView.findViewById(R.id.heart_layout);
            mHeartLayout.setTag(i);
            mHeartLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNotify((int) v.getTag());
                    lt.show();
                    mHeartLayout.addHeart(randomColor());
                    mHeartLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mHeartLayout.addHeart(randomColor());
                            lt.success();
                        }
                    }, 3000);

                }
            });
            if (i < headStrs.length) {
                headView.setImageResource(imageIds[i % imageIds.length]);
                ViewCompat.setTransitionName(headView, headStrs[i]);
            }
        }
    }
}
