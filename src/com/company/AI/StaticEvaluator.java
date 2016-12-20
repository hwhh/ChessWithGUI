package com.company.AI;

import com.company.Logic.Board;
import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.*;

public class StaticEvaluator {

    static final int pawnBoard[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}};
    static final int rookBoard[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}};
    static final int knightBoard[][]={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};
    static final int bishopBoard[][]={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};
    static final int queenBoard[][]={
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}};
    static final int kingMidBoard[][]={
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}};
    static final int kingEndBoard[][]={
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}};



    private TurnNode turnNode;

    public StaticEvaluator(TurnNode turnNode) {
        this.turnNode = turnNode;
    }

    public int rating(int depth) {
        int counter=0;
        int material = rateMaterial();
        counter+=rateMaterial();
        counter+= rateMovability(depth, material);
        counter+=ratePositional(material);
        counter+=rateAttack();
        return -counter+depth*50;
        //positive = white, negative is opponent ?
    }

    public int rateAttack(){
        int counter = 0;
        for (Piece piece : turnNode.getBoard().getPieces()) {
            for (Move move : turnNode.getBoard().availableMoves(piece)) {
                if(move.getCapturedPiece()!= null){
                    if (move.getCapturedPiece() instanceof Bishop)
                        counter+=30;
                    else if (move.getCapturedPiece() instanceof King)
                        counter+= 99999;
                    else if (move.getCapturedPiece() instanceof Knight)
                        counter+= 30;
                    else if (move.getCapturedPiece() instanceof Pawn)
                        counter+= 10;
                    else if (move.getCapturedPiece() instanceof Queen)
                        counter+= 100;
                    else if (move.getCapturedPiece() instanceof Rook)
                        counter+= 40;
                }

            }
        }
        return counter;
    }

    public int rateMaterial() {
        int counter = 0, bishopCounter=0;
        for (Piece piece : turnNode.getBoard().getPieces()) {
            if (piece instanceof Bishop)
                bishopCounter+=1;
            else if (piece instanceof King)
                counter+= 99999;
            else if (piece instanceof Knight)
                counter+= 30;
            else if (piece instanceof Pawn)
                counter+= 10;
            else if (piece instanceof Queen)
                counter+= 100;
            else if (piece instanceof Rook)
                counter+= 40;
        }
        if (bishopCounter>=2) {
            counter+=300*bishopCounter;
        } else {
            if (bishopCounter==1) {counter+=250;}
        }
        return counter;
    }




    public int rateMovability(int depth, int material) {
        int counter = 0;
        for (Piece piece : turnNode.getBoard().getPieces()) {
            for (Move move : turnNode.getBoard().availableMoves(piece)) {
                counter++;
            }
        }
        //check for check and stalemate
        return counter;
    }

   public int ratePositional(int material) {
        int counter=0;
        for (Piece piece : turnNode.getBoard().getPieces()) {
            if (piece instanceof Bishop)
                counter += bishopBoard[piece.getxPos() / 8][piece.getyPos() % 8];
            else if (piece instanceof Knight)
                counter += knightBoard[piece.getxPos() / 8][piece.getyPos() % 8];
            else if (piece instanceof Pawn)
                counter += pawnBoard[piece.getxPos() / 8][piece.getyPos() % 8];
            else if (piece instanceof Queen)
                counter += queenBoard[piece.getxPos() / 8][piece.getyPos() % 8];
            else if (piece instanceof Rook)
                counter += rookBoard[piece.getxPos() / 8][piece.getyPos() % 8];
            else if (piece instanceof King) {
                if (material >= 1750)
                    counter += kingMidBoard[piece.getxPos() / 8][piece.getyPos() % 8];
                if (material < 1750)
                    counter += kingEndBoard[piece.getxPos() / 8][piece.getyPos() % 8];
            }
        }
        return counter;
    }

}


