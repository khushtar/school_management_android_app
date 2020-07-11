package khushtar.e.al_hussainy_public_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUserEmail,edtPassword;
    Button btnSubmit;
    private FirebaseAuth mAuth;

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

        btnSubmit.setOnClickListener((view)->{
            String email,password;
            email=edtUserEmail.getText().toString().trim();
            password=edtPassword.getText().toString().trim();
            verifyUser(email,password);

        });

    }

    private void verifyUser(String email, String password) {
        pd.setMessage("Please Wait");
        pd.show();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("currentUser",user);
                            pd.dismiss();
                            startActivity(intent);
                           // updateUI(user);
                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }


}
