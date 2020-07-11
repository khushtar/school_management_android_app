package khushtar.e.al_hussainy_public_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin=findViewById(R.id.btnStartLogin);
        btnRegister=findViewById(R.id.btnStartRegister);

        btnLogin.setOnClickListener((v)->{
            startActivity((new Intent(StartActivity.this,LoginActivity.class)));
            finish();

        });
       // btnRegister.setVisibility(View.INVISIBLE);
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this,RegisterTeacherActivity.class));
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
