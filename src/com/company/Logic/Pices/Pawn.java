package com.company.Logic.Pices;

import com.company.Logic.Board;
import com.company.Logic.MoveTypes.Move;

public class Pawn extends Piece {

    //PAWN_MOVES = {{1,0}, {-1,0}, {0, 1}, {0,-1} {};



    public Pawn(boolean isCaptured, int x, int y, Colour colour) {
        super(isCaptured, x, y, colour);
        setFirstMove(true);
    }

    //TODO Add in promotion move
    public boolean moveValidation(Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;
            if(diffX==0 && diffY == 1 && piece.getColour() == Colour.BLACK && !board.isPieceAtLocation(targetX, targetY))//NORMAL MOVE
                return true;
            else if(diffX == 0 && diffY == -1 && piece.getColour() == Colour.WHITE && !board.isPieceAtLocation(targetX, targetY) )//NORMAL MOVE
                return true;
            else if(diffX == -1  && diffY == -1 && piece.getColour() == Colour.WHITE && board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY))//CAPTURING MOVE
                return true;
            else if(diffX == 1  && diffY == -1 && piece.getColour() == Colour.WHITE && board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY))//CAPTURING MOVE
                return true;
            else if(diffX == -1  && diffY == 1 && piece.getColour() == Colour.BLACK && board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY))//CAPTURING MOVE
                return true;
            else if(diffX == 1  && diffY == 1 && piece.getColour() == Colour.BLACK && board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY))//CAPTURING MOVE
                return true;
            if(diffX==0 && diffY == 2&& piece.getColour() == Colour.BLACK && isFirstMove() && !board.isPieceAtLocation(targetX, targetY))//FIRST MOVE
                return true;
            else if(diffX == 0 && diffY ==-2 && piece.getColour() == Colour.WHITE && isFirstMove() && !board.isPieceAtLocation(targetX, targetY))//FIRST MOVE
                return true;
        return false;
    }

    public Move move(Board board, Piece piece, int targetX, int targetY){
        setFirstMove(false);
        if(board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)){
            board.pieceAtLocation(targetX,targetY).isCaptured = true;
            board.getPieces().remove(board.pieceAtLocation(targetX,targetY));
            board.getCapturedPieces().add(board.pieceAtLocation(targetX,targetY));
        }
        Move move = new Move(piece, piece.getxPos(), piece.getyPos(), targetX, targetY);
        piece.setxPos(targetX);
        piece.setyPos(targetY);
        return move;
    }
}
