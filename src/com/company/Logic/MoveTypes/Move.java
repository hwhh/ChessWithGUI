package com.company.Logic.MoveTypes;

import com.company.Logic.Pices.Piece;

public class Move {

    int sourceX, sourceY, targetX, targetY;
    Piece piece, capturedPiece;

    public Move(Piece piece,  int sourceX, int sourceY, int targetX, int targetY) {
        this.piece = piece;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public boolean isEqual(Move move) {
        boolean equal = true;
        if (sourceX!=move.sourceX)
            equal=false;
        if (sourceY!=move.sourceY)
            equal=false;
        if (targetX!=move.targetX)
            equal=false;
        if (targetY!=move.targetY)
            equal=false;
        if (!piece.equals(move.piece))
            equal=false;
        return equal;
    }



    @Override
    public String toString() {
        return "Move{" +
                "sourceX=" + sourceX +
                ", sourceY=" + sourceY +
                ", targetX=" + targetX +
                ", targetY=" + targetY +
                '}';
    }

    public int getTargetY() {
        return targetY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getSourceX() {
        return sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }
}
