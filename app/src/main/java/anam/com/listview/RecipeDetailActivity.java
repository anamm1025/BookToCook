package anam.com.listview;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import com.splunk.mint.Mint;

import java.io.File;
import java.util.HashMap;



import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.content.Intent;
import java.util.Locale;
import android.widget.Toast;
public class RecipeDetailActivity extends AppCompatActivity implements OnClickListener, OnInitListener {

    Context context = this;

    String title;
    int id;
    String type;
    String imUrl;
    String cat;
    DetailObj obj;
    TextView ing;
    TextView dir;


    //status check code
    private int MY_DATA_CHECK_CODE = 0;
    private TextToSpeech myTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ing = (TextView) findViewById(R.id.ingridients);
        dir = (TextView) findViewById(R.id.direction);

        Intent i = this.getIntent();

        title = i.getStringExtra("recTitle");
        id = i.getIntExtra("recID", 0);
        type = i.getStringExtra("typeOfDetail");
        cat = i.getStringExtra("cat");
        imUrl = i.getStringExtra("imUrl");
        setTitle(Html.fromHtml("<b>"+title+"</b>"));

        OfflineDB db = new OfflineDB(this);
        obj = db.getDetail(id, type);

        if(obj!=null)
        {
            ing.setText("INGREDIENTS:\n" + obj.ingredients);
            dir.setText("\nDIRECTIONS:\n" + obj.directions);
        }



        //------------------------------------------------------------
        //TTS setup

        ImageButton speak = (ImageButton) findViewById(R.id.speaker);
        speak.setOnClickListener(this);

        //check tts data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        //------------------------------------------------------------


    }//end on create


    //===================================================================================================

    //TEXT - TO - SPEECH SECTION STARTS HERE

    public void onClick(View v)
    {
        //get the text entered
        String words = "Ingredients are as follows " + ing.getText().toString();
        words+=" Directions are as follows " + dir.getText().toString();
        speakWords(words);
    }

    private void speakWords(String speech)
    {
        //speak straight away
        try{
            myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }catch(RuntimeException e){
            Mint.logException(new RuntimeException("Inside RecipeDetail Function speakwords"));
            throw new RuntimeException("This is a crash");
        }

    }

    //setup TTS
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Mint.logException(new RuntimeException("Text to speech error onInit function"));
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
    //TEXT - TO - SPEECH ENDS HERE
    //===================================================================================================



    public void shareButtonClicked(View v) {

        final Dialog alert = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        alert.setTitle("Share Recipe");
        View view = inflater.inflate(R.layout.share_dialog, (ViewGroup) findViewById(R.id.root_share_dialog));

        LinearLayout fb = (LinearLayout) view.findViewById(R.id.facebook);
        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                test(v);
            }


        });


        LinearLayout twitter = (LinearLayout) view.findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Twitter clicked", Toast.LENGTH_SHORT).show();
            }


        });


        LinearLayout mail = (LinearLayout) view.findViewById(R.id.email);
        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Mail clicked", Toast.LENGTH_SHORT).show();

            }


        });


        LinearLayout msg = (LinearLayout) view.findViewById(R.id.sms);
        msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "SMS clicked", Toast.LENGTH_SHORT).show();

            }


        });


        LinearLayout call = (LinearLayout) view.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Call clicked", Toast.LENGTH_SHORT).show();

            }


        });


        Button cancel = (Button) view.findViewById(R.id.cancelDialog);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alert.dismiss();
            }


        });

        alert.setContentView(view);
        alert.show();
    }

    public void test(View view) {

        Bitmap b=null;
        if (ImageStorage.checkifImageExists (imUrl))
        {
            File file = ImageStorage.getImage("/" +imUrl + ".jpg");
            String path = file.getAbsolutePath();
            if (path != null) {
                b = BitmapFactory.decodeFile(path);
            }
        }

              ///case R.id.share_facebook:
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(b)
                .build();

        String description =  "INGREDIENTS:"+ System.getProperty("line.separator")  + obj.ingredients + System.getProperty("line.separator") + System.getProperty("line.separator") + "DIRECTIONS:" + System.getProperty("line.separator") +  obj.directions;

        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "android-recipe:recipe")
                .putString("og:title", title)
                .putString("og:description", description)
                .putPhoto("og:image", photo)
                .build();
