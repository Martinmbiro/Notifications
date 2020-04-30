package com.ahrefs.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.ahrefs.myapplication.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {
    private Button mButton1;
    private EditText mTitle;
    private EditText mDescription;
    private NotificationManagerCompat mManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initiate views
        mTitle = findViewById(R.id.title_editText);
        mDescription = findViewById(R.id.description_editText);
        mButton1 = findViewById(R.id.channel_1_button);

        mManagerCompat = NotificationManagerCompat.from(this);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                //Create task builder and add intent
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addNextIntentWithParentStack(intent);
                //Get intent containing the entire stack
                PendingIntent activityIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

                //Circular Bitmap
                Bitmap fromDrawable = BitmapFactory.decodeResource(getResources(),R.drawable.empty);
                Bitmap avatar = getCircleBitmap(fromDrawable);
                Notification notification1 = new NotificationCompat
                        .Builder(MainActivity.this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setLargeIcon(avatar)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(mDescription.getText().toString())
                                .setBigContentTitle(mTitle.getText().toString()))
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentTitle(mTitle.getText().toString())
                        .setContentText(mDescription.getText().toString())
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(false)
                        .setContentIntent(activityIntent)
                        .build();

                mManagerCompat.notify(1, notification1);

            }
        });
    }

    private static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output;
        Rect srcRect, dstRect;
        float r;
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        if (width > height){
            output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
            int left = (width - height) / 2;
            int right = left + height;
            srcRect = new Rect(left, 0, right, height);
            dstRect = new Rect(0, 0, height, height);
            r = height / 2;
        }else{
            output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            int top = (height - width)/2;
            int bottom = top + width;
            srcRect = new Rect(0, top, width, bottom);
            dstRect = new Rect(0, 0, width, width);
            r = width / 2;
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        bitmap.recycle();

        return output;
    }
}
