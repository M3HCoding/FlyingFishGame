
package dev.zmq.flyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


public class FlyingFishView extends View
{

    private Bitmap fish[] = new Bitmap[2];
    private Bitmap life[] = new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private int fishX =10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth;
    private int canvasHeight;

    private SoundPlayer soundPlayer;

    private boolean action_flag = false;
    private boolean start_flag=false;


    private int yellowX, yellowY, yellowSpeed = 14;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 16;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 18;
    private Paint redPaint = new Paint();
    private int score, lifeCountOfLife;


    Rect backgroundRect=new Rect(0,0, canvasWidth, canvasHeight);

    public FlyingFishView(Context context)
    {
        super(context);

        soundPlayer=new SoundPlayer(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 580;
        score = 0;
        lifeCountOfLife = 4;



    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

        int w = metrics.widthPixels;

        int h = metrics.heightPixels;

        canvas.drawBitmap(backgroundImage, null, new RectF(0, 0, canvasWidth, canvasHeight), null);

        int minFish=fish[0].getHeight();
        int maxFishY = canvasHeight+minFish-fishY+fish[0].getHeight()* 4;

        fishY = fishY -fishSpeed;
        if (fishY < minFish)
        {

            fishY = minFish;

        }

        fishY = fishY +fishSpeed;

        if (fishY > maxFishY)
        {

            fishY = maxFishY;

        }

        fishSpeed = fishSpeed+3;


/*
      //another method moving fish up & down direction

        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

        int w = metrics.widthPixels;

        int h = metrics.heightPixels;

        canvas.drawBitmap(backgroundImage, null, new RectF(0, 0, canvasWidth, canvasHeight), null);

        int minFishX=fishX+fish[0].getHeight()/2;
        int maxFishY = fishY+fish[0].getHeight()/2 * 4;


        if (0 <= minFishX && minFishX <=canvasHeight && fishY<=maxFishY && maxFishY <=fishY+canvasHeight)
        {

               fishY =fishY+fishSpeed;
        }

        fishSpeed = fishSpeed+2;
        //end fish moving up & down condition

        //action_flag event

        if (action_flag)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);

            action_flag = false;
            fishSpeed-=20;
        }
        else
        {

            canvas.drawBitmap(fish[0], fishX, fishY, null);
            fishSpeed+=20;
        }
    */

        if (action_flag) {

            canvas.drawBitmap(fish[1], fishX, fishY, null);

            action_flag = false;



        }
        else
            {

            canvas.drawBitmap(fish[0], fishX, fishY, null);

        }


        yellowX = yellowX - yellowSpeed;

        //updates the score if the ball is collected

        if (hitBallChecker(yellowX, yellowY))
        {

            score = score + 10;

            yellowX = -100;
            soundPlayer.playHitSound();

        }

        if (yellowX < 0)
        {

            yellowX = canvasWidth + 21;

            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFish)) + minFish;

        }

        //increases size of ball

        canvas.drawCircle(yellowX, yellowY, 18, yellowPaint);

        //Green ball

        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY))
        {

            score = score + 20;

            greenX = -100;
            soundPlayer.playHitSound();

        }

        if (greenX < 0)
        {

            greenX = canvasWidth + 21;

            greenY = (int) Math.floor(Math.random() * (maxFishY - minFish)) + minFish;


        }
        canvas.drawCircle(greenX,greenY,19,greenPaint);

        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY))
        {

            redX = -100;
            soundPlayer.playOverSound();

            lifeCountOfLife--;

            if (lifeCountOfLife == 0)
            {

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                gameOverIntent.putExtra("score", score);

                getContext().startActivity(gameOverIntent);

            }

        }

        //increases size of ball


        if (redX < 0)
        {

            redX = canvasWidth + 21;

            redY = (int) Math.floor(Math.random() * (maxFishY - minFish)) + minFish;

        }

        canvas.drawCircle(redX, redY, 21, redPaint);

        canvas.drawText("Score: " + score, 20, 60, scorePaint);


        for (int i = 0; i < 5; i++)
        {

            int x = (int) (580 + life[0].getWidth() * 1.5 * i);

            int y = 18;

            if (i < lifeCountOfLife)
            {

                canvas.drawBitmap(life[0], x, y, null);



            } else

            {

                canvas.drawBitmap(life[1], x, y, scorePaint);

            }

        }

    }

    public boolean hitBallChecker(int x, int y)
    {

        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {

            return true;

        }

        return false;

    }

