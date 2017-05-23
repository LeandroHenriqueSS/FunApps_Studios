package com.lh_freeapps.coxinhaoumortadela.ui.result;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lh_freeapps.coxinhaoumortadela.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Class used for sharing the result through facebook or another app (whatsapp, gmail and so on)
 */

public class ShareResultImageUtil {

    private Context con;

    private Bitmap bitmapResult;

    private static final String filename = "sharableImageFile.png";


    public ShareResultImageUtil(Context con) {
        this.con = con;
    }


    public void shareResult(ViewGroup viewGroup, View bt) {
        File file = new File(con.getFilesDir(), filename);

        if (bitmapResult == null) {
            createSharableImage(viewGroup);
            saveSharableImageIntoFile(file);
        }

        // create intent
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(con, con.getPackageName(), file));

        // start share activity
        String facebookPackage = getFacebookPackageName(intent);
        if (bt.getId() == R.id.bt_facebook && facebookPackage != null)
            con.startActivity((intent.setPackage(facebookPackage)));
        else // view.getId() == R.id.bt_share
            con.startActivity(Intent.createChooser(intent, con.getString(R.string.sharing_chooser)));

    }

    /** prepare result layout and convert to bitmap */
    private void createSharableImage(ViewGroup viewGroup) {
        // get original LinearLayout params
        ViewGroup.LayoutParams originalParams = viewGroup.getLayoutParams();

        // alter LinearLayout provisionally
        LinearLayout.LayoutParams provisoryParams = new LinearLayout.LayoutParams(900, 600);
        viewGroup.setLayoutParams(provisoryParams);
        viewGroup.findViewById(R.id.result_buttons).setVisibility(View.GONE);

        // create a Bitmap from a LinearLayout
        viewGroup.setDrawingCacheEnabled(true);
        bitmapResult = Bitmap.createBitmap(viewGroup.getDrawingCache());
        viewGroup.setDrawingCacheEnabled(false);

        // restore layout
        viewGroup.setLayoutParams(originalParams);
        viewGroup.findViewById(R.id.result_buttons).setVisibility(View.VISIBLE);
    }


    /** the image needs to be saved into file in ordem to be shared */
    private void saveSharableImageIntoFile(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmapResult.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /** get a facebook's package name installed in the device if it exist
      * It could be Facebook App, Facebook Light or might be other */
    private String getFacebookPackageName(Intent intent) {
        List<ResolveInfo> matches = con.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo info : matches)
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana"))
                return info.activityInfo.packageName;


        //TODO: maybe I should move it to my activity
        Toast.makeText(con, con.getString(R.string.er_facebook_not_found), Toast.LENGTH_LONG).show();
        return null;
    }


}