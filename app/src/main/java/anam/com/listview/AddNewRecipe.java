package anam.com.listview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewRecipe extends AppCompatActivity {

    int userId=0;
    Bitmap bitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);
        setTitle(Html.fromHtml("<b>Add New Recipe</b>"));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        }

    public void addClicked(View view)
    {
        String name = (String)((EditText)findViewById(R.id.recipeName)).getText().toString();
        String ing = (String)((EditText)findViewById(R.id.ingridients)).getText().toString();
        String dir = (String)((EditText)findViewById(R.id.direction)).getText().toString();

        if(name.equals("")||ing.equals("")||dir.equals(""))
        {
            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        OfflineDB db = new OfflineDB(this);
        int imgId = db.addNewRecipe(name, ing, dir, userId);

        if(bitmap!=null) {
            ImageStorage.saveToSdCard(bitmap, "user"+imgId);
        }
        Toast.makeText(this, "Recipe Added Successfully", Toast.LENGTH_LONG).show();
        finish();
        /*Intent intent = new Intent(this, listActivity.class);
        intent.putExtra("type", "myRecipes");
        intent.putExtra("userId", userId);
        startActivity(intent);*/

    }

    public  void takePicClicked(View v)
    {
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK){
             bitmap = (Bitmap)data.getExtras().get("data");

            ImageView imageHolder = (ImageView)findViewById(R.id.capPic);

            imageHolder.setImageBitmap(bitmap);

          }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.home:
                Intent i = new Intent(this,MainMenu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            case R.id.about:
                Intent i1 = new Intent(this,AboutActivity.class);
                startActivity(i1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
