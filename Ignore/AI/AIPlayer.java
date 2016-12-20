package com.company.AI;

import com.company.GUI.GUIBoard;
import com.company.Logic.Board;
import com.company.Logic.Game;
import com.company.Logic.IPlayer;
import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.*;

import java.util.ArrayList;
import java.util.List;


public class AIPlayer extends AI implements IPlayer{
    
    private StaticEvaluator staticEvaluator;

    public AIPlayer(Board board, Game game, GUIBoard guiBoard, Piece.Colour playerColour, int movesAhead) {
        super(board, game, guiBoard, playerColour, movesAhead);
    }

    @Override
    public Move getMove() {

        Move move =  getBestMove();
        System.out.println(move.toString());
        //executeMove(move.getPiece(), move);
        //guiBoard.repaint();
        return move;
    }

    public Move getBestMove (){
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        double bestResult = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move move : getValidMoves()) {
            executeMove(move.getPiece(), move);
            double score = negaMax(movesAhead, alpha, beta, playerColour);
            undoMove(move);
            if(score > bestResult){
                bestResult = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public List<Move> getValidMoves(){
        List<Move> availableMoves = new ArrayList<>();
        for (Piece piece : board.getPiecesForColour(playerColour)) {
            availableMoves.addAll(board.availableMoves(piece));
        }
        return availableMoves;
    }

    private double negaMax(int depth, double alpha, double beta, Piece.Colour colour){
        if(depth == 0 || game.whiteWon || game.blackWon){
            return evaluateState();
        }
        double bestValue = Integer.MIN_VALUE;
        for (Move availableMove : getValidMoves()) {
            executeMove(availableMove.getPiece(), availableMove);
            double v = -negaMax(depth - 1, -beta, -alpha, switchColour(colour));
            undoMove(availableMove);
            bestValue= Math.max(bestValue, v);
            alpha= Math.max(alpha,v);
            if(alpha>=beta)
                break;
        }
        return bestValue;
    }



    private int evaluateState() {
        int scoreWhite = 0;
        int scoreBlack = 0;
        for (Piece piece : this.board.getPieces()) {
            if (piece.getColour() == Piece.Colour.BLACK) {
                scoreBlack += getScoreForPieceType(piece);
                scoreBlack += getScoreForPiecePosition(piece.getxPos()-1, piece.getyPos()-1);
            } else if (piece.getColour() == Piece.Colour.WHITE) {
                scoreWhite += getScoreForPieceType(piece);
                scoreWhite += getScoreForPiecePosition(piece.getxPos()-1, piece.getyPos()-1);
            }
        }
        if (game.getGameState() == 1)
            return scoreBlack - scoreWhite;
        else if (game.getGameState() == 0)
            return scoreWhite - scoreBlack;
        else if (game.whiteWon || game.blackWon)
            return Integer.MIN_VALUE + 1;
        else
            throw new IllegalStateException("unknown game state: " + game.getGameState());
    }


    private int getScoreForPiecePosition(int row, int column) {
        byte[][] positionWeight =
                {{1,1,1,1,1,1,1,1}
                ,{2,2,2,2,2,2,2,2}
                ,{2,2,3,3,3,3,2,2}
                ,{2,2,3,4,4,3,2,2}
                ,{2,2,3,4,4,3,2,2}
                ,{2,2,3,3,3,3,2,2}
                ,{2,2,2,2,2,2,2,2}
                ,{1,1,1,1,1,1,1,1}
                };
        return positionWeight[row][column];
    }


    private int getScoreForPieceType(Piece piece){
        if (piece instanceof Bishop)
            return 30;
        else if (piece instanceof King)
            return 999;
        else if (piece instanceof Knight)
            return 30;
        else if (piece instanceof Pawn)
            return  10;
        else if (piece instanceof Queen)
            return 100;
        else if (piece instanceof Rook)
            return  40;
        return 0;
    }



}
