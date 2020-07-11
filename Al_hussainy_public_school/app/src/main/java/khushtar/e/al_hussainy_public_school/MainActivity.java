package khushtar.e.al_hussainy_public_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private TextView txtUserid,txtEmail,txtDisplay,txtOther;
    private Button btnLogout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserid=findViewById(R.id.txt_user_id);
        txtEmail=findViewById(R.id.txt_user_email);
        txtDisplay=findViewById(R.id.txt_user_display);
        txtOther=findViewById(R.id.txt_user_other);
        btnLogout=findViewById(R.id.logout);
       // String usrid= getIntent().getStringExtra("currentUser").toString();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        //FirebaseDatabase database=new FirebaseDatabase();
        txtUserid.setText("user id: " + currentUser.getUid());
        txtEmail.setText("Email: " + currentUser.getEmail());
        txtDisplay.setText("Display Name: " + currentUser.getDisplayName());
        txtOther.setText("Mob: "+ currentUser.getPhoneNumber());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

}
