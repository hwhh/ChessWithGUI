package com.company.Logic.Pices;

import com.company.Logic.Board;

public class Rook extends Piece {

    // ROOK_MOVES = {{1,0}, {-1,0}, {0, 1}, {0,-1}};

    public Rook(boolean isCaptured, int x, int y, Colour colour) {
        super(isCaptured, x, y, colour);
    }

    //TODO add in casteling
    public boolean  moveValidation(Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){//TODO make directions in Piece class
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;
        if (!board.isPieceAtLocation(targetX, targetY) || board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)) {
            if(diffX==0 && diffY > 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 0, 1);
            else if(diffX > 0 && diffY == 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 1, 0);
            else if(diffX < 0 && diffY == 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, -1, 0);
            else if(diffX==0 && diffY < 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 0, -1);
        }
        return false;
    }

}
