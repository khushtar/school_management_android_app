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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName,edtUsername,edtEmail,edtAddress,edtPassword,edtRepassword,edtMobile_no;
    private Button btnSubmit;
    private FirebaseAuth mAuth;
    private String mobileno;
    private ProgressDialog pd;
    private TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signIn=findViewById(R.id.register_signIn);
        edtName=findViewById(R.id.edtRegistrationName);
        edtUsername=findViewById(R.id.edtRegistrationUsername);
        edtEmail=findViewById(R.id.edtRegistrationEmail);
        edtAddress=findViewById(R.id.edtRegistrationAddress);
        edtPassword=findViewById(R.id.edtRegistrationPassword);
        edtRepassword=findViewById(R.id.edtRegistrationRePassword);
        edtMobile_no=findViewById(R.id.edtRegistrationMobile);

        btnSubmit=findViewById(R.id.btnRegitrationSubmit);
        mAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(RegisterActivity.this);

        signIn.setOnClickListener((v)->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        });



        btnSubmit.setOnClickListener((v)->{

            String name,username,email,address,password,rePassword;
            name=edtName.getText().toString().trim();
            username=edtUsername.getText().toString().trim();
            email=edtEmail.getText().toString().trim();
            address=edtAddress.getText().toString().trim();
            mobileno=edtMobile_no.getText().toString().trim();
            password=edtPassword.getText().toString().trim();
            rePassword=edtRepassword.getText().toString().trim();


            if(TextUtils.isEmpty(name)){
                Toast.makeText(RegisterActivity.this, "Name required", Toast.LENGTH_SHORT).show();
                edtName.requestFocus();

            }else if(TextUtils.isEmpty(username)){
                Toast.makeText(RegisterActivity.this, "User Name is required", Toast.LENGTH_SHORT).show();
                edtUsername.requestFocus();
            }else if(TextUtils.isEmpty(email)){
                Toast.makeText(RegisterActivity.this, "Email required", Toast.LENGTH_SHORT).show();
                edtEmail.requestFocus();

            }else if(TextUtils.isEmpty(address)){
                Toast.makeText(RegisterActivity.this, "Address required", Toast.LENGTH_SHORT).show();
                edtAddress.requestFocus();
            }else if(TextUtils.isEmpty(mobileno) || mobileno.length()<10 ){
                Toast.makeText(RegisterActivity.this, "Please Enter valid Mobile no", Toast.LENGTH_SHORT).show();
                edtMobile_no.requestFocus();
            }else if(TextUtils.isEmpty(password)){
                Toast.makeText(RegisterActivity.this, "Password required", Toast.LENGTH_SHORT).show();
                edtPassword.requestFocus();
            }else if(TextUtils.isEmpty(password)|| password.length()<6) {
                Toast.makeText(RegisterActivity.this, "password required and it should be at least 6 digit", Toast.LENGTH_SHORT).show();
                edtPassword.requestFocus();
            }else if(!password.equals(rePassword) ){
                Toast.makeText(RegisterActivity.this, "Re-Password not Matched", Toast.LENGTH_SHORT).show();
                edtRepassword.requestFocus();
            }
            else {
                registerUser(name,username,email,address,password,mobileno);
            }
        });
    }


    private void registerUser(String name, String username, String email, String address, String password, String mobileno) {
        pd.setMessage("Please Wait");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                        Map<String,String> newUser=new HashMap<>();
                        newUser.put("name",name);
                        newUser.put("username",username);
                        newUser.put("mobile_no",mobileno);
                        newUser.put("email",email);
                        newUser.put("address",address);
                        newUser.put("password",password);

                        FirebaseDatabase fdb=FirebaseDatabase.getInstance();
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        String uid=user.getUid();

                        fdb.getReference("School Data").child("User").child(uid).setValue(newUser)
                                .addOnCompleteListener(task -> {

                                    if(task.isSuccessful()) {
                                        pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }).addOnFailureListener(e -> {
                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                    });

    }



}



