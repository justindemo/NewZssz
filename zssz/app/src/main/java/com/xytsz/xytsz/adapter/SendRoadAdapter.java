package com.xytsz.xytsz.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.xytsz.xytsz.activity.SendBigPhotoActivity;
import com.xytsz.xytsz.activity.SendRoadDetailActivity;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.PersonList;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.ui.TimeChoiceButton;
import com.xytsz.xytsz.R;

import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/17.
 * xiapai
 */
public class SendRoadAdapter extends BaseAdapter implements View.OnClickListener {


    private static final int ISSEND = 1000002;
    private static final int ISSENDPERSON = 1000003;
    private static final int ISSENDBACK = 1000004;

    private String str;
    private Review.ReviewRoad reviewRoad;
    private List<List<ImageUrl>> imageUrlLists;
    private String[] servicePerson;
    private List<PersonList.PersonListBean> personlist;
    private Handler handler;
    private int requirementsComplete_person_id;
    private String imgurl;
    private EditText etAdvice;


    public SendRoadAdapter(Handler handler, Review.ReviewRoad reviewRoad, List<List<ImageUrl>> imageUrlLists,  List<PersonList.PersonListBean> personlist) {
        this.handler = handler;

        this.reviewRoad = reviewRoad;
        this.imageUrlLists = imageUrlLists;

        //this.servicePerson = servicePerson;
        this.personlist = personlist;

        this.servicePerson = new String[personlist.size()];
        for (int i = 0; i < servicePerson.length; i++) {
            this.servicePerson[i] = personlist.get(i).getName();
        }
    }

