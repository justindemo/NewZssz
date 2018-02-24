package com.xytsz.xytsz.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.xytsz.xytsz.activity.PhotoShowActivity;
import com.xytsz.xytsz.activity.SendBigPhotoActivity;
import com.xytsz.xytsz.activity.SendRoadDetailActivity;
import com.xytsz.xytsz.bean.AudioUrl;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.PersonList;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.ui.TimeChoiceButton;
import com.xytsz.xytsz.R;

import com.xytsz.xytsz.util.SoundUtil;
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
public class SendRoadAdapter extends BaseAdapter {


    private static final int ISSEND = 1000002;
    private static final int ISSENDPERSON = 1000003;
    private static final int ISSENDBACK = 1000004;

    private String str;
    private Review.ReviewRoad reviewRoad;
    private List<List<ImageUrl>> imageUrlLists;
    private String[] servicePerson;
    private List<PersonList.PersonListBean> personlist;
    private List<AudioUrl> audioUrls;
    private int personid;
    private Handler handler;
    private int requirementsComplete_person_id;
    private String imgurl;
    private EditText etAdvice;
    private SoundUtil soundUtil;
    private String advise;


    public SendRoadAdapter(Handler handler, Review.ReviewRoad reviewRoad,
                           List<List<ImageUrl>> imageUrlLists,
                           List<PersonList.PersonListBean> personlist,
                           List<AudioUrl> audioUrls,int personid) {
        this.handler = handler;

        this.reviewRoad = reviewRoad;
        this.imageUrlLists = imageUrlLists;

        //this.servicePerson = servicePerson;
        this.personlist = personlist;
        this.audioUrls = audioUrls;
        this.personid = personid;

        this.servicePerson = new String[personlist.size()];
        for (int i = 0; i < servicePerson.length; i++) {
            this.servicePerson[i] = personlist.get(i).getName();
        }

        soundUtil = new SoundUtil();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_send, null);
            holder.Vname = (TextView) convertView.findViewById(R.id.tv_sendroad_Vname);
            holder.Pname = (TextView) convertView.findViewById(R.id.tv_send_Pname);
            holder.cb = (CheckBox) convertView.findViewById(R.id.sendroad_checkbox);
            holder.tvProblemAudio = (TextView) convertView.findViewById(R.id.tv_send_audio);
            holder.date = (TextView) convertView.findViewById(R.id.tv_send_date);
            holder.bottom = (LinearLayout) convertView.findViewById(R.id.ll_sendroad_bottom);
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
        holder.Vname.setText(userName);
        holder.Pname.setText(reviewRoadDetail.getAddressDescription());
        holder.date.setText(uploadTime);


        //是否显示底部。
        if (reviewRoadDetail.isShow()) {
            holder.bottom.setVisibility(View.GONE);
            holder.cb.setVisibility(View.VISIBLE);
        } else {
            holder.bottom.setVisibility(View.VISIBLE);
            holder.cb.setVisibility(View.GONE);
        }

        holder.cb.setChecked(reviewRoadDetail.isCheck());


