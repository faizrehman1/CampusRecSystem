package com.example.faizrehman.campus_recruitment_system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainThread";
    private EditText email,pass;
    private Button loginbtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private String checkuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        checkuser = getIntent().getStringExtra("TAG");

    email = (EditText)findViewById(R.id.editText_Loginemail);
        pass = (EditText)findViewById(R.id.editText_loginpass);
        loginbtn = (Button)findViewById(R.id.login_btn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString();
                String passo = pass.getText().toString();



                if (emails.length() == 0) {
                    email.setError("This is Required Field");
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    pass.setError("This is Required Field");
                }

                if (email.getText().toString().matches("faiz") && pass.getText().toString().matches("faiz")) {
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Sign In", "Connecting...", true, false);

                        mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                    checkUser(task.getResult().getUser().getUid());
                                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                } else if (!task.isSuccessful()) {
                                    AppLogs.logw("signInWithEmail" + task.getException());
                                    Toast.makeText(LoginActivity.this, "" + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                }
                            }
                        }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignin", e.getMessage());
                            }
                        });


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                AppLogs.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                checkUser(user.getUid().toString());

            } else {
                // User is signed out
                AppLogs.d(TAG, "onAuthStateChanged:signed_out");
            }
        }
    };

    }

    public void checkUser(final String uid){
        if(checkuser.matches("Company")) {
            firebase.child("Company").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        if (dataSnapshot.hasChild(uid)) {
                            
                        } else {
                            Toast.makeText(LoginActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else if(checkuser.matches("Student")){
            firebase.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        if (dataSnapshot.hasChild(uid)) {
                            Intent intent = new Intent(LoginActivity.this, Student_Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}