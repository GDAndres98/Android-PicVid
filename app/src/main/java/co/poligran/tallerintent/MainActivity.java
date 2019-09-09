package co.poligran.tallerintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    private boolean isCamera = true;

    private Button prevButton;  // Button previous item
    private Button nextButton;  // Button next item
    private Button changeButton; // Button change capture mode, Video or Image
    private Button captureButton;   // Button that captures a Video or a Picture
    private Button galleryButton;   // Button that opens GalleryActivity


    private ImageView actualImage;
    private VideoView actualVideo;
    private int imageIndex = -1;
    private int videoIndex = -1;
    private ArrayList<Bitmap> images;
    private ArrayList<Uri> videos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        images = new ArrayList<>();
        videos = new ArrayList<>();
        actualImage = (ImageView) findViewById(R.id.image);
        actualVideo = (VideoView) findViewById(R.id.video);
        prevButton = (Button) findViewById(R.id.prev);
        nextButton = (Button) findViewById(R.id.next);
        changeButton = (Button) findViewById(R.id.changeCapture);
        captureButton = (Button) findViewById(R.id.capture);
        galleryButton = (Button) findViewById(R.id.openGallery);
    }

    public void changeCapture(View view) {
        if (isCamera) {
            actualVideo.setVisibility(View.VISIBLE);
            actualImage.setVisibility(View.INVISIBLE);
            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeVid(v);
                }
            });
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previousVid(v);
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextVid(v);
                }
            });
            captureButton.setText(R.string.take_video);
            changeButton.setText(R.string.picture);
            setVideo(videoIndex);
        } else {
            actualImage.setVisibility(View.VISIBLE);
            actualVideo.setVisibility(View.INVISIBLE);
            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePic(v);
                }
            });
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previousPic(v);
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextPic(v);
                }
            });
            captureButton.setText(R.string.take_picture);
            changeButton.setText(R.string.video);
            setImage(imageIndex);
        }
        isCamera = !isCamera;
    }


    public void takePic(View view) {
        Toast.makeText(this, "Tomar Foto", Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void setImage(int i) {
        if (images.isEmpty())
            return;
        imageIndex = (i + images.size()) % images.size();
        System.out.println(imageIndex);
        actualImage.setImageBitmap(images.get(imageIndex));
    }

    public void previousPic(View view) {
        setImage(imageIndex - 1);
    }

    public void nextPic(View view) {
        setImage(imageIndex + 1);
    }

    public void takeVid(View view) {
        Toast.makeText(this, "Tomar Video", Toast.LENGTH_SHORT).show();
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
    }

    private void setVideo(int i) {
        if (videos.isEmpty())
            return;
        videoIndex = (i + videos.size()) % videos.size();
        System.out.println(videoIndex);
        actualVideo.setVideoURI(videos.get(videoIndex));
        actualVideo.start();
    }

    public void previousVid(View view) {
        setVideo(videoIndex - 1);
    }

    public void nextVid(View view) {
        setVideo(videoIndex + 1);
    }

    public void setVideo(View view) {
        setVideo(videoIndex);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(this, "Tome su foto :v", Toast.LENGTH_SHORT).show();

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images.add(imageBitmap);
            setImage(images.size() - 1);
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videos.add(data.getData());
            setVideo(videos.size()-1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openGallery(View view){
        Intent openActivityIntent = new Intent(this,GalleryActivity.class);
        startActivity(openActivityIntent);
    }

}
