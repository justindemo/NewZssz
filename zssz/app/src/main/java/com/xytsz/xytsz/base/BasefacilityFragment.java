package com.xytsz.xytsz.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.ui.StateLayout;

import java.util.Collection;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/11.
 *
 */
public abstract class BasefacilityFragment extends Fragment {

    private Context context;
    private StateLayout stateLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();

        // 填充出来的状态布局已经拥有了3种状态：正在加载、加载失败、加载为空
        stateLayout = (StateLayout) inflater.inflate(R.layout.state_layout, null);

        // 因为这里是Base类，Base类不知道子类要设置的正常界面是什么，所以这里把这个正常界面写了抽象方法
        stateLayout.setContentView(getContentView());


        initView();
        initListener();


//		initData();	// 为了预防7个界面的加载网络的方法在界面第一次选择的时候再执行，这个方法在ViewPager的监听器中去调用
        return stateLayout;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 检查数据是否OK，如果数据OK则返回true。这个方法内部会根据数据决定要显示哪种状态的View（failView、emptyView、contentView)
     * @param datas
     * @return
     */
    public boolean checkDatas(Collection<?> datas) {
        boolean result = false;
        if (datas == null) {
            stateLayout.showFailView();
        } else {
            stateLayout.showContentView();	// 显示“正常界面”的View
            result = true;
        }
        return result;
    }

    public <T>T findView(int viewId){
        @SuppressWarnings("unchecked")
        T viewById = (T) stateLayout.findViewById(viewId);
        return viewById;
    }

    /**
     * 创建布局
     */
    protected abstract Object getContentView();
    protected abstract void initView();
    protected abstract void initListener();
    protected abstract void initData();
}
