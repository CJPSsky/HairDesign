package com.ssky.example.hairdesign.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.ssky.example.hairdesign.R;
import com.ssky.example.hairdesign.activity.MainActivity;
import com.ssky.example.hairdesign.activity.ShowActivity;

import java.io.File;
import java.io.IOException;

public class MainFragment extends PictureSelectorFragment {
    Toolbar toolbar;
    ImageView mPictureIv;
    TextView maleTxt;
    Button testHairBtn;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        //maleTxt = view.findViewById(R.id.male_txt);
        testHairBtn = view.findViewById(R.id.test_hair_btn);
       // mPictureIv = view.findViewById(R.id.main_frag_picture_iv);
        initEvents();
    }

    public void initEvents() {
        // 设置图片点击监听
        testHairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });

        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                //mPictureIv.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                String imagePath = Uri.decode(filePath);
                Toast.makeText(getContext(), "图片已经保存到:" + imagePath, Toast.LENGTH_LONG).show();
                File photo_file=new File(imagePath);
                sendRequestWithOkHttp(photo_file);
                Intent intent = new Intent(getActivity(),ShowActivity.class);
                intent.putExtra("shape","oval");
                intent.putExtra("sex","male");
                intent.putExtra("path",imagePath);
//                intent.setData(fileUri);
                startActivity(intent);
            }
        });
   }

    private void sendRequestWithOkHttp(final File photo_file){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    if (photo_file != null){
                        String imageType = "multipart/form-data";
                        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"),photo_file);
                        RequestBody requestBody = new Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("imageType",imageType)
                                .addFormDataPart("img","test.jpg",fileBody).build();
                        Request request = new Request.Builder()
                                .url("http://192.168.1.201:8000/upload")
                                .post(requestBody)
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        System.out.println(responseData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
