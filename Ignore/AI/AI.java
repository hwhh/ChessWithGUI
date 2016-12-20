package com.company.AI;

import com.company.GUI.GUIBoard;
import com.company.Logic.Board;
import com.company.Logic.Game;
import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.Piece;

public abstract class AI {

    public Board board;
    Game game;
    GUIBoard guiBoard;
    Piece.Colour playerColour;
    int movesAhead;

    public AI(Board board, Game game, GUIBoard guiBoard, Piece.Colour playerColour, int movesAhead) {
        this.board = board;
        this.game = game;
        this.guiBoard = guiBoard;
        this.playerColour = playerColour;
        this.movesAhead = movesAhead;
    }

    void undoMove(Move move) {
        this.board.undoMove(move);
        this.game.changeGameState();
    }


    void executeMove(Piece piece, Move move) {
        piece.move(board, piece, move.getTargetX(), move.getTargetY());
        this.game.changeGameState();
    }




}
