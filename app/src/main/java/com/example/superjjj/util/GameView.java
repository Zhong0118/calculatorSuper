package com.example.superjjj.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.superjjj.Game2048Activity;
import com.example.superjjj.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends GridLayout {
    private int mTouchSlop;
    private static final int GRID_SIZE = 4;
    private Card[][] cardsMap = new Card[GRID_SIZE][GRID_SIZE];

    private Card[][] lastCardsMap = new Card[GRID_SIZE][GRID_SIZE]; // 上一次历史记录


    List<Point> emptyPoints = new ArrayList<>(); // 存点

    public int tileSize, tileMargin;
    public int score;
    public int tempScore;


    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initGameView();
    }

    public GameView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initGameView();
    }

    private void initGameView() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        post(new Runnable() {
            @Override
            public void run() {
                int width = getWidth();
                tileMargin = getResources().getDimensionPixelSize(R.dimen.tile_margin);
                int paddingTotal = 2 * getResources().getDimensionPixelSize(R.dimen.padding);
                tileSize = (width - paddingTotal - tileMargin * (GRID_SIZE - 1)) / GRID_SIZE;

                // 初始化卡片
                addCards(tileSize, tileMargin);
                start();
            }
        });

        setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -mTouchSlop) {
                                toLeft();
                            } else if (offsetX > mTouchSlop) {
                                toRight();
                            }
                        } else {
                            if (offsetY < -mTouchSlop) {
                                toUp();
                            } else if (offsetY > mTouchSlop) {
                                toDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }


    public void start() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                cardsMap[y][x].setNum(0);
            }
        }
        addRandomCard();
        addRandomCard();
        score = 0;
        updateScore();
    }

    private void addCards(int tileSize, int tileMargin) {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Card card = new Card(getContext());
                card.setNum(0); // 初始化数字为0,同时不会显示出来数
                addView(card, tileSize, tileSize);

                // 设置边距
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) card.getLayoutParams();
                int halfMargin = tileMargin / 2;
                params.setMargins(halfMargin, halfMargin, halfMargin, halfMargin);
                card.setLayoutParams(params);

                cardsMap[y][x] = card;
            }
        }

    }

    /**
     * 这个方法将遍历每一行，对于行中的每个格子，它将向右看，
     * 并找到第一个非空的格子。如果当前格子为空，它会移动数字。
     * 如果它们的数字相等，它会合并它们。
     * 注意 x-- 的使用，它确保如果一个移动发生在中间，那么后面的格子也会继续检查。
     * x-- 的作用：当执行移动或合并操作后，x-- 会减少 x 的值。
     * 这样做的目的是为了重新检查当前位置 (y, x)。因为在当前迭代中，(y, x) 位置的值已经改变
     * （要么是移动了一个新数字过来，要么是两个数字合并），可能需要再次与后面的数字进行比较。
     * 重新循环的影响：通过减少 x 的值，循环会再次检查更新后的 (y, x) 位置。
     * 这确保了如果通过移动或合并操作更改了当前位置的数字，相同的位置可以与后续的数字再次进行比较，以便于处理连续的合并或移动
     * 如果在任何滑动操作中进行了移动或合并（needAddCard 为 true），那么会添加一个新的随机卡片。
     */
    private void toLeft() {
        saveLastState();

        boolean needAddCard = false;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                for (int x1 = x + 1; x1 < GRID_SIZE; x1++) {
                    if (cardsMap[y][x1].getNum() > 0) {
                        if (cardsMap[y][x].getNum() == 0) {
                            cardsMap[y][x].setNum(cardsMap[y][x1].getNum());
                            cardsMap[y][x1].setNum(0);
                            x--; // After a merge, check this x position again
                            needAddCard = true;
                        } else if (cardsMap[y][x].equal(cardsMap[y][x1])) {
                            cardsMap[y][x].setNum(cardsMap[y][x].getNum() * 2);

                            cardsMap[y][x].setScaleX((float) 1.1);
                            cardsMap[y][x].setScaleY((float) 1.1);
                            cardsMap[y][x].animate().scaleX(1).scaleY(1).setDuration(200).start();


                            score += cardsMap[y][x].getNum() * 2;
                            cardsMap[y][x1].setNum(0);
                            needAddCard = true;

                        }
                        break;
                    }
                }
            }
        }
        if (needAddCard) {
            addRandomCard();
            updateScore();
        }
        if (!canMove()){
            showGameOverDialog();
        }
    }


    private void toRight() {
        saveLastState();

        boolean needAddCard = false;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = GRID_SIZE - 1; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[y][x1].getNum() > 0) {
                        if (cardsMap[y][x].getNum() == 0) {
                            cardsMap[y][x].setNum(cardsMap[y][x1].getNum());
                            cardsMap[y][x1].setNum(0);
                            x++; // Move further to the right
                            needAddCard = true;

                        } else if (cardsMap[y][x].equal(cardsMap[y][x1])) {
                            cardsMap[y][x].setNum(cardsMap[y][x].getNum() * 2);

                            cardsMap[y][x].setScaleX((float) 1.1);
                            cardsMap[y][x].setScaleY((float) 1.1);
                            cardsMap[y][x].animate().scaleX(1).scaleY(1).setDuration(200).start();
                            score += cardsMap[y][x].getNum() * 2;

                            cardsMap[y][x1].setNum(0);
                            needAddCard = true;

                        }
                        break;
                    }
                }
            }
        }

        if (needAddCard) {
            addRandomCard();
            updateScore();
        }
        if (!canMove()){
            showGameOverDialog();
        }
    }


    private void toUp() {
        saveLastState();

        boolean needAddCard = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                for (int y1 = y + 1; y1 < GRID_SIZE; y1++) {
                    if (cardsMap[y1][x].getNum() > 0) {
                        if (cardsMap[y][x].getNum() == 0) {
                            cardsMap[y][x].setNum(cardsMap[y1][x].getNum());
                            cardsMap[y1][x].setNum(0);
                            y--; // Move further up
                            needAddCard = true;

                        } else if (cardsMap[y][x].equal(cardsMap[y1][x])) {
                            cardsMap[y][x].setNum(cardsMap[y][x].getNum() * 2);

                            cardsMap[y][x].setScaleX((float) 1.1);
                            cardsMap[y][x].setScaleY((float) 1.1);
                            cardsMap[y][x].animate().scaleX(1).scaleY(1).setDuration(200).start();
                            score += cardsMap[y][x].getNum() * 2;

                            cardsMap[y1][x].setNum(0);
                            needAddCard = true;

                        }
                        break;
                    }
                }
            }
        }

        if (needAddCard) {
            addRandomCard();
            updateScore();
        }
        if (!canMove()){
            showGameOverDialog();
        }
    }


    private void toDown() {
        saveLastState();

        boolean needAddCard = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = GRID_SIZE - 1; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[y1][x].getNum() > 0) {
                        if (cardsMap[y][x].getNum() == 0) {
                            cardsMap[y][x].setNum(cardsMap[y1][x].getNum());
                            cardsMap[y1][x].setNum(0);
                            y++; // Move further down
                            needAddCard = true;

                        } else if (cardsMap[y][x].equal(cardsMap[y1][x])) {
                            cardsMap[y][x].setNum(cardsMap[y][x].getNum() * 2);

                            cardsMap[y][x].setScaleX((float) 1.1);
                            cardsMap[y][x].setScaleY((float) 1.1);
                            cardsMap[y][x].animate().scaleX(1).scaleY(1).setDuration(200).start();
                            score += cardsMap[y][x].getNum() * 2;

                            cardsMap[y1][x].setNum(0);
                            needAddCard = true;

                        }
                        break;
                    }
                }
            }
        }

        if (needAddCard) {
            addRandomCard();
            updateScore();
        }
        if (!canMove()){
            showGameOverDialog();
        }
    }

    private void updateScore() {
        if (getContext() instanceof Game2048Activity) {
            ((Game2048Activity) getContext()).updateScore(score);
        }
    }


    private void addRandomCard() {
        emptyPoints.clear();
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                // 如果当前位置是0的话就记录下来
                if (cardsMap[y][x].getNum() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        if (emptyPoints.isEmpty()) {
            return; // 没有空位时直接返回
        }
        // 随机得到一个点的位置
        Point randomPoint = emptyPoints.get(new Random().nextInt(emptyPoints.size()));
        cardsMap[randomPoint.y][randomPoint.x].setNum(Math.random() > 0.15 ? 2 : 4); // 85%概率生成2，10%概率生成4
        // 动画
        cardsMap[randomPoint.y][randomPoint.x].setScaleX((float) 0.4);
        cardsMap[randomPoint.y][randomPoint.x].setScaleY((float) 0.4);
        cardsMap[randomPoint.y][randomPoint.x].animate().scaleX(1).scaleY(1).setDuration(300).start();


    }

    private boolean canMove() {
        // 检查是否有空的卡片
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (cardsMap[y][x].getNum() == 0) {
                    return true; // 存在空卡片，可以移动
                }
            }
        }

        // 检查是否有可合并的卡片
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                int num = cardsMap[y][x].getNum();
                if ((x < GRID_SIZE - 1 && num == cardsMap[y][x + 1].getNum()) ||
                        (y < GRID_SIZE - 1 && num == cardsMap[y + 1][x].getNum())) {
                    return true; // 存在可合并的卡片
                }
            }
        }

        // 既没有空卡片也无法合并，游戏结束
        return false;
    }

    private void showGameOverDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over") // 设置对话框标题
                .setMessage("Your score is " + score) // 设置对话框显示的消息
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) getContext()).finish();
                    }
                })
                .setCancelable(false) // 设置对话框不可被用户取消
                .show(); // 显示对话框
    }


    private void saveLastState() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (lastCardsMap[y][x] == null) {
                    lastCardsMap[y][x] = new Card(getContext());
                }
                lastCardsMap[y][x].setNum(cardsMap[y][x].getNum());
            }
        }
        tempScore = score;
    }

    public void undo() {
        boolean canUndo = false; // 增加了一个标志来检查是否可以回退
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (lastCardsMap[y][x] != null && cardsMap[y][x].getNum() != lastCardsMap[y][x].getNum()) {
                    canUndo = true; // 如果存在状态改变，则可以回退
                    cardsMap[y][x].setNum(lastCardsMap[y][x].getNum());
                }
            }
        }
        if (canUndo) {
            score = tempScore; // 恢复分数
            updateScore();
        } else {
            ToastUtil.showShort(getContext(), "you can't back");
        }
    }


    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


}


