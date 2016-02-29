package com.globussoft.readydoctors.patient.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.Singleton;

import java.util.List;

/**
 * Created by GLB-276 on 12/21/2015.
 */
public class GetFreeVisits extends Activity
{
    ImageView backImage,fShare,tWeet,gMail,tExt;
    TextView promocode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_free_visits);
        initUI();
    }
    public void initUI()
    {
        promocode=(TextView)findViewById(R.id.promocode);
        promocode.setText(Singleton.PromoCode);
        fShare=(ImageView)findViewById(R.id.fshare);

        fShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"fShare",Toast.LENGTH_SHORT).show();


             /*String shareBody = "Try ReadyDoctor-see MDs and Psychologist on your phone or PC.Use my code "+ Singleton.PromoCode+" get 1st visit FREE!!!.";

               *//* Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

                sharingIntent.setType("text/plain");

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Try ReadyDoctor");

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

//                startActivity(Intent.createChooser(sharingIntent, shareBody));
                sharingIntent.setPackage("com.facebook.katana");
                startActivity(sharingIntent);*//*

                String packageAd ="com.facebook.katana";
                String warn ="facebook not found";
                PackageManager pm = getPackageManager();

                try
                {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);

                    waIntent.setType("text/plain");

                    PackageInfo info = pm.getPackageInfo(packageAd,PackageManager.GET_META_DATA);

                    // Check if package exists or not. If not then code
                    // in catch block will be called

                    waIntent.setPackage(packageAd);

                    waIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                    startActivityForResult((Intent.createChooser(waIntent, "Share App on")), 555);

                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Toast.makeText(getApplicationContext(), warn, Toast.LENGTH_SHORT).show();
                }*/




                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Try ReadyDoctor");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Try ReadyDoctor-see MDs and Psychologist on your phone or PC.Use my code "+ Singleton.PromoCode+" get 1st visit FREE!!!.");
                shareIntent.setPackage("com.facebook.katana");
                startActivity(shareIntent);


               /* Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, "https://www.codeofaninja.com");

                startActivity(Intent.createChooser(share, "Share link!"));*/
            }
        });
        tWeet=(ImageView)findViewById(R.id.tweet);
        tWeet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent tweetIntent = new Intent(Intent.ACTION_SEND);
                tweetIntent.putExtra(Intent.EXTRA_TEXT, "dod.globusapps.com");
                tweetIntent.setType("text/plain");

                PackageManager packManager = getPackageManager();
                List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

                boolean resolved = false;
                for(ResolveInfo resolveInfo: resolvedInfoList)
                {
                    if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android"))
                    {
                        tweetIntent.setClassName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name );
                        resolved = true;
                        break;
                    }
                }
                if(resolved)
                {
                    startActivity(tweetIntent);
                }else
                {
                    Toast.makeText(getApplicationContext(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
                }
            }
        });
        gMail=(ImageView)findViewById(R.id.gmail);
        gMail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","abc@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I thought you would like ReadyDoctor, a new app that let's you see a Doctor," +
                        "Pshychologist and more from your smartphone or computer." +
                        "I tried it-I loved it!Check out ReadyDoctor-See a Doctor,Psychologist and more from your phone or PC." +
                        "Try it for FREE.Use my Code "+ Singleton.PromoCode+"to get 1st visit FREE!!!.");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        tExt=(ImageView)findViewById(R.id.Text);
        tExt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body","ReadyDoctor-see MDs and Psychologist on your phone.Use my code "+ Singleton.PromoCode+"to get 1st visit FREE!!!.");
                startActivity(smsIntent);
            }
        });
        backImage=(ImageView)findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }
}