package com.zzx.apackage.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzx.apackage.R;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Random;

import com.zzx.apackage.model.HumanPlayer;
import com.zzx.apackage.model.Player;

public class DoubleActivity extends Activity implements PlayersStateView {

    private static DoubleActivity myself;
    private static int mp[]={R.raw.mangzhong,R.raw.qianbenying,R.raw.reai105dudeni};
    private static int music_num=3;
    protected GameView gameView;
    TextView player1state, player2state, player1occupying, player2occupying;
    ImageView playerNowPointer;
    boolean music, touchSoundOn;
    SoundPool soundpool;
    MediaPlayer mediaPlayer;
    Player[] players;
    Integer[] playersOccupying = new Integer[]{0, 0};
    Player playerNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //加载音乐
        Random random=new Random();
        int idx=((random.nextInt()%music_num)+music_num)%music_num;
        mediaPlayer = MediaPlayer.create(this, mp[idx]);
        soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundpool.load(this, R.raw.ben, BIND_ADJUST_WITH_ACTIVITY);
        try {
            FileInputStream fis = openFileInput("sound");
            DataInputStream dis = new DataInputStream(fis);
            music = dis.readBoolean();
            touchSoundOn = dis.readBoolean();
            dis.close();
        } catch (Exception e) {
            music = true;
            touchSoundOn = true;
        }
        if (music) {
            mediaPlayer.start();
        }


        myself = this;
        int[] mover = new int[1];

        //退出和刷新按钮
        ImageButton returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
        ImageButton refresh = (ImageButton) findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showRefreshDialog();
            }
        });

        player1state = (TextView) findViewById(R.id.player1state);
        player2state = (TextView) findViewById(R.id.player2state);
        player1occupying = (TextView) findViewById(R.id.player1occupying);
        player2occupying = (TextView) findViewById(R.id.player2occupying);
        playerNowPointer = (ImageView) findViewById(R.id.playerNowPointer);

        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setPlayersState(this);

        players = initPlayers();

        showChooseFirstMoverDialog();
        showChooseGameSizeDialog();

    }

    public void updateState() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (playerNow == players[0]) {
                    player1state.setText("思考中");
                    player2state.setText("等待中");
                    playerNowPointer.setImageResource(R.mipmap.a1);
                } else {
                    player2state.setText("思考中");
                    player1state.setText("等待中");
                    playerNowPointer.setImageResource(R.mipmap.a2);
                }
                player1occupying.setText("得分 " + playersOccupying[0]);
                player2occupying.setText("得分 " + playersOccupying[1]);
            }
        });
    }


//手机按下返回键时，弹出弹窗
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private void showExitDialog() {
        new AlertDialog.Builder(myself)
                .setTitle("点线棋")
                .setMessage(
                        "你想退出码?\n你将会失去现存的游戏.")
                .setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                            }
                        })
                .setNegativeButton("否",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    private void showRefreshDialog() {
        new AlertDialog.Builder(myself)
                .setTitle("点线棋")
                .setMessage(
                        "你想清除棋盘吗？\n你将不会缓存这个游戏")
                .setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                recreate();
                            }
                        })
                .setNegativeButton("否",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    protected Player[] initPlayers() {
        return new Player[]{new HumanPlayer("玩家一"), new HumanPlayer("玩家二")};
    }

    private void showChooseFirstMoverDialog() {
        new AlertDialog.Builder(this)
                .setTitle("点线棋")
                .setMessage("请选择先下棋者")
                .setPositiveButton(R.string.Player1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                startGame(0);
                            }
                        })
                .setNegativeButton(R.string.Player2,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                startGame(1);
                            }
                        }).show();
    }

    protected void showChooseGameSizeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("点线棋")
                .setMessage("请选择棋盘大小")
                .setPositiveButton("3 x 3",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                gameView.setGameSize(3);
                            }
                        })
                .setNegativeButton( "5 x 5",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                gameView.setGameSize(5);
                            }
                        }).show();
    }

    private void startGame(int indexOfPlayerFirstToMove) {
        playerNow = players[indexOfPlayerFirstToMove];
        gameView.startGame(playerNow, players);//--------------------------------------------!!!!!!
        updateState();
    }

    @Override
    public void setPlayerNow(Player player) {
        playerNow = player;
        updateState();
    }

    @Override
    public void setPlayerOccupyingBoxesCount(Map<Player, Integer> player_occupyingBoxesCount_map) {
        playersOccupying[0] = (player_occupyingBoxesCount_map.get(players[0]));
        playersOccupying[1] = (player_occupyingBoxesCount_map.get(players[1]));
        updateState();
    }

    @Override
    public void playerTouched() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (touchSoundOn)
                    soundpool.play(1, (float) 0.8, (float) 0.8, 0, 0, 1);
            }
        });
    }

    @Override
    public void setWinner(final Player[] winner) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String winnerNames = "";
                for (Player player : winner) {
                    winnerNames += player.getName();
                    winnerNames += " ";
                }
                new AlertDialog.Builder(DoubleActivity.this)

                        .setTitle("点线棋")
                        .setMessage(winnerNames + "赢了!")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        recreate();
                                    }
                                }).show();
            }
        });

    }
}
