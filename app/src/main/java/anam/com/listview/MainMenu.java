package anam.com.listview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.splunk.mint.Mint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainMenu extends AppCompatActivity {

    private AdView mAdView;
    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Mint.initAndStartSession(MainMenu.this, "49eb25ad");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        setTitle(Html.fromHtml("<b>Book to Cook</b>"));


        mAdView = (AdView) findViewById(R.id.ad_view);
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("052E0DF3CAD81E9ED079C9DE38A484CB")
                .addTestDevice(getString(R.string.visit_device))
                .build();

        //modify string visit_device to add another device for ads


        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);



    }
    public void categoriesClicked(View view)
    {
        Intent i = new Intent(this,listActivity.class);
        i.putExtra("type", "category");
        startActivity(i);
    }

    public void allRecipesClicked(View view)
    {
        Intent i = new Intent(this,listActivity.class);
        i.putExtra("type","allRecipes");
        startActivity(i);
    }


    public void myRecipesClicked(View view)
    {
        Intent i = new Intent(this,LoginToRecipes.class);
         startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.cat:
                Intent i = new Intent(this,listActivity.class);
                i.putExtra("type", "category");
                startActivity(i);
                return true;
            case R.id.logId:
                Intent i1 = new Intent(this,LoginActivity.class);
                startActivity(i1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);

        if (mAdView != null) {
            mAdView.resume();
        }

    }

    @Override
    protected void onPause() {

        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    public void RefreshClicked(View view)
    {

      new RefreshData().execute(0);

    }

    private class RefreshData extends AsyncTask<Integer, Void, Void> {

        String msg = "success";
        @Override
        protected Void doInBackground(Integer... params) {

            try {
                String linkForCategoriesTable = "http://recipesprojectsmd.comlu.com/getAllCategories.php";
                String result = loadOnlineTable(linkForCategoriesTable);
                insertInCategory(result);


                String linkForRecipesTable = "http://recipesprojectsmd.comlu.com/getAllRecipes.php";
                result = loadOnlineTable(linkForRecipesTable);
                insertInRecipe(result);
            }
            catch (Exception e)
            {
                msg= "error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void obj) {

            if(msg.equals("success"))
            Toast.makeText(context, "Data Refreshed!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "No Internet!", Toast.LENGTH_SHORT).show();
        }
    }



    public String loadOnlineTable(String link) throws Exception {
            String result = "";
        InputStream isr = null;

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            isr = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "UTF-8"));
            String webPage = "", data = "";

            while ((data = reader.readLine()) != null) {
                webPage += data + "\n";
            }
            isr.close();
            result = webPage.toString();
            conn.disconnect();



        return result;

    }


    public void insertInCategory(String result) throws Exception{

        OfflineDB dbHelper = new OfflineDB(context);
        dbHelper.emptyTables();
        int id=0;
        int imgId=0;
        String CategoryName;
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);
            int resultLength = jArray.length();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                id = jsonObject.getInt("ID");
                CategoryName = jsonObject.getString("CategoryName").toString();
                imgId = jsonObject.getInt("ImgId");
                dbHelper.insertCategory(id, CategoryName, imgId);

                if (!ImageStorage.checkifImageExists (Integer.toString(imgId))) {
                    downloadImage(Integer.toString(imgId));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data " + e.toString());
        }


    }


    public void insertInRecipe(String result) throws Exception{

        OfflineDB dbHelper = new OfflineDB(context);
           int id=0;
           int catId=0;
           int imgId=0;
           String name, ing, dir;

        try {
            JSONArray jArray = new JSONArray(result);
            int resultLength = jArray.length();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                 id = jsonObject.getInt("ID");
                 name = jsonObject.getString("RecipeName").toString();
                 catId = (jsonObject.getInt("CategoryId"));
                 imgId = jsonObject.getInt("ImgId");
                 ing = jsonObject.getString("Ingredients").toString();
                 dir = jsonObject.getString("Directions").toString();
                dbHelper.insertRecipe(id, catId, name, imgId, ing, dir);
                if (!ImageStorage.checkifImageExists (Integer.toString(imgId))) {
                    downloadImage(Integer.toString(imgId));
                }
            }

        }
            catch(Exception e)
            {
                Log.e("log_tag", "Error Parsing Data " + e.toString());
            }

    }


    public void crashApplication(View view) {

        Mint.logException(new RuntimeException("Crash!!!"));

        throw new RuntimeException("This is a crash");

    }



        void downloadImage(String imagename_) throws Exception{
            String requestUrl = "http://recipesprojectsmd.comlu.com/"+imagename_+".jpg";
            Bitmap bitmap=null;

            URL url = new URL(requestUrl);
                URLConnection conn = url.openConnection();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());

            if(!ImageStorage.checkifImageExists(imagename_))
            {
                ImageStorage.saveToSdCard(bitmap, imagename_);

            }

        }
    }
