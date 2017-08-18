package com.xytsz.xytsz.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.activity.PhotoShowActivity;
import com.xytsz.xytsz.activity.SendRoadDetailActivity;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.R;

import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.ui.SwipeLayoutManager;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.List;


/**
 * Created by admin on 2017/1/11.
 *
 */
public class RoadAdapter extends BaseAdapter {

    private static final int ISPASS = 100001;
    private static final int ISFAIL = 100002;

    private int phaseIndication = 0;
    private Handler handler ;
    private Review.ReviewRoad reviewRoad;
    private List<List<ImageUrl>> imageUrlLists;
    private String isPass;
    private String isFail;
    private List<ImageUrl> urlList;
    private int personID;
    private EditText etAdvice;
    private Button btnOk;


    public RoadAdapter(Handler handler, Review.ReviewRoad reviewRoad, List<List<ImageUrl>> imageUrlLists) {
        this.handler = handler;
        this.reviewRoad = reviewRoad;
        //返回的URl 集合
        this.imageUrlLists = imageUrlLists;
    }


    @Override
    public int getCount() {

        return reviewRoad.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return reviewRoad.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_road, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_swipe_icon);
            holder.tvProblemlocaname = (TextView) convertView.findViewById(R.id.tv_problem_loca);

            holder.tvProblemreporter = (TextView) convertView.findViewById(R.id.tv_problem_reporter);
            holder.tvProblemtime = (TextView) convertView.findViewById(R.id.tv_problem_reportertime);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete);
            holder.tvPass1 = (TextView) convertView.findViewById(R.id.tv_pass1);
            //holder.tvPass2 = (TextView) convertView.findViewById(R.id.tv_pass2);
            holder.detail = (TextView)convertView.findViewById(R.id.tv_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //显示图片 //


        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);


        holder.tvProblemlocaname.setText(reviewRoadDetail.getAddressDescription());
        String upload_person_id = reviewRoadDetail.getUpload_Person_ID()+"";
        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))){
                id = i;
            }
        }

        String userName = personNamelist.get(id);



        String uploadTime = reviewRoadDetail.getUploadTime();


        //获取到当前点击的URL集合
        /**
         * 如果size 不为空
         */
        if (imageUrlLists.size()!= 0) {
            urlList = imageUrlLists.get(position);
            //显示的第一张图片
            ImageUrl imageUrl = urlList.get(0);
            String imgurl = imageUrl.getImgurl();
            Glide.with(parent.getContext()).load(imgurl).fitCenter().into(holder.ivIcon);
            holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(v.getContext(),PhotoShowActivity.class);
                    intent.putExtra("imageUrllist",(Serializable)imageUrlLists.get(position));
                    v.getContext().startActivity(intent);

                }
            });

        }

        holder.tvProblemreporter.setText(userName);
        holder.tvProblemtime.setText(uploadTime);
        holder.tvDelete.setTag(position);
        holder.tvPass1.setTag(position);
        holder.tvPass1.setText("通过");
        //holder.tvPass2.setTag(position);
        holder.detail.setTag(position);
        holder.detail.setOnClickListener(listener);
        holder.tvDelete.setOnClickListener(listener);
        holder.tvPass1.setOnClickListener(listener);
        //holder.tvPass2.setOnClickListener(listener);

        return convertView;
    }

    private  int id;
    static class ViewHolder {
        private TextView tvProblemlocaname;
        private TextView tvProblemreporter;
        private TextView tvProblemtime;
        private ImageView ivIcon;
        private TextView tvDelete;
        private TextView tvPass1;
        private TextView tvPass2;
        private TextView detail;

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            switch (v.getId()) {

                case R.id.tv_detail:
                    int position2 = (int) v.getTag();
                    Intent intent = new Intent(v.getContext(),SendRoadDetailActivity.class);
                    intent.putExtra("detail",reviewRoad.getList().get(position2));
                    intent.putExtra("imageUrls",(Serializable) imageUrlLists.get(position2));
                    v.getContext().startActivity(intent);

                    break;
                case R.id.tv_delete:
                    final int position = (int) v.getTag();
                    phaseIndication = 5;
                    taskNumber = getTaskNumber(position);
                    personID = SpUtils.getInt(v.getContext(), GlobalContanstant.PERSONID);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                    View view = View.inflate(v.getContext(), R.layout.dialog_reject, null);
                    etAdvice = (EditText) view.findViewById(R.id.dialog_et_advise);
                    btnOk = (Button) view.findViewById(R.id.btn_ok);
                    dialog.setView(view);
                    dialog.show();



                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            final String advice = etAdvice.getText().toString();
                            new Thread(){
                                @Override
                                public void run() {

                                    //传驳回内容
                                    try {
                                        isFail = toExamine(taskNumber, phaseIndication,personID,advice);
                                        Message message = Message.obtain();
                                        message.what = ISFAIL;

                                        Bundle bundle = new Bundle();
                                        bundle.putInt("position",position);
                                        bundle.putString("isfail",isFail);
                                        message.setData(bundle);
                                        //message.obj = isFail;
                                        handler.sendMessage(message);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }.start();
                        }
                    });

                    reviewRoad.getList().remove(position);
                    notifyDataSetChanged();
                    //点击的时候关闭这个条目
                    SwipeLayoutManager.getInstance().getSwipeLayout().close(false);
                    //上传当前状态给服务器

                    break;
                //带修改：
                case R.id.tv_pass1:
                    //派给养护一段
                    final int position1 = (int) v.getTag();

                    phaseIndication = 1;
                    taskNumber = getTaskNumber(position1);
                    personID = SpUtils.getInt(v.getContext(), GlobalContanstant.PERSONID);
                    new Thread(){
                        @Override
                        public void run() {
                            try {

                                //TODO:上传标识
                                isPass = toExamine(taskNumber, phaseIndication, personID,"");

                                Message message = Message.obtain();
                                message.what = ISPASS;

                                Bundle bundle = new Bundle();
                                bundle.putInt("position",position1);
                                bundle.putString("isPass",isPass);
                                message.setData(bundle);
                                //message.obj = isPass;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                    reviewRoad.getList().remove(position1);
                    //点击的时候关闭这个条目
                    notifyDataSetChanged();
                    SwipeLayoutManager.getInstance().getSwipeLayout().close(false);
                    break;

                /*case R.id.tv_pass2:
                    int position2 = (int) v.getTag();

                    phaseIndication = 1;
                    taskNumber = getTaskNumber(position2);

                    personID = SpUtils.getInt(v.getContext(), GlobalContanstant.PERSONID);
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                //TODO:上传标识
                                isPass = toExamine(taskNumber, phaseIndication, personID,2);

                                Message message = Message.obtain();
                                message.what = ISPASS;
                                message.obj = isPass;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                    reviewRoad.getList().remove(position2);
                    //点击的时候关闭这个条目
                    notifyDataSetChanged();
                    SwipeLayoutManager.getInstance().getSwipeLayout().close(false);


                    break;*/

            }


        }
    };
    private String taskNumber;

    private String getTaskNumber(int position) {
        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);
        String taskNumber = reviewRoadDetail.getTaskNumber();
        return taskNumber;
    }



    private String toExamine(final String taskNumber, final int phaseIndication, int personID,String advice) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.reviewmethodName);
        soapObject.addProperty("TaskNumber", taskNumber);
        soapObject.addProperty("PhaseIndication", phaseIndication);
        soapObject.addProperty("PersonId",personID);
        //soapObject.addProperty("IsPersonId",IsPersonId);
        soapObject.addProperty("RejectInfo",advice);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.toExamine_SOAP_ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result =  object.getProperty(0).toString();
        return result;

    }


}

