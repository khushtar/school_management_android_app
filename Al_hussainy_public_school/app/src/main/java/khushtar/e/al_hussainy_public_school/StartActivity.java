package khushtar.e.al_hussainy_public_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

         Button btnLogin,btnRegister;

        btnLogin=findViewById(R.id.btnStartLogin);
        btnRegister=findViewById(R.id.btnStartRegister);

        btnLogin.setOnClickListener((v)->{
            startActivity((new Intent(StartActivity.this,LoginActivity.class)));
            finish();

        });
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            finish();
        });
    }
    @Override
    protected void onStart(){
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent( StartActivity.this, MainActivity.class));
            finish();
        }
    }
}
