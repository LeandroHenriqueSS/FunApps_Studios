package androidlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * HAND TALK - A Translator Plataform from Spoken and
 * Written Languages to Sign languages.
 * http://www.handtalk.me
 *
 * Created by carloswanderlan on 3/6/17.
 */

public class TutorialSDK extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(br.com.handtalk.androidlib.R.layout.activity_tutorial_sdk);

//        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageTutorial);
//        gifImageView.setGifImageResource(R.drawable.handtalk_tutorial);
//        <!--<br.com.handtalk.androidlib.GifImageView-->
//        <!--android:id="@+id/GifImageTutorial"-->
//        <!--android:layout_centerHorizontal="true"-->
//        <!--android:layout_centerVertical="true"-->
//        <!--android:layout_width="wrap_content"-->
//        <!--android:layout_height="wrap_content" />-->

        ImageView closeButton = (ImageView) findViewById(br.com.handtalk.androidlib.R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
