package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/9/4.
 * <p>
 * 软件评价
 */
public class AppraiseActivity extends AppCompatActivity {

    private static final int SUCCESS = 3321;
    private static final int FAIL = 3322;
    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.et_appraise)
    EditText etAppraise;
    @Bind(R.id.appraise_btn)
    Button appraiseBtn;
    @Bind(R.id.tv_ratingbar_result)
    TextView tvRatingbarResult;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    ToastUtil.shortToast(getApplicationContext(), "提交成功");
                    break;
                case FAIL:
                    ToastUtil.shortToast(getApplicationContext(), "请检查网络");
                    break;
            }
        }
    };
    private String appraise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraise);
        ButterKnife.bind(this);
        
        initActionbar();

        initView();

    }

    private void initActionbar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("软件评价");
        }
    }

    private void initView() {
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                switch ((int)rating){
                    case 1:
                        tvRatingbarResult.setText("差");
                        break;
                    case 2:
                        tvRatingbarResult.setText("一般");
                        break;
                    case 3:
                        tvRatingbarResult.setText("好");
                        break;
                    case 4:
                        tvRatingbarResult.setText("很好");
                        break;
                    case 5:
                        tvRatingbarResult.setText("非常好");
                        break;
                }
            }
        });
    }

    @OnClick(R.id.appraise_btn)
    public void onViewClicked() {
        appraise = etAppraise.getText().toString();
        String result = uptoserver();
        if (result != null) {
            if (result.equals("true")) {
                Message message = Message.obtain();
                message.what = SUCCESS;
                handler.sendMessage(message);
            } else {
                Message message = Message.obtain();

                message.what = FAIL;
                handler.sendMessage(message);
            }

        }
        finish();
    }

    private String uptoserver() {

        String string = "true";
        return string;

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
