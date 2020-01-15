package dev.zmq.flyingfishgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity

{
    private Button StartGame;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StartGame=(Button)findViewById(R.id.btnPlay);

        StartGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent
                        (SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

/*
        Thread thread = new Thread()
        {

            public void run()
            {

                try
                {
                    sleep(4000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent intent=new Intent
                            (SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
@Override
    protected void onPause()
{
    super.onPause();
    finish();*/
}

}
