package com.company.AI;

import com.company.GUI.GUIBoard;
import com.company.Logic.Board;
import com.company.Logic.Game;
import com.company.Logic.IPlayer;
import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.Piece;

import java.util.ArrayList;
import java.util.List;

public class AI implements IPlayer{

    private Board board;
    private Game game;
    private GUIBoard guiBoard;
    private Piece.Colour colour;
    private int depth;


    public AI(Board board, Game game, GUIBoard guiBoard, Piece.Colour colour, int depth) {
        this.board = board;
        this.game = game;
        this.guiBoard = guiBoard;
        this.colour = colour;
        this.depth = depth;
    }

    @Override
    public Move getMove() {
        //Move move = getBestMove();
        return getBestMove();
    }

    private Move getBestMove(){
        double bestResult = 0;
        Move bestMove = null;

        for (Move move:  board.allAvailableMovesForPlayer(colour)) {
            move.getPiece().move(board, move.getPiece(), move.getTargetX(), move.getTargetY());
            double score = negaMax(new TurnNode(board),Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth, colour);
            board.undoMove(move);
            if( score > bestResult){
                bestResult = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private List<TurnNode> getValidMoves(Board board, Piece.Colour pieceColour){
        List<TurnNode> availableMovesForBoard= new ArrayList<>();
        for (Move move : board.allAvailableMovesForPlayer(pieceColour)) {
            move.getPiece().move(board, move.getPiece(), move.getTargetX(), move.getTargetY());
            availableMovesForBoard.add(new TurnNode(board));
            board.undoMove(move);
        }

        return availableMovesForBoard;
    }

    private double negaMax(TurnNode node, double alpha, double beta, int depth, Piece.Colour pieceColour){
        if (depth == 0){
            StaticEvaluator staticEvaluator = new StaticEvaluator(node);
            return staticEvaluator.rating(depth);
        }
        double bestMove = Double.NEGATIVE_INFINITY;
        for (TurnNode childNode : getValidMoves(node.getBoard(), Game.switchColour(pieceColour))) {
            double v = -negaMax(childNode, -beta, -alpha, depth-1, pieceColour);
            bestMove = Math.max(bestMove, v);
            alpha = Math.max(alpha, v);
            if(alpha >= beta)
                break;
        }
        return bestMove;
    }

}


