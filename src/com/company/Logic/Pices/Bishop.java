package com.company.Logic.Pices;

import com.company.Logic.Board;
import com.company.Logic.MoveTypes.Move;
import com.company.Main;

import java.util.List;

public class Bishop extends Piece {

    // BISHOP_MOVES = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

    public Bishop(boolean isCaptured, int x, int y, Colour colour) {
        super(isCaptured, x, y, colour);
    }


    public boolean moveValidation(Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;
        boolean moveValid = false;
        if (!board.isPieceAtLocation(targetX, targetY) || board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)) {
            if (diffX == diffY && diffY > 0)
                moveValid=!board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 1, 1);
            else if (diffX == -diffY && diffY > 0)
                moveValid=!board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, -1, 1);
            else if (diffX == diffY && diffY < 0)
                moveValid=!board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, -1, -1);
            else if (diffX == -diffY && diffY < 0)
                moveValid=!board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 1, -1);
        }
        return moveValid;
    }










}
