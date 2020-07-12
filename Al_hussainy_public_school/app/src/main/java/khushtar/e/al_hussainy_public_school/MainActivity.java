package khushtar.e.al_hussainy_public_school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import khushtar.e.al_hussainy_public_school.Fragments.AddFragment;
import khushtar.e.al_hussainy_public_school.Fragments.HomeFragment;
import khushtar.e.al_hussainy_public_school.Fragments.RemoveFragment;
import khushtar.e.al_hussainy_public_school.Fragments.SearchFragment;
import khushtar.e.al_hussainy_public_school.Fragments.UpdateFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
      private Button btnLogout;
      FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        btnLogout=findViewById(R.id.log_out);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener((item)->{

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectorFragment=new HomeFragment();
                    break;
                case R.id.nav_add:
                    selectorFragment=new AddFragment();
                    break;
                case R.id.nav_update:
                    selectorFragment=new UpdateFragment();
                    break;
                case R.id.nav_search:
                    selectorFragment=new SearchFragment();
                    break;
                case R.id.nav_remove:
                    selectorFragment=new RemoveFragment();
                    break;
            }
            if(selectorFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
            }

            return true;
        });
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

           btnLogout.setOnClickListener((view)->{

               mAuth.signOut();
               startActivity(new Intent(MainActivity.this,LoginActivity.class));
               finish();
           });
    }



}
