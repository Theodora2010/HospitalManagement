package com.example.myapplication;

import static com.example.myapplication.R.id.filterBtn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class DoctorImageEditActivity extends AppCompatActivity {

    ImageView img;
    Drawable img2;
    ImageButton btn, filterBtn, brightnessBtn, contrastBtn;
    HorizontalScrollView toosLayout;
    ConstraintLayout brightnessSeekBarLayout, contrastSeekBarLayout, filterBtnsLayout;
    TextView  brightnessSeekBarOkView,  contrastSeekBarOkView;
    SeekBar brightnessSeekerBar, contrastSeekerBar;
    BitmapDrawable ogBmp;
    TextView  filterBackBtn;
    String filtered;
    Bitmap filteredBMP;
    Bitmap outputBitmap;
    Bitmap mIcon;

    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_image_edit_activity);
        img = findViewById(R.id.imageView);
        btn = findViewById(R.id.camera);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(DoctorImageEditActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });


        ogBmp = (BitmapDrawable)img2;

        initializeViews();

    }

    private void initializeViews() {

        toosLayout = findViewById(R.id.toolsLayout);

        filterBtn = findViewById(R.id.filterBtn);
        filterBtnsLayout = findViewById(R.id.filterBtnsLayout);
        filterBackBtn = findViewById(R.id.filterBackBtn);

        brightnessBtn = findViewById(R.id.brightnessBtn);
        brightnessSeekBarLayout = findViewById(R.id.brightnessSeekBarLayout);
        brightnessSeekBarOkView = findViewById(R.id.brightnessSeekBarOkView);
        brightnessSeekerBar = findViewById(R.id.brightnessSeekBar);


        contrastBtn = findViewById(R.id.contrastBtn);
        contrastSeekBarLayout = findViewById(R.id.contrastSeekBarLayout);
        contrastSeekBarOkView = findViewById(R.id.contrastSeekBarOkView);
        contrastSeekerBar = findViewById(R.id.contrastSeekBar);


        filterBtn.setOnClickListener(view -> {
            toosLayout.setVisibility(View.GONE);
            filterBtnsLayout.setVisibility(View.VISIBLE);
        });
        filterBackBtn.setOnClickListener(view -> {
            toosLayout.setVisibility(View.VISIBLE);
            filterBtnsLayout.setVisibility(View.GONE);
        });

        brightnessBtn.setOnClickListener(view -> {
            brightnessSeekBarLayout.setVisibility(View.VISIBLE);
            toosLayout.setVisibility(View.GONE);
        });
        brightnessSeekBarOkView.setOnClickListener(view -> {
            brightnessSeekBarLayout.setVisibility(View.GONE);
            toosLayout.setVisibility(View.VISIBLE);
        });

        contrastBtn.setOnClickListener(view -> {
            contrastSeekBarLayout.setVisibility(View.VISIBLE);
            toosLayout.setVisibility(View.GONE);
        });
        contrastSeekBarOkView.setOnClickListener(view -> {
            contrastSeekBarLayout.setVisibility(View.GONE);
            toosLayout.setVisibility(View.VISIBLE);
        });


        filters();
        brightnessSeekerBar.setProgress(0);
        contrastSeekerBar.setProgress(255);
        seekBarListeners();

    }

    private void seekBarListeners() {
        //brightness
        brightnessSeekerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                adjustBrightness(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //contrast seekbar
        contrastSeekerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                adjustContrast(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void adjustContrast(int value) {
        Bitmap bmp = Bitmap.createBitmap(((BitmapDrawable) img2).getBitmap());
        if (filteredBMP != null) {
            bmp = filteredBMP;
        }
        //define mul
        //final int mul = 0xFFFFFF; // constant at 255
        //define add value
        String initialHex = Tool.hexScale()[value];
        String initialCon = "0X"+initialHex + initialHex + initialHex;
        int mul = Integer.decode(initialCon);
        int add = 0x000000;

        // generate output bitmap
        Bitmap outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false).copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        ColorFilter colorFilter = new LightingColorFilter(mul, add);
        paint.setColorFilter(colorFilter);

        Canvas canvas = new Canvas(outputBitmap);
        canvas.drawBitmap(outputBitmap, 0, 0, paint);
        img.setImageBitmap(outputBitmap);
    }

    private void adjustBrightness(int value) {
        Bitmap bmp = Bitmap.createBitmap(((BitmapDrawable) img2).getBitmap());
        if (filteredBMP != null) {
            bmp = filteredBMP;
        }
        //define mul
        final int mul = 0xFFFFFF; // constant at 255
        //define add value
        String initialHex = Tool.hexScale()[value];
        String initialAdd = "0X"+initialHex + initialHex + initialHex;
        int add = Integer.decode(initialAdd);

        // generate output bitmap
        Bitmap outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false).copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        ColorFilter colorFilter = new LightingColorFilter(mul, add);
        paint.setColorFilter(colorFilter);

        Canvas canvas = new Canvas(outputBitmap);
        canvas.drawBitmap(outputBitmap, 0, 0, paint);
        img.setImageBitmap(outputBitmap);
    }

    private void filters() {
        //grey btn
        ImageView greyBtn = findViewById(R.id.greyBtn);
        //filterBtn(greyBtn, Filter.grey);
        greyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(Filter.grey);
            }
        });
        ImageView normalBtn = findViewById(R.id.normalBtn);
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(Filter.normal);
            }
        });


        ImageView binaryBtn = findViewById(R.id.binaryBtn);
        filterBtn(binaryBtn, Filter.binary);
        binaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(Filter.binary);
            }
        });


        ImageView invertBtn = findViewById(R.id.invertBtn);
        filterBtn(invertBtn, Filter.invert);
        invertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(Filter.invert);
            }
        });
    }

    private void filterBtn(ImageView btn, String filter) {

    }


    private void filter(String filter) {
        Bitmap bmp = Bitmap.createBitmap(((BitmapDrawable) img2).getBitmap());
        //Bitmap bmp = Bitmap.createBitmap(ogBmp.getBitmap());
        outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false).copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(outputBitmap);

        if (filter.equalsIgnoreCase(Filter.grey)){
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(colorFilter);

            canvas.drawBitmap(outputBitmap, 0,0, paint);
        } else if (filter.equalsIgnoreCase(Filter.normal)){
            outputBitmap = bmp;
        }else if (filter.equalsIgnoreCase(Filter.binary)){
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            //binary threshold
            float m = 255f;
            float t = -255*128f;
            ColorMatrix threshold = new ColorMatrix(new float[]{
                    m, 0, 0, 1, t,
                    0, m, 0, 1, t,
                    0, 0, m, 1, t,
                    0, 0, 0, 1, 0,
            });

            //convert the color matrix to grey scale
            colorMatrix.postConcat(threshold);

            //color filter
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(colorFilter);

            canvas.drawBitmap(outputBitmap, 0,0, paint);
        } else if (filter.equalsIgnoreCase(Filter.invert)){
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            colorMatrix.set(new float[]{
                    -1, 0, 0, 0, 255,
                    0, -1, 0, 0, 255,
                    0, 0, -1, 0, 255,
                    0, 0, 0, 1, 0
            });



            //color filter
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(colorFilter);

            canvas.drawBitmap(outputBitmap, 0,0, paint);
        }

        img.setImageBitmap(outputBitmap);

        filteredBMP = outputBitmap;
        filtered = filter;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        img.setImageURI(uri);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            img2 = Drawable.createFromStream(inputStream, uri.toString() );
        } catch (FileNotFoundException e) {
            img2 = getResources().getDrawable(R.drawable.background);
        }
        //img2.setImageResource(img);
    }


    public void saveEventAction(View view)
    {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(DoctorImageEditActivity.this, DoctorEventEditActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        //intent1.putExtra("outputBitmap", outputBitmap);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        outputBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
        byte[] byteArray = bs.toByteArray();
        intent1.putExtra("byteArray", byteArray);
        startActivity(intent1);
    }

    public void backAction(View view)
    {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(DoctorImageEditActivity.this, DoctorEventEditActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }


}
