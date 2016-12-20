package com.company;

import com.company.AI.AI;

import com.company.GUI.GUIBoard;
import com.company.Logic.Board;
import com.company.Logic.Game;
import com.company.Logic.Pices.Piece;

public class Main implements  Runnable{

    public static void main(String[] args) {
        Board board = new Board();
        Game game = new Game(board);
        GUIBoard GUIBoard = new GUIBoard(board, game);

        board.setGuiBoard(GUIBoard);
        board.setGame(game);

        AI aiPlayer = new AI(board ,game ,GUIBoard,Piece.Colour.WHITE, 3);
        AI aiPlayer2 = new AI(board ,game ,GUIBoard,Piece.Colour.BLACK, 1);


        game.setPlayer(Piece.Colour.WHITE, aiPlayer);
        game.setPlayer(Piece.Colour.BLACK, aiPlayer2);

        new Thread(game).start();

    }


    @Override
    public void run() {

    }
}
