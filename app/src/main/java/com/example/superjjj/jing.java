package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.superjjj.util.TicTacToeBoardView;

import java.util.Arrays;

public class jing extends AppCompatActivity {



    private View header;
    private View back;
    private TextView text;
    private TicTacToeBoardView ticTacToeBoard;

    private TextView playerTurn;
    private TextView playerO;  // 这是playerO的胜利次数的文本
    private TextView playerX;  // 这是playerX的胜利次数的文本
    private TextView draw;  // 这是平局次数的文本
    private int player = 0; // 表示此时是哪个玩家 0是圆玩家，1是叉玩家
    private int O = 0;  // playerO的胜利次数
    private int X = 0;  // playerX的胜利次数
    private int D = 0;  // 平局次数
    private Button playAgain;  // 重开按键。点击之后会把所有数据设为初始值并清空画板
    private boolean over;  // 是否结束
    private int status;  // 结束的状态，0是O赢，1是X赢，2是平局



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_jing);

        header = findViewById(R.id.header);

        text = findViewById(R.id.textView);
        text.setText("TICTACTOE");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        playerTurn = findViewById(R.id.playerTurn);
        playerO = findViewById(R.id.playerO);
        playerX = findViewById(R.id.playerX);
        draw = findViewById(R.id.draw);
        playAgain = findViewById(R.id.playButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        ticTacToeBoard.setOnGameStateChangeListener(new TicTacToeBoardView.OnGameStateChangeListener() {
            @Override
            public void onGameStateChange(char[] boardState) {
                // 处理游戏状态改变，比如检查胜利条件
                checkGameState(boardState);
            }
        });

        ticTacToeBoard.setCurrentPlayer(player);  // 初始化此时的玩家是O
        ticTacToeBoard.setOver(false);  // 初始化没over


    }

    private void checkGameState(char[] boardState) {
        // 检查行
        for (int i = 0; i < 9; i += 3) {
            if (boardState[i] != ' ' && boardState[i] == boardState[i + 1] && boardState[i] == boardState[i + 2]) {
                if (i == 0){
                    ticTacToeBoard.linesToDraw[0] = true;
                } else if (i == 3){
                    ticTacToeBoard.linesToDraw[1] = true;
                } else {
                    ticTacToeBoard.linesToDraw[2] = true;
                }

                over = true;
                status = (boardState[i] == 'O') ? 0 : 1;
                updatePlayerWin();
                return;
            }
        }

        // 检查列
        for (int i = 0; i < 3; i++) {
            if (boardState[i] != ' ' && boardState[i] == boardState[i + 3] && boardState[i] == boardState[i + 6]) {
                if (i == 0){
                    ticTacToeBoard.linesToDraw[3] = true;
                } else if (i == 1){
                    ticTacToeBoard.linesToDraw[4] = true;
                } else {
                    ticTacToeBoard.linesToDraw[5] = true;
                }

                over = true;
                status = (boardState[i] == 'O') ? 0 : 1;
                updatePlayerWin();
                return;
            }
        }

        // 检查对角线
        if ((boardState[0] != ' ' && boardState[0] == boardState[4] && boardState[0] == boardState[8]) ||
                (boardState[2] != ' ' && boardState[2] == boardState[4] && boardState[2] == boardState[6])) {
            if (boardState[0] != ' ' && boardState[0] == boardState[4] && boardState[0] == boardState[8]) {
                ticTacToeBoard.linesToDraw[6] = true;
            } else {
                ticTacToeBoard.linesToDraw[7] = true;
            }

            over = true;
            status = (boardState[4] == 'O') ? 0 : 1;
            updatePlayerWin();
            return;
        }

        // 检查平局
        boolean isDraw = true;
        for (char cell : boardState) {
            if (cell == ' ') {  // 表示有没占的格子的时候，意思就是没结束
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            over = true;
            status = 2; // 表示平局
            updatePlayerWin();
        } else {
            switchPlayer();
            updatePlayerTurn();
        }
    }



    @SuppressLint("SetTextI18n")
    private void updatePlayerTurn() {
        playerTurn.setTextColor(Color.parseColor("#808080"));
        if (player == 0) {
            playerTurn.setText("O turn");
        } else {
            playerTurn.setText("X turn");
        }
    }

    private void switchPlayer() {
        player = (player == 0) ? 1 : 0;
        ticTacToeBoard.setCurrentPlayer(player);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void updatePlayerWin() {
        if (status == 0) {
            playerTurn.setTextColor(ContextCompat.getColor(this, R.color.aqua));
            playerTurn.setText("Player 'O' win");
            O++;
            playerO.setText("O: " + O);
        } else if (status == 1){
            playerTurn.setTextColor(ContextCompat.getColor(this, R.color.greenish_yellow));
            playerTurn.setText("Player 'X' win");
            X++;
            playerX.setText("X: " + X);
        } else {
            playerTurn.setTextColor(ContextCompat.getColor(this, R.color.blue_custom));
            playerTurn.setText("Draw");
            D++;
            draw.setText("Draw: " + D);
        }
        ticTacToeBoard.setOver(true);
    }



    private void resetGame() {
        // 重置游戏状态
        player = 0;
        over = false;
        Arrays.fill(ticTacToeBoard.boardState, ' '); // 重置棋盘状态
        ticTacToeBoard.invalidate(); // 重绘 TicTacToeBoardView
        updatePlayerTurn(); // 更新玩家轮次显示
        ticTacToeBoard.setOver(false);
        ticTacToeBoard.setCurrentPlayer(0);
    }

}