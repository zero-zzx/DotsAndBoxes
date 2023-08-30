package com.zzx.apackage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class AIPlayer extends Player {
    private int difficulty;
    private int sz;
    private boolean[][] horizontal;
    private boolean[][] vertical;
    private Box[][] box;
    private Vector<Line> safeLine;
    private Vector<Line> goodLine;
    private Vector<Line> badLine;
    private HashMap<Line, Integer> goodLineValue;
    private HashMap<Line, Integer> badLineValue;
    private HashMap<Line, Integer> goodLineType;

    private AIPlayer(int difficulty, Game game) {
        this(null);
        this.setDifficulty(difficulty);
        this.addToGame(game);
    }

    public void setGameSize(int x){
        sz=x;
    }

    public AIPlayer(String name) {
        super(name);
        horizontal = new boolean[6][5];
        vertical = new boolean[5][6];
        box = new Box[5][5];
        safeLine = new Vector<Line>();
        goodLine = new Vector<Line>();
        badLine = new Vector<Line>();
        badLineValue = new HashMap<Line, Integer>();
        goodLineValue = new HashMap<Line, Integer>();
        goodLineType = new HashMap<Line, Integer>();

    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Line move() {
        initialiseBoard();
        initialiseSafeLine();
        initialiseGoodLine();
        initialiseBadLine();

        if (difficulty <= 1) {
            return normal();
        } else if (difficulty == 2) {
            return hard();
        } else if (difficulty >= 3) {
            return ultra();
        } else
            return random();
    }

    private void initialiseGoodLine() {
        int counter = 0;
        boolean t1 = false;
        boolean t2 = false;
        goodLine.clear();
        for (int i = 0; i < sz+1; i++) {
            for (int j = 0; j < sz; j++) {
                if (!horizontal[i][j]) {
                    if (i == 0) {
                        counter = 0;
                        if (box[i][j].left)
                            counter++;
                        if (box[i][j].bottom)
                            counter++;
                        if (box[i][j].right)
                            counter++;
                        if (box[i][j].contain() == 3)
                            goodLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == sz) {

                        counter = 0;
                        if (box[i - 1][j].left)
                            counter++;
                        if (box[i - 1][j].top)
                            counter++;
                        if (box[i - 1][j].right)
                            counter++;
                        if (counter == 3)
                            goodLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (box[i][j].left)
                            counter++;
                        if (box[i][j].bottom)
                            counter++;
                        if (box[i][j].right)
                            counter++;
                        if (counter == 3)
                            t1 = true;
                        counter = 0;
                        if (box[i - 1][j].left)
                            counter++;
                        if (box[i - 1][j].top)
                            counter++;
                        if (box[i - 1][j].right)
                            counter++;
                        if (counter == 3)
                            t2 = true;
                        if (t1 || t2)
                            goodLine.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!vertical[j][i]) {
                    if (i == 0) {
                        counter = 0;
                        if (box[j][i].right)
                            counter++;
                        if (box[j][i].bottom)
                            counter++;
                        if (box[j][i].top)
                            counter++;
                        if (counter == 3)
                            goodLine.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == sz) {
                        counter = 0;
                        if (box[j][i - 1].left)
                            counter++;
                        if (box[j][i - 1].top)
                            counter++;
                        if (box[j][i - 1].bottom)
                            counter++;
                        if (counter == 3)
                            goodLine.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (box[j][i].right)
                            counter++;
                        if (box[j][i].bottom)
                            counter++;
                        if (box[j][i].top)
                            counter++;
                        if (counter == 3)
                            t1 = true;
                        counter = 0;
                        if (box[j][i - 1].left)
                            counter++;
                        if (box[j][i - 1].top)
                            counter++;
                        if (box[j][i - 1].bottom)
                            counter++;
                        if (counter == 3)
                            t2 = true;
                        if (t1 || t2)
                            goodLine.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }

    }

    private void initialiseBadLine() {
        int counter = 0;
        boolean t1 = false;
        boolean t2 = false;
        badLine.clear();
        for (int i = 0; i < sz+1; i++) {
            for (int j = 0; j < sz; j++) {
                if (!horizontal[i][j]) {
                    if (i == 0) {
                        counter = 0;
                        if (box[i][j].left)
                            counter++;
                        if (box[i][j].bottom)
                            counter++;
                        if (box[i][j].right)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == sz) {

                        counter = 0;
                        if (box[i - 1][j].left)
                            counter++;
                        if (box[i - 1][j].top)
                            counter++;
                        if (box[i - 1][j].right)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (box[i][j].left)
                            counter++;
                        if (box[i][j].bottom)
                            counter++;
                        if (box[i][j].right)
                            counter++;
                        if (counter == 2)
                            t1 = true;
                        counter = 0;
                        if (box[i - 1][j].left)
                            counter++;
                        if (box[i - 1][j].top)
                            counter++;
                        if (box[i - 1][j].right)
                            counter++;
                        if (counter == 2)
                            t2 = true;
                        if (t1 || t2)
                            badLine.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!vertical[j][i]) {
                    if (i == 0) {
                        counter = 0;
                        if (box[j][i].right)
                            counter++;
                        if (box[j][i].bottom)
                            counter++;
                        if (box[j][i].top)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == sz) {
                        counter = 0;
                        if (box[j][i - 1].left)
                            counter++;
                        if (box[j][i - 1].top)
                            counter++;
                        if (box[j][i - 1].bottom)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (box[j][i].right)
                            counter++;
                        if (box[j][i].bottom)
                            counter++;
                        if (box[j][i].top)
                            counter++;
                        if (counter == 2)
                            t1 = true;
                        counter = 0;
                        if (box[j][i - 1].left)
                            counter++;
                        if (box[j][i - 1].top)
                            counter++;
                        if (box[j][i - 1].bottom)
                            counter++;
                        if (counter == 2)
                            t2 = true;
                        if (t1 || t2)
                            badLine.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }
        for (Line a : goodLine) {
            try {
                for (Line b : badLine) {
                    if (a.direction() == b.direction() && a.row() == b.row() && a.column() == b.column()) {
                        badLine.remove(b);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void initialiseSafeLine() {
        int counter = 0;
        boolean t1 = false;
        boolean t2 = false;
        safeLine.clear();

        for (int i = 0; i < sz+1; i++) {
            for (int j = 0; j < sz; j++) {
                if (!horizontal[i][j]) {
                    if (i == 0) {
                        counter = 0;
                        if (box[i][j].left)
                            counter++;
                        if (box[i][j].bottom)
                            counter++;
                        if (box[i][j].right)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == sz) {

                        counter = 0;
                        if (box[i - 1][j].left)
                            counter++;
                        if (box[i - 1][j].top)
                            counter++;
                        if (box[i - 1][j].right)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (box[i][j].left)
                            counter++;
                        if (box[i][j].bottom)
                            counter++;
                        if (box[i][j].right)
                            counter++;
                        if (counter < 2)
                            t1 = true;
                        counter = 0;
                        if (box[i - 1][j].left)
                            counter++;
                        if (box[i - 1][j].top)
                            counter++;
                        if (box[i - 1][j].right)
                            counter++;
                        if (counter < 2)
                            t2 = true;
                        if (t1 && t2)
                            safeLine.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!vertical[j][i]) {
                    if (i == 0) {
                        counter = 0;
                        if (box[j][i].right)
                            counter++;
                        if (box[j][i].bottom)
                            counter++;
                        if (box[j][i].top)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == sz) {
                        counter = 0;
                        if (box[j][i - 1].left)
                            counter++;
                        if (box[j][i - 1].top)
                            counter++;
                        if (box[j][i - 1].bottom)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (box[j][i].right)
                            counter++;
                        if (box[j][i].bottom)
                            counter++;
                        if (box[j][i].top)
                            counter++;
                        if (counter < 2)
                            t1 = true;
                        counter = 0;
                        if (box[j][i - 1].left)
                            counter++;
                        if (box[j][i - 1].top)
                            counter++;
                        if (box[j][i - 1].bottom)
                            counter++;
                        if (counter < 2)
                            t2 = true;
                        if (t1 && t2)
                            safeLine.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }
    }

    private void initialiseBoard() {
        for (int i = 0; i < sz+1; i++) {
            for (int j = 0; j < sz; j++) {
                if (getGame().isLineOccupied(Direction.HORIZONTAL, i, j))
                    horizontal[i][j] = true;
                else
                    horizontal[i][j] = false;
                if (getGame().isLineOccupied(Direction.VERTICAL, j, i))
                    vertical[j][i] = true;
                else
                    vertical[j][i] = false;
            }
        }
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                box[i][j] = new Box(vertical[i][j], horizontal[i][j],
                        vertical[i][j + 1], horizontal[i + 1][j]);
            }
        }
    }

    private void initialiseGoodLineValue() {
        if (goodLine.isEmpty())
            return;
        goodLineValue.clear();
        for (Line Line : goodLine) {
            VBoard vboard = new VBoard(horizontal, vertical);
            goodLineValue.put(Line, vboard.getGoodLineValue(Line));
        }
    }

    private void initialiseBadLineValue() {
        if (badLine.isEmpty())
            return;
        badLineValue.clear();
        for (Line Line : badLine) {
            VBoard vboard = new VBoard(horizontal, vertical);
            badLineValue.put(Line, vboard.getBadLineValue(Line));
        }
    }

    private void initialiseGoodLineType() {
        if (goodLine.isEmpty())
            return;
        goodLineType.clear();
        for (Line Line : goodLine) {
            if (Line.direction() == Direction.HORIZONTAL) {
                if (Line.row() == 0)
                    goodLineType.put(Line, 1);
                else if (Line.row() == sz)
                    goodLineType.put(Line, 1);
                else {
                    int counter = 0;
                    if (box[Line.row()][Line.column()].contain() == 3)
                        counter++;
                    if (box[Line.row() - 1][Line.column()].contain() == 3)
                        counter++;
                    goodLineType.put(Line, counter);
                }
            } else {
                if (Line.column() == 0)
                    goodLineType.put(Line, 1);
                else if (Line.column() == sz)
                    goodLineType.put(Line, 1);
                else {
                    int counter = 0;
                    if (box[Line.row()][Line.column()].contain() == 3)
                        counter++;
                    if (box[Line.row()][Line.column() - 1].contain() == 3)
                        counter++;
                    goodLineType.put(Line, counter);
                }

            }

        }
    }

    private Line Line(VBoard vboard) {
        for (int i = 0; i < sz+1; i++) {
            for (int j = 0; j < sz; j++) {
                horizontal[i][j] = vboard.horizontal[i][j];
                vertical[j][i] = vboard.vertical[j][i];
            }
        }
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                box[i][j] = new Box(vertical[i][j], horizontal[i][j],
                        vertical[i][j + 1], horizontal[i + 1][j]);
            }
        }
        initialiseSafeLine();
        initialiseGoodLine();
        initialiseBadLine();

        if (difficulty == 1) {
            return normal();
        } else if (difficulty == 2) {
            return hard();
        } else if (difficulty == 3) {
            return ultra();
        } else
            return random();
    }

    private Line normal() {
        if (goodLine.size() != 0)
            return goodLine.get((int) ((goodLine.size()) * Math.random()));
        if (safeLine.size() != 0)
            return safeLine.get((int) ((safeLine.size()) * Math.random()));
        else {
            Line min = null;
            int minValue = 26;
            initialiseBadLineValue();
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < minValue) {
                    min = Line;
                    minValue = badLineValue.get(Line);
                }
            }
            return min;
        }
    }

    private Line hard() {
        if (safeLine.size() != 0) {
            if (goodLine.size() != 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            else
                return safeLine.get((int) ((safeLine.size()) * Math.random()));
        }
        else if (goodLine.size() != 0) {
            if (badLine.size() == 0)//可以通吃结束战斗，直接下
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            initialiseGoodLineValue();
            initialiseBadLineValue();

            ArrayList<Line> bad = new ArrayList<Line>();
            ArrayList<Line> bad2 = new ArrayList<Line>();
            ArrayList<Line> good = new ArrayList<Line>();
            ArrayList<Line> good2 = new ArrayList<Line>();
            int badValue = 26;
            int bad2Value = 26;
            int goodValue = 0;//这步最多占领多少格
            int goodValue2 = 0;//这步倒数第二多占领多少格

            for (Line Line : badLine) {
                if (badLineValue.get(Line) < badValue) {
                    bad2.clear();
                    for (Line m : bad) {
                        bad2.add(m);
                    }
                    bad2Value = badValue;
                    bad.clear();
                    bad.add(Line);
                    badValue = badLineValue.get(Line);
                } else if (badLineValue.get(Line) == badValue) {
                    bad.add(Line);
                }
            }
            for (Line Line : goodLine) {
                if (goodLineValue.get(Line) > goodValue) {
                    good2.clear();
                    for (Line m : good) {
                        good2.add(m);
                    }
                    goodValue2 = goodValue;
                    good.clear();
                    good.add(Line);
                    goodValue = goodLineValue.get(Line);
                } else if (goodLineValue.get(Line) == goodValue) {
                    good.add(Line);
                }
            }

            //AI灵魂，留给对方两个子，自己拿剩下的一条
            if (goodValue == 2 && badValue == 2 && bad.size() == 1 && bad2.size() != 0 && bad2Value > 6) {
                if (goodValue2 > 2) {
                    return good2.get((int) ((good2.size()) * Math.random()));
                }
                initialiseGoodLineType();
                for (Line Line : good) {
                    if (goodLineType.get(Line) == 2) {
                        return Line;
                    }
                }
                return bad.get(0);
            }

            else{
                return good.get((int) ((good.size()) * Math.random()));
            }

        }
        else {
            Line min = null;
            int minValue = 26;
            initialiseBadLineValue();
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < minValue) {
                    min = Line;
                    minValue = badLineValue.get(Line);
                }
            }
            return min;
        }

    }

    private Line ultra() {
        if (safeLine.size() != 0) {
            if (goodLine.size() != 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            else
                return safeLine.get((int) ((safeLine.size()) * Math.random()));
        } else if (goodLine.size() != 0) {
            if (badLine.size() == 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            initialiseGoodLineValue();
            initialiseBadLineValue();

            ArrayList<Line> bad = new ArrayList<Line>();
            ArrayList<Line> bad2 = new ArrayList<Line>();
            ArrayList<Line> good = new ArrayList<Line>();
            ArrayList<Line> good2 = new ArrayList<Line>();
            int badValue = 26;
            int bad2Value = 26;
            int goodValue = 0;
            int goodValue2 = 0;
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < badValue) {
                    bad2.clear();
                    for (Line m : bad) {
                        bad2.add(m);
                    }
                    bad2Value = badValue;
                    bad.clear();
                    bad.add(Line);
                    badValue = badLineValue.get(Line);
                } else if (badLineValue.get(Line) == badValue) {
                    bad.add(Line);
                }
            }
            for (Line Line : goodLine) {
                if (goodLineValue.get(Line) > goodValue) {
                    good2.clear();
                    for (Line m : good) {
                        good2.add(m);
                    }
                    goodValue2 = goodValue;
                    good.clear();
                    good.add(Line);
                    goodValue = goodLineValue.get(Line);
                } else if (goodLineValue.get(Line) == goodValue) {
                    good.add(Line);
                }
            }

            if (goodValue == 2 && badValue == 2 && bad.size() == 1
                    && bad2.size() != 0 && bad2Value > 6) {
                if (goodValue2 > 2) {
                    return good2.get((int) ((good2.size()) * Math.random()));
                }
                initialiseGoodLineType();
                for (Line Line : good) {
                    if (goodLineType.get(Line) == 2) {
                        return Line;
                    }
                }
                return bad.get(0);
            } else
                return good.get((int) ((good.size()) * Math.random()));

        } else {
            Line min = null;
            int minValue = 26;
            initialiseBadLineValue();
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < minValue) {
                    min = Line;
                    minValue = badLineValue.get(Line);
                }
            }
            return min;
        }

    }

    private Line random() {
        if (goodLine.size() != 0)
            return goodLine.get((int) ((goodLine.size()) * Math.random()));
        Line line=null;
        for (int i = 0; i < sz+1; i++) {
            for (int j = 0; j < sz; j++) {
                if (!horizontal[i][j]) {
                    line = new Line(Direction.HORIZONTAL, i, j);
                    break;
                }
                if (!vertical[j][i]) {
                    line = new Line(Direction.VERTICAL, j, i);
                    break;
                }
            }
        }
        return line;
    }

    private static class Box {
        boolean left;
        boolean top;
        boolean right;
        boolean bottom;
        boolean ocpd;

        Box(boolean l, boolean t, boolean r, boolean b) {
            this.left = l;
            this.top = t;
            this.right = r;
            this.bottom = b;
            if (l && t && r && b)
                this.ocpd = true;
            else
                this.ocpd = false;
        }

        int contain() {
            int counter = 0;
            if (this.left)
                counter++;
            if (this.right)
                counter++;
            if (this.top)
                counter++;
            if (this.bottom)
                counter++;
            return counter;
        }

    }

    class VBoard {
        boolean[][] horizontal;
        boolean[][] vertical;
        boolean[][] occupied;
        boolean keepgoing;
        private Box[][] box;

        VBoard(boolean[][] h, boolean[][] v) {
            horizontal = new boolean[6][5];
            vertical = new boolean[5][6];
            occupied = new boolean[5][5];
            box = new Box[5][5];
            for (int i = 0; i < sz+1; i++) {
                for (int j = 0; j < sz; j++) {
                    horizontal[i][j] = h[i][j];
                    vertical[j][i] = v[j][i];
                }
            }

        }

        void ini() {
            for (int i = 0; i < sz; i++) {
                for (int j = 0; j < sz; j++) {
                    box[i][j] = new Box(vertical[i][j], horizontal[i][j],
                            vertical[i][j + 1], horizontal[i + 1][j]);
                    if (box[i][j].ocpd)
                        occupied[i][j] = true;
                }
            }
        }

        void add(Line Line) {
            Direction type = Line.direction();
            int a = Line.row();
            int b = Line.column();
            keepgoing = false;

            switch (type) {
                case HORIZONTAL:
                    if (!horizontal[a][b]) {
                        horizontal[a][b] = true;
                        if (a == 0) {
                            if (horizontal[a + 1][b] && vertical[a][b]
                                    && vertical[a][b + 1]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                        } else if (a == sz) {
                            if (horizontal[a - 1][b] && vertical[a - 1][b]
                                    && vertical[a - 1][b + 1]) {
                                occupied[a - 1][b] = true;
                                keepgoing = true;
                            }
                        } else {
                            if (horizontal[a + 1][b] && vertical[a][b]
                                    && vertical[a][b + 1]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                            if (horizontal[a - 1][b] && vertical[a - 1][b]
                                    && vertical[a - 1][b + 1]) {
                                occupied[a - 1][b] = true;
                                keepgoing = true;
                            }
                        }
                    } else {
                        keepgoing = true;
                    }
                    break;
                case VERTICAL:
                    if (!vertical[a][b]) {
                        vertical[a][b] = true;
                        if (b == 0) {
                            if (vertical[a][b + 1] && horizontal[a][b]
                                    && horizontal[a + 1][b]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                        } else if (b == sz) {
                            if (vertical[a][b - 1] && horizontal[a][b - 1]
                                    && horizontal[a + 1][b - 1]) {
                                occupied[a][b - 1] = true;
                                keepgoing = true;
                            }
                        } else {
                            if (vertical[a][b + 1] && horizontal[a][b]
                                    && horizontal[a + 1][b]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                            if (vertical[a][b - 1] && horizontal[a][b - 1]
                                    && horizontal[a + 1][b - 1]) {
                                occupied[a][b - 1] = true;
                                keepgoing = true;
                            }
                        }
                    } else {
                        keepgoing = true;
                    }
                    break;
            }
        }

        int getOccupiedNumber() {
            int counter = 0;
            for (int i = 0; i < sz; i++) {
                for (int j = 0; j < sz; j++) {
                    if (occupied[i][j])
                        counter++;
                }
            }
            return counter;
        }

        int getBadLineValue(Line Line) {
            int start, end;
            if (Line.direction() == Direction.HORIZONTAL)
                this.horizontal[Line.row()][Line.column()] = true;
            else
                this.vertical[Line.row()][Line.column()] = true;
            AIPlayer ai = new AIPlayer(0, getGame());
            ai.setGameSize(sz);
            ini();
            start = this.getOccupiedNumber();
            keepgoing = true;
            while (keepgoing) {
                add(ai.Line(this));
                if (sz==5&&this.getOccupiedNumber() == 25||sz==3&&this.getOccupiedNumber()==9)
                    break;
            }
            end = this.getOccupiedNumber();
            return (end - start);
        }

        int getGoodLineValue(Line Line) {
            int start, end;
            if (Line.direction() == Direction.HORIZONTAL)
                this.horizontal[Line.row()][Line.column()] = true;
            else
                this.vertical[Line.row()][Line.column()] = true;
            AIPlayer ai = new AIPlayer(0, getGame());
            ai.setGameSize(sz);
            ini();
            start = this.getOccupiedNumber();
            keepgoing = true;
            while (keepgoing) {
                add(ai.Line(this));
                if (sz==5&&this.getOccupiedNumber() == 25||sz==3&&this.getOccupiedNumber()==9)
                    break;
            }
            end = this.getOccupiedNumber();
            return (end - start);
        }
    }

}
