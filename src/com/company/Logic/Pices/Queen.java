package com.company.Logic.Pices;

import com.company.Logic.Board;

public class Queen extends Piece {

    // QUEEN_MOVES = {{1,1}, {-1, 1}, {1,-1},  {-1,-1}, {0, 1}, {0,-1}, {1,0}, {-1,0}};

    public Queen(boolean isCaptured, int x, int y, Colour colour) {
        super(isCaptured, x, y, colour);
    }

    public boolean  moveValidation(Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;


        if (!board.isPieceAtLocation(targetX, targetY) || board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)) {
            if (diffX == diffY && diffY > 0)
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 1, 1);
            else if (diffX == -diffY && diffY > 0)
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, -1, 1);
            else if (diffX == -diffY && diffY < 0)
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 1, -1);
            else if (diffX == diffY && diffY < 0)
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, -1, -1);
            else if(diffX==0 && diffY > 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 0, 1);
            else if(diffX==0 && diffY < 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 0, -1);
            else if(diffX > 0 && diffY == 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, 1, 0);
            else if(diffX < 0 && diffY == 0 )
                return !board.isPieceBetweenLocations(sourceX, sourceY, targetX, targetY, -1, 0);
        }
        return false;
    }



}