        //点击事件
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reviewRoadDetail.isMultiSelect()) {
                    if (holder.cb.isChecked()) {
                        holder.cb.setChecked(true);
                        reviewRoadDetail.setCheck(true);
                        reviewRoadDetail.setPosition(position);
                    } else {
                        holder.cb.setChecked(false);
                        reviewRoadDetail.setCheck(false);
                    }
                }
            }
        });

        holder.btChoice.setClickable(true);
        holder.btChoice.setFocusable(true);

        if (reviewRoadDetail.getRequestTime() == null) {
            holder.btChoice.setText("要求时间");
        } else {
            holder.btChoice.setText(reviewRoadDetail.getRequestTime());
        }


        // 根据position设置CheckBox是否可见，是否选中

        // ListView每一个Item的长按事件
        holder.detail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.OnLongclick(v, position);
                return true;
            }
        });


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
                                    str = servicePerson[which];
                                    dialog.dismiss();

                                    new AlertDialog.Builder(v.getContext()).setTitle("维修人").
                                            setMessage("确定让：" + str + " 维修?").setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


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


                                                    //上传服务器数据
                                                    final int passposition = (int) btn.getTag();
                                                    SendRoadAdapter.this.taskNumber = getTaskNumber(passposition);
                                                    getRequstPersonID(passposition, str);
                                                    requstPersonID = reviewRoadDetail.getRequirementsComplete_Person_ID();
                                                    requstTime = getRequstTime(passposition);
                                                    new Thread() {
                                                        @Override
                                                        public void run() {

                                                            try {
                                                                String isSend = toDispatching(SendRoadAdapter.this.taskNumber,
                                                                        requstPersonID, requstTime, GlobalContanstant.GETDEAL,personid);

                                                                Message message = Message.obtain();
                                                                message.what = ISSEND;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt("passposition", passposition);
                                                                bundle.putString("issend", isSend);
                                                                message.setData(bundle);
                                                                //message.obj = isSend;
                                                                handler.sendMessage(message);
                                                            } catch (Exception e) {

                                                            }
                                                        }
                                                    }.start();

                                                    reviewRoad.getList().remove(position);
                                                    imageUrlLists.remove(position);
                                                    audioUrls.remove(position);
                                                    notifyDataSetChanged();
                                                    dialog.dismiss();
                                                }
                                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();

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
            if (urlList.size() != 0) {
                ImageUrl imageUrl = urlList.get(0);
                imgurl = imageUrl.getImgurl();

                Glide.with(parent.getContext()).load(imgurl).into(holder.sendIcon);
                holder.sendIcon.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PhotoShowActivity.class);
                        intent.putExtra("imageUrllist", (Serializable) imageUrlLists.get(position));
                        v.getContext().startActivity(intent);
                    }
                });
            } else {
                Glide.with(parent.getContext()).load(R.mipmap.prepost).fitCenter().into(holder.sendIcon);
            }

        }


        //判断是否有语音
        if (reviewRoadDetail.getAddressDescription().isEmpty()) {
            final AudioUrl audioUrl = audioUrls.get(position);
            if (audioUrl != null) {
                if (audioUrl.getAudioUrl() != null) {
                    if (!audioUrl.getAudioUrl().equals("false")) {
                        if (!audioUrl.getTime().isEmpty()) {
                            holder.Pname.setVisibility(View.GONE);
                            holder.tvProblemAudio.setVisibility(View.VISIBLE);

                            holder.tvProblemAudio.setText(audioUrl.getTime());

                            holder.tvProblemAudio.setOnClickListener(new View.OnClickListener() {


                                @Override
                                public void onClick(View v) {

                                    Drawable drawable = parent.getContext().getResources().getDrawable(R.mipmap.pause);
                                    final Drawable drawableRight = parent.getContext().getResources().getDrawable(R.mipmap.play);
                                    final TextView tv = (TextView) v;
                                    tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                                    soundUtil.setOnFinishListener(new SoundUtil.OnFinishListener() {
                                        @Override
                                        public void onFinish() {
                                            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);

                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });

                                    soundUtil.play(audioUrl.getAudioUrl());
                                }
                            });
                        }
                    } else {
                        holder.Pname.setVisibility(View.VISIBLE);
                        holder.tvProblemAudio.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.Pname.setVisibility(View.VISIBLE);
                holder.tvProblemAudio.setVisibility(View.GONE);
            }

        } else {
            holder.Pname.setVisibility(View.VISIBLE);
            holder.tvProblemAudio.setVisibility(View.GONE);
        }

        holder.detail.setTag(position);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position1 = (int) v.getTag();
                // 处于多选模式
                if (reviewRoadDetail.isMultiSelect()) {
                    if (holder.cb.isChecked()) {
                        holder.cb.setChecked(false);
                        reviewRoadDetail.setCheck(false);
                    } else {
                        holder.cb.setChecked(true);
                        reviewRoadDetail.setCheck(true);
                        reviewRoadDetail.setPosition(position1);
                    }
                    notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(v.getContext(), SendRoadDetailActivity.class);
                    intent.putExtra("detail", reviewRoad.getList().get(position1));
                    intent.putExtra("audioUrl", audioUrls.get(position1));
                    intent.putExtra("imageUrls", (Serializable) imageUrlLists.get(position1));
                    v.getContext().startActivity(intent);
                }
            }
        });

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
                Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.back_rg);

                radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.noblong_me_rb:
                                advise = "非管护段";
                                break;
                            case R.id.noblong_rb:
                                advise = "非权属";
                                break;
                        }
                    }
                });


                dialog.setView(view);
                dialog.show();

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String advice = advise + " " + etAdvice.getText().toString();
                        backList.add(taskNumber);
                        backList.add(advice);


                        Message message = Message.obtain();
                        message.what = ISSENDTASK;

                        Bundle bundle = new Bundle();
                        bundle.putInt("failposition", failposition);
                        bundle.putString("taskNumber", taskNumber);
                        bundle.putString("advice", advice);
                        message.setData(bundle);

                        //message.obj = backList;
                        handler.sendMessage(message);

                        reviewRoad.getList().remove(failposition);
                        imageUrlLists.remove(failposition);
                        audioUrls.remove(failposition);
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                notifyDataSetChanged();


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
        if (personlist != null && personlist.size() != 0) {


            for (int i = 0; i < personlist.size(); i++) {

                if (str == personlist.get(i).getName()) {
                    requirementsComplete_person_id = personlist.get(i).getId();
                    reviewRoadDetail.setRequirementsComplete_Person_ID(requirementsComplete_person_id);
                }

            }
        }

        return requirementsComplete_person_id;
    }

    private String getRequstTime(int position) {
        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = reviewRoad.getList().get(position);
        String requestTime = reviewRoadDetail.getRequestTime();

        return requestTime;
    }


    static class ViewHolder {
        public TextView Vname;
        public TextView date;
        public TextView Pname;
        public CheckBox cb;
        public LinearLayout bottom;
        public TextView tvProblemAudio;
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


    public String toDispatching(String taskNumber, int requstPersonID, String requestTime, int phaseIndication,int personid) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.sendmethodName);
        soapObject.addProperty("TaskNumber", taskNumber);
        soapObject.addProperty("RequirementsComplete_Person_ID", requstPersonID);
        soapObject.addProperty("RequirementsCompleteTime", requestTime);
        soapObject.addProperty("PhaseIndication", phaseIndication);
        soapObject.addProperty("Issued_Person_ID", personid);

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


    public interface OnItemLongClickListeners {
        void OnLongclick(View v, int position);
    }

    private OnItemLongClickListeners onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListeners onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


}
