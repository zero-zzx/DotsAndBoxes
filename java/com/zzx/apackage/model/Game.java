package com.zzx.apackage.model;

import java.util.ArrayList;
import java.util.Observable;


public class Game extends Observable {
    public Player[] getPlayers() {
        return players;
    }

    private Player[] players;
    private int playerNowIndex;

    public int getWeigh() {
        return weigh;
    }

    public int getHeight() {
        return height;
    }

    private int weigh;
    private int height;
    private Player[][] occupied;
    private boolean[][] horizontalLines;
    private boolean[][] verticalLines;
    private Line latestLine;

    public Line getLatestLine() {
        return latestLine;
    }

    public Game(int weigh, int height, Player firstMover, Player... players) {
        this.weigh = weigh;
        this.height = height;
        this.players = players;

        occupied = new Player[height][weigh];
        horizontalLines = new boolean[height + 1][weigh];
        verticalLines = new boolean[height][weigh + 1];

        this.playerNowIndex = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                throw new IllegalArgumentException("Player Is Null");
            }
            if (players[i] == firstMover) {
                playerNowIndex = i;
            }
            players[i].addToGame(this);
        }

        if (players.length == 0)
            throw new IllegalArgumentException("No Player");
        if (weigh < 1 || height < 1)
            throw new IllegalArgumentException("Size Too Small");
    }

    public void start() {
        while (!isGameFinished()) {
            addMove(playerNow().move());//---------------------------------move核心！！！
            setChanged();
            notifyObservers();
        }
    }

    public void addMove(Line move) {
        if (isLineOccupied(move)) {
            return;
        }
        boolean newBoxOccupied = tryToOccupyBox(move);
        setLineOccupied(move);
        latestLine=move;

        if (!newBoxOccupied)
            toNextPlayer();
    }

    public Player playerNow() {
        return players[playerNowIndex];
    }

    public boolean isLineOccupied(Direction direction, int row, int column) {
        return isLineOccupied(new Line(direction, row, column));
    }

    public boolean isLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                return horizontalLines[line.row()][line.column()];
            case VERTICAL:
                return verticalLines[line.row()][line.column()];
        }
        throw new IllegalArgumentException(line.direction().toString());
    }

    public Player getBoxOccupier(int row,int column){
        return occupied[row][column];
    }

    public int getPlayerOccupyingBoxCount(Player player) {
        int count=0;
        for(int i=0;i<occupied.length;i++) {
            for (int j = 0; j < occupied[0].length; j++) {
                if(getBoxOccupier(i,j)==player)
                    count++;
            }
        }
        return count;
    }

    private boolean tryToOccupyBox(Line move) {
        boolean rightOccupied = tryToOccupyRightBox(move);
        boolean underOccupied = tryToOccupyUnderBox(move);
        boolean upperOccupied = tryToOccupyUpperBox(move);
        boolean leftOccupied = tryToOccupyLeftBox(move);
        return leftOccupied||rightOccupied||upperOccupied||underOccupied;
    }

    private void setLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                horizontalLines[line.row()][line.column()] = true;
                break;
            case VERTICAL:
                verticalLines[line.row()][line.column()] = true;
                break;
        }
    }

    private void setBoxOccupied(int row, int column, Player player) {
        occupied[row][column] = player;
    }

    private boolean tryToOccupyUpperBox(Line move) {
        if (move.direction() != Direction.HORIZONTAL || move.row() <= 0)
            return false;
        if (isLineOccupied(Direction.HORIZONTAL, move.row() - 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row() - 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row() - 1, move.column() + 1)) {
            setBoxOccupied(move.row() - 1, move.column(), playerNow());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyUnderBox(Line move) {
        if (move.direction() != Direction.HORIZONTAL || move.row() >= (height))
            return false;
        if (isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row(), move.column())
                && isLineOccupied(Direction.VERTICAL, move.row(), move.column() + 1)) {
            setBoxOccupied(move.row(), move.column(), playerNow());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyLeftBox(Line move) {
        if (move.direction() != Direction.VERTICAL || move.column() <= 0)
            return false;
        if (isLineOccupied(Direction.VERTICAL, move.row(), move.column() - 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row(), move.column() - 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column() - 1)) {
            setBoxOccupied(move.row(), move.column() - 1, playerNow());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyRightBox(Line move) {
        if (move.direction() != Direction.VERTICAL || move.column() >= (weigh))
            return false;
        if (isLineOccupied(Direction.VERTICAL, move.row(), move.column() + 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row(), move.column())
                && isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column())) {
            setBoxOccupied(move.row(), move.column(), playerNow());
            return true;
        } else {
            return false;
        }
    }

    private void toNextPlayer() {
        playerNowIndex = (playerNowIndex + 1) % players.length;
    }

    private boolean isGameFinished() {
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[0].length; j++) {
                 if (occupied[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public Player[] getWinners() {
        if(!isGameFinished()){
            return null;
        }
        int playerCount = players.length;
        int[] occupyingBoxCount = new int[playerCount];
        int maxOccupyingBoxCount=0;
        for (int player_i = 0; player_i < playerCount; player_i++) {
            occupyingBoxCount[player_i]=getPlayerOccupyingBoxCount(players[player_i]);
            if(occupyingBoxCount[player_i]>maxOccupyingBoxCount)
                maxOccupyingBoxCount=occupyingBoxCount[player_i];
        }
        ArrayList<Player> winners=new ArrayList<>();
        for (int player_i = 0; player_i < playerCount; player_i++) {
            if(occupyingBoxCount[player_i]==maxOccupyingBoxCount){
                winners.add(players[player_i]);
            }
        }
        return winners.toArray(new Player[0]);
    }
}
