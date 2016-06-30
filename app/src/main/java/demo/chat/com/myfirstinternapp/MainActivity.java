package demo.chat.com.myfirstinternapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import demo.chat.com.myfirstinternapp.Validate;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.dd.processbutton.iml.GenerateProcessButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    EditText edit_uname;
    EditText edit_pass;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    TextView txtForgotPassword;
    private static Handler handler;
   ActionProcessButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        //Edit Text
        edit_uname = (EditText) findViewById(R.id.input_uname);
        edit_pass = (EditText) findViewById(R.id.input_password);
        txtForgotPassword=(TextView)findViewById(R.id.textViewForgot);
        handler = new Handler();

        final ActionProcessButton btnProcess = (ActionProcessButton) findViewById(R.id.btnProcess);
        btnProcess.setMode(ActionProcessButton.Mode.ENDLESS);

         btnProcess.setProgress(25);


                btnProcess.setOnClickListener(new View.OnClickListener()

                {

                        @Override
                        public void onClick (View view){
                            InputMethodManager im=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            im.hideSoftInputFromWindow(edit_uname.getWindowToken(),0);
                        if (Validate.isEmptyEmail(edit_uname)) {
                            Validate.displayErrorSnackMessage(view,"Email adresini girmelisiniz!");

                        } else if (Validate.isEmptyPassword(edit_pass)) {

                            Validate.displayErrorSnackMessage(view,"Sifrenizi girmelisiniz!");

                        } else {

                            ActionProcessButton btn = (ActionProcessButton) view;

                                btn.setProgress(100);
                            Validate.displaySuccessSnackMessage(view,"Giris Basarılı!");
                            startActivity(new Intent(MainActivity.this,Home.class));

                        }
                    }


                }


            );


        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));

            }
        });

    }





}