    @Override
    public int getCount() {
        //假数据
        //根据id去返回不同的数组

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
    public View getView( final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_send, null);
            holder.Vname = (TextView) convertView.findViewById(R.id.tv_send_Vname);
            holder.Pname = (TextView) convertView.findViewById(R.id.tv_send_Pname);
            holder.date = (TextView) convertView.findViewById(R.id.tv_send_date);
            holder.detail = (RelativeLayout) convertView.findViewById(R.id.rl_send_road_detail);
            holder.sendIcon = (ImageView) convertView.findViewById(R.id.iv_send_photo);
            holder.btSend = (TextView) convertView.findViewById(R.id.bt_send_send);
            holder.btSendBack = (TextView) convertView.findViewById(R.id.bt_send_back);
            holder.btChoice = (TimeChoiceButton) convertView.findViewById(R.id.bt_send_choice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);
        String upload_person_id = reviewRoadDetail.getUpload_Person_ID() + "";
        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))) {
                id = i;
            }
        }

        String userName = personNamelist.get(id);


        String uploadTime = reviewRoadDetail.getUploadTime();
        int level = reviewRoadDetail.getLevel();

        holder.btChoice.setReviewRoadDetail(this, reviewRoadDetail);
        //String userName = SpUtils.getString(parent.getContext(), GlobalContanstant.USERNAME);
        //赋值
        holder.Pname.setText(userName);
        holder.Vname.setText(Data.pbname[level]);
        holder.date.setText(uploadTime);


        if (reviewRoadDetail.getRequestTime() == null) {
            holder.btChoice.setText("要求时间");
        } else {
            holder.btChoice.setText(reviewRoadDetail.getRequestTime());
        }

        //选择派发人
        holder.btSend.setTag(position);
        holder.btSendBack.setTag(position);

        holder.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //点击的时候弹出对话框 都是一样的 人员名字


                //改变bean类的参数
                if (reviewRoadDetail.getRequestTime() == null) {
                    ToastUtil.shortToast(v.getContext(), "请先选择要求时间");

                } else {
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("请选择").setSingleChoiceItems(servicePerson, 0, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("person",servicePerson.toString());
                                    str = servicePerson[which];
                                    reviewRoadDetail.setSendPerson(str);
                                    Message message = Message.obtain();
                                    message.what = ISSENDPERSON;
                                    message.obj = str;
                                    handler.sendMessage(message);


                                    //做判断 求出上报人员的ID
                                    //reviewRoadDetail.setRequirementsComplete_Person_ID();
                                    TextView btn = (TextView) v;
                                    btn.setText(str);
                                    notifyDataSetChanged();
                                    dialog.dismiss();

                                    //上传服务器数据
                                    final int passposition =  (int) btn.getTag();
                                    SendRoadAdapter.this.taskNumber = getTaskNumber(passposition);
                                    getRequstPersonID(passposition, str);
                                    requstPersonID = reviewRoadDetail.getRequirementsComplete_Person_ID();
                                    requstTime = getRequstTime(passposition);
                                    new Thread() {
                                        @Override
                                        public void run() {

                                            try {
                                                String isSend = toDispatching(SendRoadAdapter.this.taskNumber, requstPersonID, requstTime, GlobalContanstant.GETDEAL);

                                                Message message = Message.obtain();
                                                message.what = ISSEND;
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("passposition",passposition);
                                                bundle.putString("issend",isSend);
                                                message.setData(bundle);
                                                //message.obj = isSend;
                                                handler.sendMessage(message);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.start();

                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });

        if (reviewRoadDetail.getSendPerson() == null) {
            holder.btSend.setText("派发");
        } else {
            holder.btSend.setText(reviewRoadDetail.getSendPerson());

        }

        //获取到当前点击的URL集合
        if (imageUrlLists.size() != 0) {
            urlList = imageUrlLists.get(position);
            //显示的第一张图片
            ImageUrl imageUrl = urlList.get(0);
            imgurl = imageUrl.getImgurl();

            Glide.with(parent.getContext()).load(imgurl).into(holder.sendIcon);
            holder.sendIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SendBigPhotoActivity.class);
                    intent.putExtra("imageurl", imageUrlLists.get(position).get(0).getImgurl());
                    v.getContext().startActivity(intent);
                }
            });


        }

        holder.detail.setTag(position);
        holder.detail.setOnClickListener(this);

        //修改
        holder.btSendBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //去除当前条目
                final int failposition = (int) v.getTag();
                final String taskNumber = getTaskNumber(position);

                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                View view = View.inflate(v.getContext(), R.layout.dialog_reject, null);
                etAdvice = (EditText) view.findViewById(R.id.dialog_et_advise);
                final Button btnOk = (Button) view.findViewById(R.id.btn_ok);
                dialog.setView(view);
                dialog.show();

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String advice = etAdvice.getText().toString();
                        backList.add(taskNumber);
                        backList.add(advice);


                        Message message = Message.obtain();
                        message.what = ISSENDTASK;

                        Bundle bundle = new Bundle();
                        bundle.putInt("failposition",failposition);
                        bundle.putString("taskNumber",taskNumber);
                        bundle.putString("advice",advice);
                        message.setData(bundle);

                        //message.obj = backList;
                        handler.sendMessage(message);
                    }
                });


            }
        });



        return convertView;
    }

    private static final int ISSENDTASK = 1000005;
    private List<String> backList = new ArrayList<>();
    private int id;
    private List<ImageUrl> urlList;

    private int getRequstPersonID(int position, String str) {
        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);
        String sendPerson = reviewRoadDetail.getSendPerson();
        /*for (int i = 0; i < Data.servicePerson.length; i++) {
            if (sendPerson.equals(Data.servicePerson[i])) {
                requirementsComplete_person_id = i + 4;

            }
        }

        //做判断 获取到当前选择人对应的ID  发送给服务器

        reviewRoadDetail.setRequirementsComplete_Person_ID(requirementsComplete_person_id);*/

        //str
        for (int i = 0; i < personlist.size(); i++) {


            if (str == personlist.get(i).getName()) {
                requirementsComplete_person_id = personlist.get(i).getId();
                reviewRoadDetail.setRequirementsComplete_Person_ID(requirementsComplete_person_id);
            }

        }


        return requirementsComplete_person_id;
    }

    private String getRequstTime(int position) {
        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);
        String requestTime = reviewRoadDetail.getRequestTime();

        return requestTime;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_send_road_detail:

                int position1 = (int) v.getTag();
                Intent intent = new Intent(v.getContext(), SendRoadDetailActivity.class);
                intent.putExtra("detail", reviewRoad.getList().get(position1));
                intent.putExtra("imageUrls", (Serializable) imageUrlLists.get(position1));
                v.getContext().startActivity(intent);
                break;
        }
    }


    static class ViewHolder {
        public TextView Vname;
        public TextView date;
        public TextView Pname;
        public ImageView sendIcon;
        public TextView btSend;
        public RelativeLayout detail;
        public TextView btSendBack;
        public TimeChoiceButton btChoice;

    }

    private String requstTime;
    private String taskNumber;
    private int requstPersonID;

    private String getTaskNumber(int position) {
        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);
        String taskNumber = reviewRoadDetail.getTaskNumber();
        return taskNumber;
    }


    private String toDispatching(String taskNumber, int requstPersonID, String requestTime, int phaseIndication) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.sendmethodName);
        soapObject.addProperty("TaskNumber", taskNumber);
        soapObject.addProperty("RequirementsComplete_Person_ID", requstPersonID);
        soapObject.addProperty("RequirementsCompleteTime", requestTime);
        soapObject.addProperty("PhaseIndication", phaseIndication);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.toDispatching_SOAP_ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;

    }



}
