package com.ssky.example.hairdesign.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssky.example.hairdesign.R;
import com.ssky.example.hairdesign.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {
    //判断是否是横屏
    public boolean isLand;

    //当前选中图片信息
    private String selectedImageIntro;
    private int selectedImageId;
    //分别存储男性发型和女性发型
    private List<ImageUtil> imageMaleList = new ArrayList<>();
    private List<ImageUtil> imageFemaleList = new ArrayList<>();

    private RecyclerView imgListRv;
    //切换性别按钮
    private Button maleBtn;
    private Button femaleBtn;
    //性别信息
    private String sexData;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.hair_list_fragment, container, false);
        initHairImage();
        intiViews(view);
        if (savedInstanceState != null){
            ShowFragment showFragment = null;
            if (getFragmentManager() != null) {
                showFragment = (ShowFragment)getFragmentManager().findFragmentById(R.id.show_iv);
            }
            if (showFragment != null) {
                showFragment.refresh(savedInstanceState.getString("imageName"), savedInstanceState.getInt("imageId"));
            }
        }
        return view;
    }

    private void intiViews(View view){
        imgListRv = view.findViewById(R.id.img_list_recycler_view);
        maleBtn = view.findViewById(R.id.male_btn);
        femaleBtn = view.findViewById(R.id.female_btn);
        // 创建并添加layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgListRv.setLayoutManager(layoutManager);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        sexData = intent.getStringExtra("sex");
        System.out.println(sexData);
        if (sexData.equals("male")){
            ImgAdapter imgAdapter = new ImgAdapter(imageMaleList);
            imgListRv.setAdapter(imgAdapter);
            System.out.println("ListFragment:"+sexData);
        } else if (sexData.equals("female")){
            ImgAdapter imgAdapter = new ImgAdapter(imageFemaleList);
            imgListRv.setAdapter(imgAdapter);
        }
        //初始化图片列表
        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgAdapter imgAdapter = new ImgAdapter(imageMaleList);
                imgListRv.setAdapter(imgAdapter);
            }
        });
        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgAdapter imgAdapter = new ImgAdapter(imageFemaleList);
                imgListRv.setAdapter(imgAdapter);
            }
        });
    }

    class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder>{
        //Recycle要显示的list
        private List<ImageUtil> mImgList;
        class ViewHolder extends RecyclerView.ViewHolder{
            View imgView;
            TextView imgName;
            ImageView imgImage;
            ViewHolder(View view){
                super(view);
                imgView = view;
                imgName = view.findViewById(R.id.img_intro);
                imgImage = view.findViewById(R.id.img_image);
            }
        }
        //构造方法，传入显示的数据源
        ImgAdapter(List<ImageUtil> imgList)
        {
            mImgList = imgList;
        }

        @NonNull
        @Override
        public ImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item,viewGroup,false);
            final ShowFragment showFragment = (ShowFragment)getFragmentManager().findFragmentById(R.id.show_fragment);
            final ViewHolder holder = new ViewHolder(view);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    //获取点击的对象的实例
                    int position = holder.getAdapterPosition();
                    ImageUtil imageUtil = mImgList.get(position);
                    selectedImageIntro = imageUtil.getImageIntro();
                    selectedImageId = imageUtil.getImgId();
                    if (showFragment != null) {
                        showFragment.refresh(imageUtil.getImageIntro(), imageUtil.getImgId());
                    }
                    Toast.makeText(getContext(), imageUtil.getImageIntro(),Toast.LENGTH_SHORT).show();
                }
            });
            return holder;
        }

        // 对RecyclerView子项进行赋值
        @Override
        public void onBindViewHolder(@NonNull ImgAdapter.ViewHolder viewHolder, int i) {
            ImageUtil imageUtil = mImgList.get(i);
            viewHolder.imgImage.setImageResource(imageUtil.getImgId());
            viewHolder.imgName.setText(imageUtil.getImageIntro());
        }

        // 获得RecyclerView子项的个数
        @Override
        public int getItemCount() {
            return mImgList.size();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Configuration mConfiguration = this.getResources().getConfiguration();  //获取配置信息
        int ori = mConfiguration.orientation;   //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_PORTRAIT){
            //横屏
            isLand = true;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT)
        {
            //竖屏
            isLand = false;
        }
    }

    private void initHairImage() {

        if (imageMaleList.isEmpty()){
            ImageUtil imgMale1 = new ImageUtil("第一张图片",R.drawable.mhair1);
            ImageUtil imgMale2 = new ImageUtil("第二张图片",R.drawable.mhair2);
            ImageUtil imgMale3 = new ImageUtil("第三张图片",R.drawable.mhair3);
            ImageUtil imgMale4 = new ImageUtil("第四张图片",R.drawable.mhair4);
            ImageUtil imgMale5 = new ImageUtil("第五张图片",R.drawable.mhair5);
            imageMaleList.add(imgMale1);
            imageMaleList.add(imgMale2);
            imageMaleList.add(imgMale3);
            imageMaleList.add(imgMale4);
            imageMaleList.add(imgMale5);
        }

        if (imageFemaleList.isEmpty()){
            ImageUtil imgFemale1 = new ImageUtil("第一张图片",R.drawable.fhair1);
            ImageUtil imgFemale2 = new ImageUtil("第二张图片",R.drawable.fhair2);
            ImageUtil imgFemale3 = new ImageUtil("第三张图片",R.drawable.fhair3);
            ImageUtil imgFemale4 = new ImageUtil("第四张图片",R.drawable.fhair4);
            ImageUtil imgFemale5 = new ImageUtil("第五张图片",R.drawable.fhair5);
            ImageUtil imgFemale6 = new ImageUtil("第五张图片",R.drawable.fhair6);
            ImageUtil imgFemale7 = new ImageUtil("第五张图片",R.drawable.fhair7);
            ImageUtil imgFemale8 = new ImageUtil("第五张图片",R.drawable.fhair8);
            imageFemaleList.add(imgFemale1);
            imageFemaleList.add(imgFemale2);
            imageFemaleList.add(imgFemale3);
            imageFemaleList.add(imgFemale4);
            imageFemaleList.add(imgFemale5);
            imageFemaleList.add(imgFemale6);
            imageFemaleList.add(imgFemale7);
            imageFemaleList.add(imgFemale8);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imageName", selectedImageIntro);
        outState.putInt("imageId", selectedImageId);
    }

    public String getSelectedImageIntro() {
        return selectedImageIntro;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }
}
