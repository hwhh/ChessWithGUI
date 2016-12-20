package com.company.Logic.Pices;

import com.company.Logic.Board;


public class King extends Piece {

    // KING_MOVES = {{1,1}, {-1, 1}, {-1,-1}, {1,-1}, {0, 1}, {0,-1}, {1,0}, {-1,0}};



    //TODO add in check mate and stalemate checks - maybe in available moves - add in red square if in check
    public King(boolean isCaptured, int x, int y, Colour colour) {
        super(isCaptured, x, y, colour);
        setCheck(false);
        setCheckMate(false);
        setStaleMate(false);
    }



    public boolean  moveValidation(Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;

        if (!board.isPieceAtLocation(targetX, targetY) || board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)) {
            if(diffX == 1 && diffY == 1)
                return true;
            else if(diffX == -1 && diffY == 1)
                return true;
            else if(diffX == -1 && diffY == -1)
                return true;
            else if(diffX == 1 && diffY == -1)
                return true;
            else if(diffX == 0 && diffY == 1)
                return true;
            else if(diffX == 0 && diffY == -1)
                return true;
            else if(diffX == 1 && diffY == 0)
                return true;
            else if(diffX == -1 && diffY == 0)
                return true;
        }
        return false;
    }

}


