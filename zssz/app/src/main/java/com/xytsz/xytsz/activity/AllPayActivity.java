package com.xytsz.xytsz.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.PayResult;
import com.xytsz.xytsz.util.OrderInfoUtil2_0;

import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/8/18.
 * <p/>
 * 支付宝微信支付
 */
public class AllPayActivity extends AppCompatActivity {

    @Bind(R.id.product_subject)
    TextView productSubject;
    @Bind(R.id.product_message)
    TextView productMessage;
    @Bind(R.id.product_price)
    TextView productPrice;
    @Bind(R.id.alipay)
    Button alipay;
    @Bind(R.id.wxpay)
    Button wxpay;

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";


    /**
     * 只需要填一个
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(AllPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.putExtra("result",true);
                        setResult(1,intent);
                        finish();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AllPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.putExtra("result",false);
                        setResult(1,intent);
                        finish();


                    }
                    break;
                }


                default:
                    break;
            }
        };
    };
    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpay);
        ButterKnife.bind(this);

        initview();

    }

    private void initview() {
        productSubject.setText("掌上市政会员");
        productMessage.setText("北京市市政协会信息");
        productPrice.setText("¥"+" 200");
    }

    @OnClick({R.id.alipay, R.id.wxpay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay:
                alipay();
                break;
            case R.id.wxpay:
                wxpay();
                break;
        }
    }

    //微信支付
    private void wxpay() {
        api = WXAPIFactory.createWXAPI(this, "wxb4ba3c02aa476ea1");

        //从服务器返回
        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";

        wxpay.setEnabled(false);
        Toast.makeText(AllPayActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();
        try{
//            byte[] buf = Util.httpGet(url);
//            if (buf != null && buf.length > 0) {
//                String content = new String(buf);
                String content= "json";
            if (content == null){
                Log.e("get server pay params:",content);
                JSONObject json = new JSONObject(content);
                if(null != json && !json.has("retcode") ){
                    PayReq req = new PayReq();
                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                    req.appId			= json.getString("appid");
                    req.partnerId		= json.getString("partnerid");
                    req.prepayId		= json.getString("prepayid");
                    req.nonceStr		= json.getString("noncestr");
                    req.timeStamp		= json.getString("timestamp");
                    req.packageValue	= json.getString("package");
                    req.sign			= json.getString("sign");
                    req.extData			= "app data"; // optional
                    Toast.makeText(AllPayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    api.sendReq(req);
                }else{
                    Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
                    Toast.makeText(AllPayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                }
            }else{
            Log.d("PAY_GET", "服务器请求错误");
            Toast.makeText(AllPayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
        }
        }catch(Exception e){
            Log.e("PAY_GET", "异常："+e.getMessage());
            Toast.makeText(AllPayActivity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        wxpay.setEnabled(true);

    }




    //支付宝支付
    private void alipay() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(AllPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }
}
