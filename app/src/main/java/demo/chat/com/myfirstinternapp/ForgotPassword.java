package demo.chat.com.myfirstinternapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

/**
 * Created by engineering on 13.06.2016.
 */
public class ForgotPassword extends AppCompatActivity{
    EditText edit_uname;
     private TextInputLayout mEmailLayout;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_forgot);
         mEmailLayout = (TextInputLayout) findViewById(R.id.email_layoutP);
         edit_uname = (EditText) findViewById(R.id.input_unameP);

         final ActionProcessButton btnProcess = (ActionProcessButton) findViewById(R.id.btnProcessP);
         btnProcess.setMode(ActionProcessButton.Mode.ENDLESS);

         btnProcess.setProgress(25);
          btnProcess.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  InputMethodManager im=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                  im.hideSoftInputFromWindow(edit_uname.getWindowToken(),0);
                  if (Validate.isEmptyEmail(edit_uname))
                  {
                      Validate.displayErrorSnackMessage(v,"Email adresini girmelisiniz!");

                  }else{

                      ActionProcessButton btn = (ActionProcessButton) v;

                      btn.setProgress(100);
                      Validate.displaySuccessSnackMessage(v,"Emailinizi kontrol ediniz!");

                  }
              }
          });
     }


}

