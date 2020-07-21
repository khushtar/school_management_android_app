package khushtar.e.al_hussainy_public_school;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUserEmail,edtPassword;
    Button btnSubmit;
    private FirebaseAuth mAuth;
    private TextView singUp;
    private ProgressDialog pd;
    protected static String MSG="khustar.e.ahps. UserName and Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserEmail=findViewById(R.id.edtLoginUserEmail);
        edtPassword=findViewById(R.id.edtLoginPassword);
        btnSubmit=findViewById(R.id.btnLoginSubmit);
        pd=new ProgressDialog(LoginActivity.this);
        singUp=findViewById(R.id.login_signUp);

        singUp.setOnClickListener((v)->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });


        btnSubmit.setOnClickListener((view)->{
            String email,password;
            email=edtUserEmail.getText().toString().trim();
            password=edtPassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(LoginActivity.this, "Email required", Toast.LENGTH_SHORT).show();
                edtUserEmail.requestFocus();

            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(LoginActivity.this, "Password required", Toast.LENGTH_SHORT).show();
                edtPassword.requestFocus();
            }
            else {
                verifyUser(email, password);
            }
        });

    }

    private void verifyUser(String email, String password) {
        pd.setMessage("Please Wait");
        pd.show();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("currentUser",user);
                        pd.dismiss();
                        startActivity(intent);

                    } else {
                        pd.dismiss();

                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }


                });
    }


}
