package anam.com.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginToRecipes extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_to_recipes);

    }


    public void loginClicked(View view)
    {

        String email = (String)((EditText)findViewById(R.id.email)).getText().toString();
        String password = (String)((EditText)findViewById(R.id.password)).getText().toString();
        if(email.equals("")||password.equals(""))
        {
            Toast.makeText(this, "Please Fill Both Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        OfflineDB db = new OfflineDB(this);
        int userId = db.getUserId(email, password);
        Intent intent = new Intent(this,listActivity.class);
        intent.putExtra("type", "myRecipes");
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
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

