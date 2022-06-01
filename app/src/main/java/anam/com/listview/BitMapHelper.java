package anam.com.listview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Babar Baig on 3/21/2016.
 */
public class BitMapHelper {



    public static Bitmap loadImageBitMap(Resources res,int r){
        //File f=new File(path);
        //Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        Bitmap b = BitmapFactory.decodeResource(res, r);
        return b;
    }


    public static void removeFromCard(){
        File file = Environment.getExternalStorageDirectory();
        file = new File(file,"com.app.recipes.temp");
        deleteDirectory(file);
    }



    static String moveToSdCard(Bitmap path,String path2){
        File pictureFile = null;

        try {
            File rootSdDirectory = Environment.getExternalStorageDirectory();
            File directory = new File(rootSdDirectory,"/com.app.recipes.temp");
            directory.mkdir();

            pictureFile = new File(directory,path2.substring(path2.lastIndexOf('/')+1));
            if (pictureFile.exists()) {
                pictureFile.delete();
            }

            pictureFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(pictureFile);
            path.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();

        } catch (Exception e) {
            Log.e("Moveing file to SD Card",e.getMessage());
        }

        if(pictureFile!=null)
            return pictureFile.getAbsolutePath();
        else
            return null;
    }


    static public boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }

        return( path.delete() );
    }


}
