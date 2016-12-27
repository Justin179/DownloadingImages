package tw.com.nec.justin_chen.downloadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImage;

    public void downloadImage(View view){

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {

            // https://en.wikipedia.org/wiki/Bart_Simpson#/media/File:Bart_Simpson_200px.png (這個不能用)
            // https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png
            myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();
            downloadedImage.setImageBitmap(myImage);


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("Interaction","Button Tapped!");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedImage = (ImageView) findViewById(R.id.imageView);

    }

    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls){

            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // download all the data in one go, and convert it into Bitmap
                connection.connect(); // open the browser?

                // download the whole inputstream in one go
                InputStream inputStream = connection.getInputStream();

                // convert that to Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
