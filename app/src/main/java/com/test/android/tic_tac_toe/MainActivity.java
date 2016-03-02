package com.test.android.tic_tac_toe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Indicates status of the game
    boolean gameActive=true;
    //0 for 'X' 1 for 'O'
    int currentPlayer=0;
    //2 indicates vacant space
    int [] gameStatus={2,2,2,2,2,2,2,2,2};
    //Possible victory position in a 3x3(Update here only)
    int [][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void onPause()//BGM PROPERTY STARTS
    {
        super.onPause();
        if(m.isPlaying())
            m.pause();
    }
    public  void onResume()
    {
        super.onResume();
        if(m.isPlaying()==false)
            m.start();
    }//BGM PROPERTY ENDS
    public void dropIn(View view) {
        if (gameActive == true) {
            ImageView img = (ImageView) view;
            int tag = Integer.parseInt(img.getTag().toString());
            if (gameStatus[tag] == 2) {
                img.setTranslationY(-2000f);
                gameStatus[tag] = currentPlayer;
                if (currentPlayer == 0) {
                    img.setImageResource(R.drawable.x);
                    currentPlayer = 1;
                } else {
                    img.setImageResource(R.drawable.o);
                    currentPlayer = 0;

                }
                img.animate().translationYBy(2000f).rotation(360).setDuration(300);
            }
            //Victory check;
            boolean win=false;
            for (int[] winningPosition : winningPositions) {
                if (gameStatus[winningPosition[0]] == gameStatus[winningPosition[1]]
                        && gameStatus[winningPosition[1]] == gameStatus[winningPosition[2]]
                        && gameStatus[winningPosition[0]] != 2) {
                    //SOMEONE HAS WON!
                    LinearLayout li=(LinearLayout)findViewById(R.id.playAgainLayout);
                    li.setVisibility(View.VISIBLE);
                    TextView winner=(TextView)findViewById(R.id.winnerText);
                    if(currentPlayer==0)
                        winner.setText("0 WINS!");
                    else
                        winner.setText("X WINS!");
                    gameActive=false;
                    win=true;
                    li.animate().scaleX(0.78f).scaleY(0.7f).translationY(-585f).setDuration(7000);
                }
                else {
                    //Draw Check
                    boolean gameOver = true;
                    for (int i : gameStatus)
                        if (i == 2)
                            gameOver = false;
                    if (gameOver == true && win==false ) {
                        LinearLayout li=(LinearLayout)findViewById(R.id.playAgainLayout);
                        li.setVisibility(View.VISIBLE);
                        TextView winner=(TextView)findViewById(R.id.winnerText);
                        winner.setText("DRAW GAME!");
                        gameActive = false;
                        li.animate().scaleX(0.78f).scaleY(0.7f).translationY(-585f).setDuration(7000);
                    }
                }
            }
        }
    }

    MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        m=MediaPlayer.create(this,R.raw.bgm);
        if(m.isPlaying()==false)
            m.start();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void playAgain(View view)
    {
        LinearLayout li=(LinearLayout)findViewById(R.id.playAgainLayout);
        li.setVisibility(View.VISIBLE);
        currentPlayer=0;
        gameActive=true;
        if(m.isPlaying()==false)
            m.start();
        for(int i=0;i<gameStatus.length;i++)
            gameStatus[i]=2;
        GridLayout gi=(GridLayout)findViewById(R.id.gridLayout);
        for(int i=0;i<gi.getChildCount();i++)
        {
            ((ImageView)gi.getChildAt(i)).setImageResource(0);
        }
        li.setVisibility(View.INVISIBLE);
        li.setTranslationY(320);
        li.animate().scaleX(0.78f).scaleY(0.7f).translationY(-585f).setDuration(7000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
