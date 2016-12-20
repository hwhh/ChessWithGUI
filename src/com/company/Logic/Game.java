package com.company.Logic;


import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.King;
import com.company.Logic.Pices.Piece;
import com.company.Logic.Pices.Piece.Colour;
import org.jetbrains.annotations.Contract;


import static com.company.Logic.Pices.Piece.Colour.BLACK;
import static com.company.Logic.Pices.Piece.Colour.WHITE;

public class Game implements Runnable{


    private final Board board;
    private IPlayer blackPlayerHandler;
    private IPlayer whitePlayerHandler;
    private IPlayer activePlayerHandler;

    private boolean check, staleMate, checkMate;

    private int gameState = GAME_STATE_WHITE;//TODO change game states to colours
    public static final int GAME_STATE_WHITE = -1;
    public static final int GAME_STATE_BLACK = 1;

    public boolean whiteWon = false;
    public boolean blackWon = false;



    public Game(Board board) {
        this.board = board;
        this.check = false;
        this.staleMate = false;
        this.checkMate = false;
    }


    public void changeGameState() {
       setGameState(-getGameState());
    }

    public void setPlayer (Colour playerColour, IPlayer player){
        switch(playerColour){
            case BLACK:this.blackPlayerHandler = player;
                break;
            case WHITE:this.whitePlayerHandler = player;
                break;
            case NULL:break;
        }
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }

    public Colour turn(){
        switch(gameState){
            case -1:return WHITE;
            case 1:return BLACK;
        }
        return Colour.NULL;
    }

    @Override
    public void run() {
        startGame();
    }

    public void startGame() {
        while (this.blackPlayerHandler == null || this.whitePlayerHandler == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
        this.activePlayerHandler = this.whitePlayerHandler;
        while (!isGameEndConditionReached()) {
            board.setMovesAvailableToPlayer(board.allAvailableMovesForPlayer(turn()));
            board.getGuiBoard().setAvailableToPlayer(board.getMovesAvailableToPlayer());
            waitForMoveExecution();
            swapActivePlayer();
        }
    }

    private void waitForMoveExecution() {
        Move move = null;
        // wait for a valid move
        do{
            move = this.activePlayerHandler.getMove();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        }while(move == null);
        //execute the move
        move.getPiece().move(board, move.getPiece(), move.getTargetX(), move.getTargetY());
        board.getGuiBoard().setRotation(180);
        board.getGuiBoard().repaint();

    }


    private void swapActivePlayer() {
        if( this.activePlayerHandler == this.whitePlayerHandler ){
            this.activePlayerHandler = this.blackPlayerHandler;
        }else{
            this.activePlayerHandler = this.whitePlayerHandler;
        }
        this.changeGameState();

    }


    private boolean isGameEndConditionReached() {//TODO add checkmate
        for (Piece piece : board.getCapturedPieces()) {
            if(piece instanceof King && piece.getColour() == Colour.WHITE)
                return whiteWon = true;
            if(piece instanceof King && piece.getColour() == Colour.BLACK)
                return blackWon = true;
        }
        return false;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isStaleMate() {
        return staleMate;
    }

    public static Piece.Colour switchColour(Piece.Colour colour){
        if(colour == Piece.Colour.BLACK)
            return Piece.Colour.WHITE;
        if(colour == Piece.Colour.WHITE)
            return Piece.Colour.BLACK;
        return Piece.Colour.NULL;
    }
}
