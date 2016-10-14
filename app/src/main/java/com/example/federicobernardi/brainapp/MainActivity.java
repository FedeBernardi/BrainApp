package com.example.federicobernardi.brainapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView timerText, operationText;
    long maxTime;
    int totalActualOperation, totalOperations, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalOperations = score = 0;
        operationText = (TextView)findViewById(R.id.operationText);
        timerText = (TextView)findViewById(R.id.timer);
        this.maxTime = 30000;

        this.generateOperation();
        this.countDownTimer = new CountDownTimer(this.maxTime + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerText.setText("0");
                GridLayout grid = (GridLayout)findViewById(R.id.gridButtons);
                disableButtons(grid);

                //TODO: Behavior when finishes.
            }
        }.start();
        //TODO: Change start
        // Maybe, later, when the user achieves certain score, the thing levels up, restarting
        // the game
    }

    private void generateOperation() {
        String sign;
        int firstNumber, secondNumber;

        firstNumber = this.randomWithRange(0, 100);
        secondNumber = this.randomWithRange(0, 100);

        //Random operation
        //In the future could be a switch
        //
        //Between 0 - 4 = subtraction
        //        5 - 9 = addition
        if (this.randomWithRange(0, 10) > 5) {
            Log.i("operation", "addition");
            sign = " + ";
            this.totalActualOperation = firstNumber + secondNumber;
        } else {
            Log.i("operation", "subtraction");
            sign = " - ";
            this.totalActualOperation = firstNumber - secondNumber;
        }

        operationText.setText(
            Integer.toString(firstNumber) +
            sign +
            Integer.toString(secondNumber)
        );

        this.generateOption();

    }

    //It creates the random options for the operation, also set te correct one.
    private void generateOption() {
        int button1 = 0, button2 = 0, button3 = 0, button4 = 0, total = this.totalActualOperation;
        TextView button1Text = (TextView)findViewById(R.id.button1),
                 button2Text = (TextView)findViewById(R.id.button2),
                 button3Text = (TextView)findViewById(R.id.button3),
                 button4Text = (TextView)findViewById(R.id.button4);


        switch (this.randomWithRange(1, 4)) {
            case 1:
                button1 = total;
                button2 = randomWithRange(total - 20, total + 20);
                button3 = randomWithRange(total - 20, total + 20);
                button4 = randomWithRange(total - 20, total + 20);
                break;
            case 2:
                button2 = total;
                button1 = randomWithRange(total - 20, total + 20);
                button3 = randomWithRange(total - 20, total + 20);
                button4 = randomWithRange(total - 20, total + 20);
                break;
            case 3:
                button3 = total;
                button2 = randomWithRange(total - 20, total + 20);
                button1 = randomWithRange(total - 20, total + 20);
                button4 = randomWithRange(total - 20, total + 20);
                break;
            case 4:
                button4 = total;
                button2 = randomWithRange(total - 20, total + 20);
                button3 = randomWithRange(total - 20, total + 20);
                button1 = randomWithRange(total - 20, total + 20);
                break;
        }

        button1Text.setText(Integer.toString(button1));
        button1Text.setTag(button1);
        button2Text.setText(Integer.toString(button2));
        button2Text.setTag(button2);
        button3Text.setText(Integer.toString(button3));
        button3Text.setTag(button3);
        button4Text.setText(Integer.toString(button4));
        button4Text.setTag(button4);
    }

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public void optionClicked(View view) {
        TextView scoreBoard = (TextView)findViewById(R.id.score);
        Button tappedButton = (Button)view;

        if ((int)tappedButton.getTag() == this.totalActualOperation) {
            this.score ++;
        };
        this.totalOperations ++;

        //Update the score on the view.
        scoreBoard.setText(
                Integer.toString(this.score) +
                        " / " +
                        Integer.toString(this.totalOperations)
        );

        this.generateOperation();
    }

    private void disableButtons(GridLayout layout) {

        // Get all touchable views
        ArrayList<View> layoutButtons = layout.getTouchables();

        // loop through them, if they are an instance of Button, disable it.
        for(View v : layoutButtons){
            if( v instanceof Button ) {
                ((Button)v).setEnabled(false);
            }
        }
    }

}






























