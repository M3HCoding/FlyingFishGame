package dev.zmq.flyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;


public class GameOverActivity extends AppCompatActivity
{
    private Button PlayAgain;
    private Button YourScore;
    private Button Exit;
    private Button HighScore;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_game_over);

        score=getIntent().getExtras().getInt("score",0);

        PlayAgain=(Button) findViewById(R.id.btnPlayAgain);
        Exit=(Button) findViewById(R.id.btnExit);
        YourScore= (Button) findViewById(R.id.btnScore);

        HighScore=(Button)findViewById(R.id.btn_High_Score);



        PlayAgain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent
                        (GameOverActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View v)
                {
                finish();
                //System.exit(0);
                }

        });

        YourScore.setText("score :" +score);


        YourScore.setText("Your Score:"+score);

        SharedPreferences setting=getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE);
        int highScore=setting.getInt("HIGH_SCORE",0);
        if (score>highScore)
        {
            HighScore.setText("High Score:"+score);

            //sace score

            SharedPreferences.Editor editor=setting.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }else
        {
            HighScore.setText("High Score:"+highScore);
        }

    }


}
