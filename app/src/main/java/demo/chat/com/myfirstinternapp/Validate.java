package demo.chat.com.myfirstinternapp;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by engineering on 13.06.2016.
 */
public class Validate {

    public static boolean isEmptyEmail(EditText edit_uname) {
        return edit_uname.getText() == null || edit_uname.getText().toString() == null || edit_uname.getText().toString().isEmpty();

    }
    public static boolean isEmptyPassword(EditText edit_pass) {
        return edit_pass.getText() == null || edit_pass.getText().toString() == null || edit_pass.getText().toString().isEmpty();

    }
    public static void displayErrorSnackMessage(View view,String message)
    {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Kapat", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }

                );
        snackbar.setActionTextColor(Color.RED);
        view = snackbar.getView();
        TextView snbTv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        snbTv.setTextColor(Color.YELLOW);
        snackbar.show();

    }
    public static void displaySuccessSnackMessage(View view,String message)
    {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Kapat", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }

                );
        snackbar.setActionTextColor(Color.YELLOW);
        view = snackbar.getView();
        TextView snbTv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        snbTv.setTextColor(Color.GREEN);
        snackbar.show();

    }
}
