package com.company.Logic.Pices;


import com.company.Logic.Board;
import com.company.Logic.MoveTypes.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece{


    boolean isCaptured;
    int xPos, yPos;
    Colour colour;
    private boolean firstMove;
    private boolean staleMate;
    private boolean check;
    private boolean checkMate;

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public enum Colour {
        BLACK,
        WHITE,
        NULL
    }

    public boolean checkState (Board board, Piece piece, int sourceX, int sourceY, int targetX, int targetY){
        Move attemptedMove = new Move(piece,sourceX,sourceY,targetX,targetY);
        if(board.getGame().isCheck()) {
            System.out.println("check");
            for (Move move : board.kingPossibleMoves(piece.getColour())) {
                if(move.equals(attemptedMove))
                    return true;
            }
            return false;
        }
        else{
            return true;
        }
    }

    public Piece(boolean isCaptured, int xPos, int yPos, Colour colour) {
        this.isCaptured = isCaptured;
        this.xPos = xPos;
        this.yPos = yPos;
        this.colour = colour;
    }

    public boolean moveValidation(Board board, Piece piece,int sourceX, int sourceY, int targetX, int targetY){
        return true;
    }

    public Move move(Board board, Piece piece, int targetX, int targetY){//TODO change to take in move object as only parameter
        Move move = new Move(piece, piece.getxPos(), piece.getyPos(), targetX, targetY);
        if(board.isPieceAtLocationCapturable(piece.getColour(), targetX, targetY)){
            move.setCapturedPiece(board.pieceAtLocation(targetX,targetY));
            board.pieceAtLocation(targetX,targetY).isCaptured = true;
            board.getPieces().remove(board.pieceAtLocation(targetX,targetY));
            board.getCapturedPieces().add(board.pieceAtLocation(targetX,targetY));
        }
        piece.setxPos(targetX);
        piece.setyPos(targetY);
        return move;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public boolean isStaleMate() {
        return staleMate;
    }

    public void setStaleMate(boolean staleMate) {
        this.staleMate = staleMate;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }
}