@Override

public boolean onTouchEvent(MotionEvent event)
{
    if (event.getAction() == MotionEvent.ACTION_DOWN)

    {

        action_flag = true;

        fishSpeed = -22;

    }

    return true;

}

/*
    //Another method touch event

   if(start_flag==false)
   {
    start_flag=true;

   }
   else
   {
       if (event.getAction()==MotionEvent.ACTION_UP)
       {
           action_flag=true;
           fishSpeed-=24;
       }
       else if (event.getAction()==MotionEvent.ACTION_DOWN)
       {
           action_flag=false;
           fishSpeed+=24;
       }
   }

   return  true;
}
*/
    private void updateDifficulty()
    {
        if(score>=100 && score<=200)
        {
            yellowSpeed=10;
            greenSpeed=12;
            redSpeed=14;
        }
        if(score>=200 && score<=300)
        {
            yellowSpeed=12;
            greenSpeed=14;
            redSpeed=16;
        }
        if(score>=300 && score<=400)
        {
            yellowSpeed=14;
            greenSpeed=16;
            redSpeed=18;
        }
        if(score>=400 && score<=500)
        {
            yellowSpeed=18;
            greenSpeed=20;
            redSpeed=22;
        }
        if(score>=500 && score<=600)
        {
            yellowSpeed=19;
            greenSpeed=21;
            redSpeed=23;
        }
        if(score>=600 && score<=700)
        {
            yellowSpeed=20;
            greenSpeed=22;
            redSpeed=24;
        }
        if(score>=700 && score<=800)
        {
            yellowSpeed=21;
            greenSpeed=23;
            redSpeed=25;
        }
        if(score>=800 && score<=900)
        {
            yellowSpeed=22;
            greenSpeed=24;
            redSpeed=26;
        }
        if(score>=900 && score<=1000)
        {
            yellowSpeed=23;
            greenSpeed=25;
            redSpeed=27;
        }
        if(score>=1000 && score<=1100)
        {
            yellowSpeed=24;
            greenSpeed=26;
            redSpeed=28;
        }
        if(score>=1100 && score<=1200)
        {
            yellowSpeed=25;
            greenSpeed=27;
            redSpeed=28;
        }
        if(score>=1200 && score<=1300)
        {
            yellowSpeed=26;
            greenSpeed=28;
            redSpeed=30;
        }
        if(score>=1300 && score<=1400)
        {
            yellowSpeed=27;
            greenSpeed=29;
            redSpeed=31;
        }
        if(score>=1400 && score<=1500)
        {
            yellowSpeed=28;
            greenSpeed=30;
            redSpeed=32;
        }
        if(score>=1500 && score<=1600)
        {
            yellowSpeed=29;
            greenSpeed=31;
            redSpeed=33;
        }
        if(score>=1600 && score<=1700)
        {
            yellowSpeed=30;
            greenSpeed=32;
            redSpeed=34;
        }
        if(score>=1700 && score<=1800)
        {
            yellowSpeed=32;
            greenSpeed=34;
            redSpeed=36;
        }
        if(score>=1800 && score<=1900)
        {
            yellowSpeed=33;
            greenSpeed=35;
            redSpeed=37;
        }
        if(score>=1900 && score<=2000)
        {
            yellowSpeed=34;
            greenSpeed=36;
            redSpeed=38;
        }
        if(score>=2000 && score<=2100)
        {
            yellowSpeed=35;
            greenSpeed=37;
            redSpeed=39;
        }
        if(score>=2100 && score<=2200)
        {
            yellowSpeed=36;
            greenSpeed=38;
            redSpeed=40;
        }
        if(score>=2200 && score<=2400)
        {
            yellowSpeed=38;
            greenSpeed=40;
            redSpeed=42;
        }
        if(score>=2400 && score<=2800)
        {
            yellowSpeed=40;
            greenSpeed=42;
            redSpeed=44;
        }
        if(score>=2800 && score<=3200)
        {
            yellowSpeed=42;
            greenSpeed=44;
            redSpeed=48;
        }
        if(score>=3200 && score<=3600)
        {
            yellowSpeed=44;
            greenSpeed=46;
            redSpeed=48;
        }
        if(score>=3600 && score<=4000)
        {
            yellowSpeed=46;
            greenSpeed=48;
            redSpeed=50;
        }
        if(score > 4000)
        {
            yellowSpeed=48;
            greenSpeed=50;
            redSpeed=52;
        }
    }

}
