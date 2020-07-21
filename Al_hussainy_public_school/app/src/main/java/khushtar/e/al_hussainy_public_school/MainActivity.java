package khushtar.e.al_hussainy_public_school;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import khushtar.e.al_hussainy_public_school.Fragments.AddFragment;
import khushtar.e.al_hussainy_public_school.Fragments.HomeFragment;
import khushtar.e.al_hussainy_public_school.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    private Toolbar toolbar;
      FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.top_nav_about:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("About us")
                            .setMessage(R.string.about_us).setNeutralButton(android.R.string.ok,null).show();

                    break;
                case R.id.top_nav_exit:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Exit")
                            .setMessage("Do you want to exit?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                mAuth.signOut();
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                finish();
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    break;
            }

            return false;
        });

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener((item)->{
            switch (item.getItemId()){
                case R.id.nav_home:
                    selectorFragment=new HomeFragment();
                    break;
                case R.id.nav_add:
                    selectorFragment=new AddFragment();
                    break;
                case R.id.nav_logout:
                    logout();
                    break;
                case R.id.nav_search:
                    selectorFragment=new SearchFragment();
                    break;
            }
            if(selectorFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
            }

            return true;
        });
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();



    }

    private void logout() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("Do you want to exit?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    mAuth.signOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
      protected void onStart() {
          super.onStart();
            if(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals("FlYHX5eSKmb6NPfQHUVqtIDSbqj1")){
                Menu menu=bottomNavigationView.getMenu();
                menu.findItem(R.id.nav_add).setVisible(true);
            }
    }
}
