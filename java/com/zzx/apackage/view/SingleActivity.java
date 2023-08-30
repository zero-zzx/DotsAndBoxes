package com.zzx.apackage.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzx.apackage.R;

import com.zzx.apackage.model.AIPlayer;
import com.zzx.apackage.model.HumanPlayer;
import com.zzx.apackage.model.Player;

public class SingleActivity extends DoubleActivity {
    private AIPlayer computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText("电脑");
        player2name.setText("Computer");
        ImageView player2picture = (ImageView) findViewById(R.id.player2picture);
        player2picture.setImageResource(R.mipmap.computer);

        showChooseAIDifficultyDialog();
    }

    @Override
    protected Player[] initPlayers() {
        computer = new AIPlayer("电脑");
        return new Player[]{new HumanPlayer("玩家一"), computer};
    }

    @Override
    protected void showChooseGameSizeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("点线棋")
                .setMessage("请选择棋盘大小")
                .setPositiveButton("3 x 3",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                gameView.setGameSize(3);
                                computer.setGameSize(3);
                            }
                        })
                .setNegativeButton( "5 x 5",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                gameView.setGameSize(5);
                                computer.setGameSize(5);
                            }
                        }).show();
    }

    private void showChooseAIDifficultyDialog() {
        new AlertDialog.Builder(this)
                .setTitle("点线棋")
                .setMessage("选择难度")
                .setPositiveButton("普通",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                computer.setDifficulty(1);
                            }
                        })
                .setNeutralButton("困难", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        computer.setDifficulty(2);
                    }
                })
                .setNegativeButton("地狱",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                computer.setDifficulty(3);
                            }
                        }).show();
    }

}
