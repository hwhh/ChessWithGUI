package com.company.Logic.Pices;

import com.company.Logic.Board;

public class Knight extends Piece {

    // KNIGHT_MOVES = {{2,-1}, {2,1}, {1,-2}, {1,2}, {-1,-2}, {-1,2}, {-2,-1}, {-2,1}};

    public Knight(boolean isCaptured, int x, int y, Colour colour) {
        super(isCaptured, x, y, colour);
    }

    public boolean  moveValidation(Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;
        if (!board.isPieceAtLocation(targetX, targetY) || board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)) {
            if(diffX == 2 && diffY == -1)
                return true;
            else if(diffX == 2 && diffY == 1)
                return true;
            else if(diffX == 1 && diffY == -2)
                return true;
            else if(diffX == 1 && diffY == 2)
                return true;
            else if(diffX == -1 && diffY == -2)
                return true;
            else if(diffX == -1 && diffY == 2)
                return true;
            else if(diffX == -2 && diffY == -1)
                return true;
            else if(diffX == -2 && diffY == 1)
                return true;
        }
        return false;
    }



}
