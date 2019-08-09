package com.ssky.example.hairdesign.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ssky.example.hairdesign.R;
import com.ssky.example.hairdesign.view.StickerView;

import java.io.FileNotFoundException;
import java.util.Objects;

public class ShowFragment extends Fragment {
    private View view;
    private StickerView stickerView;
    private RelativeLayout group;
    private Bitmap resultBitmap;
    private String resultData;
    private Uri imageUri;
    private ImageView showIv;
    private static final String TAG = "ShowActivity";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.show_fragment, container, false);
        intiViews();
        if (savedInstanceState != null){
            String intro = savedInstanceState.getString("imageIntro");
            int resourceId = savedInstanceState.getInt("imageId");
            if (intro != null && resourceId != 0){
                refresh(intro,resourceId);
            }
        }
        return view;
    }

    /*
     * 初始化View
     * */
    private void intiViews(){
        stickerView = (StickerView) view.findViewById(R.id.stick);
        showIv = (ImageView)view.findViewById(R.id.show_iv);
        group = (RelativeLayout)view.findViewById(R.id.group);
        group.setDrawingCacheEnabled(true);
        group.buildDrawingCache();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.mhair1);
        stickerView.setWaterMark(bitmap);
        stickerView.setOnStickerDeleteListener(new StickerView.OnStickerDeleteListener() {
            @Override
            public void onDelete() {

            }
        });
        //获取上一个Activity传送的数据
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        resultData= intent.getStringExtra("shape");
        String path = intent.getStringExtra("path");
        if (path != null){
            Log.d(TAG,path);
            resultBitmap = BitmapFactory.decodeFile(path);
        } else {
            try {
                resultBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //根据图片和运算结果设置布局效果
        showIv.setImageBitmap(resultBitmap);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) stickerView.getLayoutParams();
        params.topMargin = stickerView.getTop() - 300;
        stickerView.setLayoutParams(params);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void refresh(String img_intro, int img_resourceId)
    {
        if (view != null)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),img_resourceId);
            stickerView.setWaterMark(bitmap);
            stickerView.setOnStickerDeleteListener(new StickerView.OnStickerDeleteListener() {

                @Override
                public void onDelete() {

                }
            });
        } else {
            Toast.makeText(getContext(),"view未被创建",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //获取当前Fragment2 的实例，来获取当前大图的信息
        ListFragment listFragment = null;
        if (getFragmentManager() != null) {
            listFragment = (ListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
        }
        if (listFragment != null) {
            outState.putString("imageName",listFragment.getSelectedImageIntro());
            outState.putInt("imageId",listFragment.getSelectedImageId());
        }
    }
}
