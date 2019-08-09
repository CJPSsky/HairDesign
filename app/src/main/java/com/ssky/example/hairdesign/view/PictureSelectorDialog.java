package com.ssky.example.hairdesign.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kevin.dialog.BaseDialog;
import com.ssky.example.hairdesign.R;


public class PictureSelectorDialog extends BaseDialog implements View.OnClickListener {

    private static final String TAG = "SelectPictureDialog";
    private Button takePhotoBtn, pickPictureBtn, cancelBtn;

    private OnSelectedListener mOnSelectedListener;


    public static PictureSelectorDialog getInstance() {
        PictureSelectorDialog dialog = new PictureSelectorDialog();
        // 设置屏蔽返回键
        dialog.setCanceledBack(false)
                // 设置屏蔽对话框点击外部关闭
                .setCanceledOnTouchOutside(false)
                // 设置对话框在底部
                .setGravity(Gravity.BOTTOM)
                // 设置宽度为屏幕宽度
                .setWidth(1f)
                // 设置黑色透明背景
                .setDimEnabled(false)
                // 设置动画
                .setAnimations(android.R.style.Animation_InputMethod);
        return dialog;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.picture_selector_fragment, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        takePhotoBtn = view.findViewById(R.id.picture_selector_take_photo_btn);
        pickPictureBtn = view.findViewById(R.id.picture_selector_pick_picture_btn);
        cancelBtn = view.findViewById(R.id.picture_selector_cancel_btn);
        // 设置按钮监听
        takePhotoBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture_selector_take_photo_btn:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 0);
                    Log.d(TAG,"拍照");
                }
                break;
            case R.id.picture_selector_pick_picture_btn:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 1);
                    Log.d(TAG,"从相册选择");
                }
                break;
            case R.id.picture_selector_cancel_btn:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 2);
                }
                break;
            default:
                break;
        }
    }

    public void show(FragmentActivity activity) {
        super.show(activity.getSupportFragmentManager(), TAG);
    }

    public void show(Fragment fragment) {
        super.show(fragment.getChildFragmentManager(), TAG);
    }

    /**
     * 设置选择监听
     *
     * @param l
     */

    public void setOnSelectedListener(OnSelectedListener l) {
        this.mOnSelectedListener = l;
    }

    /**
     * 选择监听接口
     */
    public interface OnSelectedListener {
        void OnSelected(View v, int position);
    }
}

