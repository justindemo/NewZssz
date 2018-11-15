package com.xytsz.xytsz.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.util.PermissionUtils;
import com.xytsz.xytsz.util.ToastUtil;



import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/20.
 * <p>
 * 相机
 */
public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.AutoFocusCallback {

    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;
    @Bind(R.id.btn_take_photo)
    Button btnTakePhoto;
    @Bind(R.id.title_btn_ok)
    TextView titleBtnOk;
    @Bind(R.id.btn_back)
    ImageView btnBack;
    @Bind(R.id.iv_album)
    ImageView ivAlbum;
    @Bind(R.id.focus_index)
    View focusIndex;
    private Display display;
    private SurfaceHolder holder;
    private Camera camera;
    private boolean isPreview;
    private int screenheight;
    private int screenwidth;
    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zssz/personreport/";
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();
    private int[] startLocation;
    private int[] endLocation;
    private List<String> picpaths = new ArrayList<>();
    private String photoresult;
    private String photorequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);
        screenwidth = metrics.widthPixels;
        screenheight = metrics.heightPixels;


        holder = surfaceView.getHolder();

        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        picpaths.clear();

    }


    @OnClick({R.id.btn_take_photo, R.id.title_btn_ok, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo:
                //点击拍照
                //camera.autoFocus(CameraActivity.this);
                //PermissionUtils.requestPermission(CameraActivity.this,PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,mPermissionGrant);

                camera.takePicture(null,null,CameraActivity.this);
                break;

            case R.id.title_btn_ok:
                if (num != 3) {
                    String tip = getString(R.string.camera_tip);
                    ToastUtil.shortToast(getApplicationContext(), tip);
                } else {
                    //IntentUtil.startActivity(view.getContext(), PersonReportActivity.class);

                    Intent intent = new Intent(CameraActivity.this,PersonReportActivity.class);
                    intent.putExtra("picpaths",(Serializable) picpaths);
                    CameraActivity.this.startActivity(intent);
                    finish();
                }
                break;
            case R.id.btn_back:
                CameraActivity.this.finish();
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();// 停掉原来摄像头的预览
            camera.release();// 释放资源
            camera = null;// 取消原来摄像头
        }else {
            try {
                camera = Camera.open(0);
                camera.setPreviewDisplay(holder);
            } catch (Exception e) {
                ToastUtil.shortToast(getApplicationContext(), "打开摄像头失败");

            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setCameraParamsSize(camera, screenwidth, screenheight);
        camera.startPreview();

    }

    private String TAG = "CameraActivity";

    private void setCameraParamsSize(Camera camera, int width, int height) {

        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        //parameters.setPreviewFrameRate(5);
        //获取色相头支持的图片大小列表
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        int showLength = previewSizes.size();

        int maxSize = Math.max(width, height);
        int length = supportedPictureSizes.size();

        Camera.Size preSize = getPropPreviewSize(previewSizes, width, height);
        Camera.Size picSize = getPropPictureSize(supportedPictureSizes, preSize.width, preSize.height);
        parameters.setPictureSize(picSize.width, picSize.height);
        parameters.setPreviewSize(preSize.width, preSize.height);


        setparamer(camera, parameters);
    }

    public Camera.Size getPropPreviewSize(List<Camera.Size> list, int minWidth, int minHeight) {
        Collections.sort(list, sizeComparator);
        Log.i(TAG, "PreviewSize : minWidth = " + minWidth);
        int i = 0;
        for (Camera.Size s : list) {
            Log.i(TAG, "PreviewSize : width = " + s.width + "height = " + s.height);
            if ((s.height == minWidth) && s.width >= minHeight) {
                Log.i(TAG, "PreviewSize : w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = list.size() - 1;//如果没找到，就选最大的size
        }
        return list.get(i);
    }

    public Camera.Size getPropPictureSize(List<Camera.Size> list, int minWidth, int minHeight) {
        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Camera.Size s : list) {
            Log.i(TAG, "PreviewSize : width = " + s.width + "height = " + s.height);
            if (s.height == minHeight && s.width == minWidth) {
                Log.i(TAG, "PreviewSize : w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = list.size() - 1;//如果没找到，就选最大的size
        }
        return list.get(i);
    }


    public class CameraSizeComparator implements Comparator<Camera.Size> {
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            //
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    private void setparamer(Camera camera, Camera.Parameters parameters) {
        parameters.setJpegQuality(100);
        //连续对焦
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {

            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        camera.setDisplayOrientation(90);

        camera.setParameters(parameters);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            if (isPreview) {
                camera.stopPreview();
            }
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            if (isPreview) {
                camera.stopPreview();
                isPreview = false;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera != null) {
            if (!isPreview) {
                camera.startPreview();
                // 自动对焦
                //camera.cancelAutoFocus();
                isPreview = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            if(surfaceView != null && surfaceView.getHolder() != null ){
                surfaceView.getHolder().removeCallback(this);
            }
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.lock();
            camera.release();
            camera = null;
        }
    }

    private int num;


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        //ToastUtil.shortToast(getApplicationContext(),"正在拍照");
        if (num < 3) {
            FileOutputStream bos = null;
            Bitmap bm = null;
            String picPath;
            try {
                // 获得图片
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);

                bm =rotateBitmap(bm,90f);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    //照片保存路径
                    picPath = filePath + "test" + num + ".jpg";
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    //压缩图片
                    //compressImage(bm, num);

                    bos = new FileOutputStream(picPath);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
                    //压缩比例
                    getBitmap(screenwidth, screenheight, picPath, num);


                    picpaths.add(picPath);
                    num++;
                    String play = getString(R.string.camera_play);
                    String played = getString(R.string.camera_played);
                    String plese = getString(R.string.camera_request_report);
                    photorequest = getString(R.string.camera_photorequest);
                    photoresult = getString(R.string.camera_photoresult);
                    ToastUtil.shortToast(getApplicationContext(), play + num + played);
                    playAnimator(bm,num);

                    if (num == 3) {
                        ToastUtil.shortToast(getApplicationContext(), plese);
                        return;
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "没有检测到内存卡", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            } finally {
                try {
                    bos.flush();//输出
                    bos.close();//关闭
                    camera.stopPreview();// 关闭预览
                    //执行动画
                    camera.startPreview();// 开启预览
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {

            ToastUtil.shortToast(getApplicationContext(), photoresult);
        }
    }

    private List<String> imageUrllist = new ArrayList<>();

    private void playAnimator(Bitmap bitmap,int num) {
        //从屏幕中间 到左下角 的一个抛物线
        //开始地方
        // 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        startLocation = new int[2];
        focusIndex.getLocationOnScreen(startLocation);

        // 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        endLocation = new int[2];
        ivAlbum.getLocationOnScreen(endLocation);

        ImageView ball = new ImageView(getApplicationContext());
        Bitmap zoomBitmap = zoomBitmap(bitmap);
        ball.setImageBitmap(zoomBitmap);


        setAnim(ball);
        //Bitmap rotateBitmap = rotateBitmap(bitmap, 90f);
        ivAlbum.setImageBitmap(bitmap);

        if (num <3){
            ToastUtil.shortToast(getApplicationContext(),photorequest);
        }else {
            ivAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String picPath ;
                    imageUrllist.clear();
                    for (int i = 0; i < 3; i++) {
                        picPath = filePath + i + ".jpg";
                        imageUrllist.add(picPath);
                    }

                    Intent intent = new Intent(v.getContext(),ReportPhotoShowActivity.class);
                    intent.putExtra("imageUrllist", (Serializable) imageUrllist);
                    startActivity(intent);
                }
            });
        }
    }

    private Bitmap zoomBitmap(Bitmap bm) {
        int height = bm.getHeight();
        int width = bm.getWidth();
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) 80) / width;
        float scaleHeight = ((float) 80) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    private Bitmap rotateBitmap(Bitmap bm,float orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {

            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
        }

        return null;
    }


    private void setAnim(final View view) {

        ViewGroup viewGroup =createAnimLayout();
        viewGroup.addView(view);

        //获取最终点的坐标

        endLocation = new int[]{endLocation[0],screenheight-endLocation[1]};

        int endX = -endLocation[0];              // 动画位移的X坐标
        int endY = -endLocation[1];// 动画位移的y坐标
//        System.out.println("=====x==="+endX);
//        System.out.println("=====y==="+endY);
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, screenheight);
        translateAnimationX.setInterpolator(new LinearInterpolator()); //让动画已均匀的速度改变
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true); //执行完毕，利用视图setLayoutParams来重新定位

        TranslateAnimation translateAnimationY = new TranslateAnimation(screenwidth/2, 0,
                screenheight/2, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup)getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(CameraActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }


    private Bitmap getBitmap(int width, int height, String path, int num) {
        Bitmap bitmap;
        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, factoryOptions);

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(imageWidth / width, imageHeight
                / height);

        // Decode the image file into a Bitmap sized to fill the
        // View
        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;
        factoryOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(path,
                factoryOptions);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        compressImage(bitmap, num);
        return bitmap;
    }

    //压缩图片
    private Bitmap compressImage(Bitmap bitmap, int num) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                options -= 10;//每次都减少10
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

            }

            String photopath;
            photopath = filePath + num + ".jpg";
            //压缩好后写入文件中
            FileOutputStream fos = new FileOutputStream(photopath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            PermissionUtils.requestPermission(CameraActivity.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
        }
    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    try {
                        camera.takePicture(null, null, null, CameraActivity.this);
                    } catch (Exception e) {

                    }
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(CameraActivity.this, requestCode, permissions, grantResults, mPermissionGrant);
    }
}
