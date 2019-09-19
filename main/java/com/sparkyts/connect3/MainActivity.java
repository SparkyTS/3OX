package com.sparkyts.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    enum Player {
        RED, YELLOW, UNKNOWN
    }

    Player currentPlayer = Player.YELLOW;
    Player[] gameState = {Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN,Player.UNKNOWN};

    boolean gameIsActive = true;
    int count = 0;

    int[][]winningPositions = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    public void tossInPlayer(View view, Player player){
        ImageView toss = (ImageView) view;
        toss.setTranslationY(-400f);
        toss.animate().translationYBy(400f).rotationXBy(360f).setDuration(200);
        toss.setImageResource(player == Player.RED ? R.drawable.red : R.drawable.yellow);
    }

    public void showResult(boolean render, String msg){
        LinearLayout linearLayout =  findViewById(R.id.playAgainScreen);
        if(!render)
            linearLayout.setVisibility(View.INVISIBLE);
        else{
            linearLayout.setVisibility(View.VISIBLE);
            TextView result = findViewById(R.id.result);
            result.setText(msg);
        }
    }

    public void dropIn(View view){

        int index = Integer.parseInt(view.getTag().toString());

        //if place is already filled
        if(gameState[index]!=Player.UNKNOWN || !gameIsActive)
            return;

        count++;
        //changing control to the other player
        if(currentPlayer==Player.YELLOW){
            tossInPlayer(view, Player.RED);
            gameState[index] = currentPlayer = Player.RED;
        }
        else{
            tossInPlayer(view, Player.YELLOW);
            gameState[index] = currentPlayer = Player.YELLOW;
        }

        for(int[] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]]!=Player.UNKNOWN){

                gameIsActive = false;
                showResult(true, currentPlayer + " Wins !");
                return;
            }
        }

        if(gameIsActive && count==9)
            showResult(true, "DRAW");
    }

    public void playAgain(View view){
        showResult(false,"");
        count = 0;
        for(int i = 0 ; i < gameState.length ; i++)
            gameState[i] = Player.UNKNOWN;
        gameIsActive = true;

        GridLayout gridLayout = findViewById(R.id.tossGrid);
        for(int i = 0 ; i < gridLayout.getChildCount() ; i++)
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}