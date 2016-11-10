package com.czauthor.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.czauthor.R;

public class MainActivity extends ActivityGroup {
    private static int RESULT_LOAD_IMAGE = 1;
    private Button mAddImageButton = null;
    private ImageView mImageView = null;
    private TextView mNoImageTextFiled = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            mAddImageButton = (Button) findViewById(R.id.add_image_button);
            mImageView = (ImageView) findViewById(R.id.image_view);
            mNoImageTextFiled = (TextView) findViewById(R.id.no_image_text_filed);

            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

            if(mAddImageButton != null) {
                mAddImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImageFromGallery();
                    }
                });
            }

            final String imageTabTag = getString(R.string.ImageTabTag);
            final String writeTabTag = getString(R.string.WriteTabTag);

            if (tabHost != null) {
                tabHost.setup(this.getLocalActivityManager());
                TabHost.TabSpec tab1 = tabHost.newTabSpec(imageTabTag);
                tab1.setIndicator(getString(R.string.image_tab));
                tab1.setContent(android.R.id.tabcontent);//new Intent(this, SelectImage.class));
                tabHost.addTab(tab1);

                TabHost.TabSpec tab2 = tabHost.newTabSpec(writeTabTag);
                tab2.setIndicator(getString(R.string.write_tab));
                tab2.setContent(android.R.id.tabcontent);//new Intent(this, WriteText.class));
                tabHost.addTab(tab2);

                tabHost.setCurrentTab(0);
                setTabHostView(0);

                tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                    @Override
                    public void onTabChanged(String tabId) {
                        if (imageTabTag.equals(tabId)) {
                            setTabHostView(0);
                        } else {
                            setTabHostView(1);
                        }
                    }
                });
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void setTabHostView(int mode) {
        if(mAddImageButton == null || mImageView == null || mNoImageTextFiled == null) {
            return;
        }
        try {
            if(mode == 0) {
                mAddImageButton.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
                mNoImageTextFiled.setVisibility(View.VISIBLE);
            } else {
                mAddImageButton.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
                mNoImageTextFiled.setVisibility(View.GONE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void selectImageFromGallery() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                mImageView.setImageBitmap(bitmap);
                mNoImageTextFiled.setVisibility(View.GONE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
