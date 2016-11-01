package com.google.engedu.ghost;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Button chabtn,rebtn;
    TextView gStatus,gText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        try {
            InputStream is = getAssets().open("words.txt");
            dictionary = new FastDictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gText = (TextView) findViewById(R.id.ghostText);
        gText.setText("");
         gStatus = (TextView) findViewById(R.id.gameStatus);

        rebtn=(Button)findViewById(R.id.button2);
         chabtn=(Button)findViewById(R.id.button1);
        onStart(null);




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


public static final String ALPHABET="abcdefghijklmnopqrstuvwxyz";
    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again

        String userText=(String)gText.getText();
        if(userText.length()>=4 &&  dictionary.isWord(userText))
        {
            gStatus.setText("Computer wins!click restart");

        }
        else {
            String uword = dictionary.getAnyWordStartingWith(userText);
            if (uword == null) {
                gStatus.setText("Computer wins!Click restart");

            } else {

               String cword=uword.substring(0, userText.length()+1);
                gText.setText(cword);
                userTurn = true;
                label.setText(USER_TURN);
            }


        }
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */


    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
    TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
       TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char k=(char)event.getUnicodeChar();
        if ( Character.isLetter(k)) {
            String s= (String) gText.getText();
            s=s+k;
            gText.setText(s);
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Ghost Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.google.engedu.ghost/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Ghost Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.google.engedu.ghost/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void restart(View v)
    {
        onStart(rebtn);
    }

    public void challenge(View v)
    {

        String userText=(String)gText.getText();
        if(userText.length()>=4 &&  dictionary.isWord(userText))
        {
            gStatus.setText("User wins!Click restart");

        }
        else {
            String uword = dictionary.getAnyWordStartingWith(userText);
            if (uword == null) {
                gStatus.setText("User wins!Click restart");

            } else {

                gStatus.setText("Computer Wins!Click restart");

                gText.setText(uword);

            }


        }

    }
}