//                            //.putInt("recipe:id", catRec.second.getCommonInfo().getId())
//                            //.putDouble("recipe:rating", catRec.second.getRating())
//
//
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("android-recipe:learn")
                .putObject("recipe", object)
                .build();
//
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("recipe")
                .setAction(action)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        if (!shareDialog.canShow(content))
            Log.e("Share", "Cannot show.");
        else {
            ShareDialog.show(this, content);
        }

        //   break;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.simple_menu, menu);


        //Google integration

        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_item_share));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_TEXT, title+"\n\nINGREDIENTS:\n" + obj.ingredients + "\n\nDIRECTIONS:\n" + obj.directions);
        shareIntent.putExtra(Intent.EXTRA_TITLE, title);



       if (ImageStorage.checkifImageExists (imUrl))
        {
            File file = ImageStorage.getImage("/" + imUrl + ".jpg");
            String path = file.getAbsolutePath();
            if (path != null) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));

            }
        }

        //1st param = bitmap, 2nd path = filename on temporary directory
    //    String sharedImagePath = BitMapHelper.moveToSdCard(BitMapHelper.loadImageBitMap(getResources(), R.drawable.rec1), "temp.jpg");
              mShareActionProvider.setShareIntent(shareIntent);


        return true;
    }

/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  BitMapHelper.removeFromCard();
        Log.e("DetailedActivity", "OnDestroy");
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(this, MainMenu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            case R.id.about:
                Intent i1 = new Intent(this, AboutActivity.class);
                startActivity(i1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void restaurantButtonClicked(View view)
    {
        GPSTracker gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double strSourceLatitude = gps.getLatitude();
            double strSourceLongitude = gps.getLongitude();
            String strDestinationLatitude ="31.471141";    //these values will come from database
            String strDestinationLongitude = "74.308863";  //these values will come from database

            Intent directioIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + strSourceLatitude + "," + strSourceLongitude + "&daddr=" + strDestinationLatitude + "," + strDestinationLongitude));
            directioIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(directioIntent);


        }
        else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        gps.stopUsingGPS();

    }

    //---------------------------------------------------------------------------------------

//    public void speakRecipeDetail(View view)
//    {
//        //TTS content of recipe
//
//
//        //Speak ingredients
//        String text = ing.getText().toString();
//
//                        if (text != null && text.length() > 0) {
////                    Toast.makeText(RecipeDetailActivity.this, "Saying: " + text,
////                            Toast.LENGTH_LONG).show();
//                    tts.speak(text, TextToSpeech.QUEUE_ADD, null);
//
//                }


//        try{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ttsGreater21(text);
//            } else {
//                ttsUnder20(text);
//            }
//
//        }catch (RuntimeException ex){
//            Mint.logException(new RuntimeException("App Crash due to TTS API level!!!"));
//
//            throw new RuntimeException("This is a crash");
//        }
//
//
//        //Speak Directions
//
//        text = dir.getText().toString();
//        try{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ttsGreater21(text);
//            } else {
//                ttsUnder20(text);
//            }
//
//        }catch (RuntimeException ex){
//            Mint.logException(new RuntimeException("App Crash due to TTS API level!!!"));
//
//            throw new RuntimeException("This is a crash");
//        }






}


    //------------------------TTS-------------------------------------------------------------------

//    @SuppressWarnings("deprecation")
//    private void ttsUnder20(String text) {
//
//        try{
//            HashMap<String, String> map = new HashMap<>();
//            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
//        }catch (RuntimeException ex){
//            Mint.logException(new RuntimeException("App Crash due to TTS API level under 20!!!"));
//
//            throw new RuntimeException("This is a crash");
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void ttsGreater21(String text) {
//
//        try{
//            String utteranceId=this.hashCode() + "";
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
//        }catch (RuntimeException ex){
//            Mint.logException(new RuntimeException("App Crash due to TTS API level>21!!!"));
//            throw new RuntimeException("This is a crash");
//        }
//    }

    //----------------------------------------------------------------------------------------------

