package khushtar.e.al_hussainy_public_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterTeacherActivity extends AppCompatActivity {

    private EditText edtName,edtUsername,edtEmail,edtAddress,edtPassword,edtRepassword,edtMobile_no;
    private Button btnSubmit;
    private FirebaseAuth mAuth;
    private String mobileno;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        edtName=findViewById(R.id.edtRegistrationName);
        edtUsername=findViewById(R.id.edtRegistrationUsername);
        edtEmail=findViewById(R.id.edtRegistrationEmail);
        edtAddress=findViewById(R.id.edtRegistrationAddress);
        edtPassword=findViewById(R.id.edtRegistrationPassword);
        edtRepassword=findViewById(R.id.edtRegistrationRePassword);
        edtMobile_no=findViewById(R.id.edtRegistrationMobile);

        btnSubmit=findViewById(R.id.btnRegitrationSubmit);
        mAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(RegisterTeacherActivity.this);

        btnSubmit.setOnClickListener((v)->{

            String name,username,email,address,password,repasword;
            name=edtName.getText().toString().trim();
            username=edtUsername.getText().toString().trim();
            email=edtEmail.getText().toString().trim();
            address=edtAddress.getText().toString().trim();
            mobileno=edtMobile_no.getText().toString().trim();
            password=edtPassword.getText().toString().trim();
            repasword=edtRepassword.getText().toString().trim();


            if(TextUtils.isEmpty(name)){
                Toast.makeText(RegisterTeacherActivity.this, "Name required", Toast.LENGTH_SHORT).show();
                edtName.requestFocus();

            }else if(TextUtils.isEmpty(username)){
                Toast.makeText(RegisterTeacherActivity.this, "User Name is required", Toast.LENGTH_SHORT).show();
                edtUsername.requestFocus();
            }else if(TextUtils.isEmpty(email)){
                Toast.makeText(RegisterTeacherActivity.this, "Email required", Toast.LENGTH_SHORT).show();
                edtEmail.requestFocus();

            }else if(TextUtils.isEmpty(address)){
                Toast.makeText(RegisterTeacherActivity.this, "Address required", Toast.LENGTH_SHORT).show();
                edtAddress.requestFocus();
            }else if(TextUtils.isEmpty(mobileno) || mobileno.length()<10 ){
                Toast.makeText(RegisterTeacherActivity.this, "Please Enter valid Mobile no", Toast.LENGTH_SHORT).show();
                edtMobile_no.requestFocus();
            }else if(TextUtils.isEmpty(password)){
                Toast.makeText(RegisterTeacherActivity.this, "Password required", Toast.LENGTH_SHORT).show();
                edtPassword.requestFocus();
            }else if(TextUtils.isEmpty(repasword)) {
                Toast.makeText(RegisterTeacherActivity.this, "Re-password required", Toast.LENGTH_SHORT).show();
                edtRepassword.requestFocus();
            }else if(!password.equals(repasword))
                Toast.makeText(RegisterTeacherActivity.this, "Password not Matched", Toast.LENGTH_SHORT).show();
            else registerUser(name,username,email,address,password,mobileno);
        });
    }


    private void registerUser(String name, String username, String email, String address, String password, String mobileno) {
        pd.setMessage("Please Wait");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {


                    @Override
                    public void onSuccess(AuthResult authResult) {

                            NewTeacher newUser=new NewTeacher();
                            newUser.setName(name);
                            newUser.setUsername(username);
                            newUser.setMobile_no(mobileno);
                            newUser.setEmail(email);
                            newUser.setAddress(address);
                            newUser.setPassword(password);
                            newUser.setMobile_no(mobileno);

                            FirebaseDatabase fdb=FirebaseDatabase.getInstance();

                            fdb.getReference("School Data").child("Teacher").child(username).setValue(newUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                pd.dismiss();
                                                Toast.makeText(RegisterTeacherActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(RegisterTeacherActivity.this,MainActivity.class);

                                                startActivity(intent);
                                                finish();

                                            }
                                            else{
                                                Toast.makeText(RegisterTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterTeacherActivity.this, "User Already Exist", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
        if(currentUser==null){
            //Do something
        }
    }
}



