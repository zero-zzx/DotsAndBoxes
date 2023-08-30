package com.zzx.apackage.model;

public class Line {
    private final Direction direction;
    private final int row;
    private final int column;

    public Line(Direction direction, int row, int column) {
        this.direction = direction;
        this.row = row;
        this.column = column;
    }

    public Direction direction() {
        return direction;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (row != line.row) return false;
        if (column != line.column) return false;
        return direction == line.direction;

    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "direction:"+direction().toString()+"row:"+row+"column"+column;
    }
}
