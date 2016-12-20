package com.company.Logic;

import com.company.GUI.GUIBoard;
import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.*;
import java.util.ArrayList;
import java.util.List;

public class Board {


    private GUIBoard guiBoard;
    private Game game;

    private List<Piece> pieces = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
    private List<Piece> blackPieces = new ArrayList<>();
    private List<Piece> whitePieces = new ArrayList<>();
    private List<Move> previousMoves = new ArrayList<>();

    private List<Move> movesAvailableToPlayer = new ArrayList<>();

    public List<Move> getMovesAvailableToPlayer() {
        return movesAvailableToPlayer;
    }

    public void setMovesAvailableToPlayer(List<Move> movesAvailableToPlayer) {
        this.movesAvailableToPlayer = movesAvailableToPlayer;
    }

    public Board() {
        createPieces ();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Piece> getPiecesForColour(Piece.Colour pieceColour){
        if(pieceColour == Piece.Colour.WHITE)
            return whitePieces;
        else if(pieceColour == Piece.Colour.BLACK)
            return blackPieces;
        return null;
    }

    public GUIBoard getGuiBoard() {
        return guiBoard;
    }

    public void setGuiBoard(GUIBoard guiBoard) {
        this.guiBoard = guiBoard;
    }

    public void undoMove(Move move){
        boolean firstMove = false;
        if(move.getPiece() instanceof Pawn && move.getSourceY()==2 || move.getSourceY()==7)
            firstMove = true;
        move.getPiece().setxPos(move.getSourceX());
        move.getPiece().setyPos(move.getSourceY());
        if(move.getCapturedPiece() != null){
            move.getCapturedPiece().setxPos(move.getTargetX());
            move.getCapturedPiece().setyPos(move.getTargetY());
            move.getCapturedPiece().setCaptured(false);
            this.capturedPieces.remove(move.getCapturedPiece());
            this.pieces.add(move.getCapturedPiece());
        }
        move.getPiece().setFirstMove(firstMove);
    }


    public List<Move> allAvailableMovesForPlayer(Piece.Colour pieceColour) {
        movesAvailableToPlayer = new ArrayList<>();
        if (false)//kingInDanger(pieceColour))
        {
            getKing(pieceColour).setCheck(true);
            movesAvailableToPlayer = kingPossibleMoves(pieceColour);
        } else {
            getKing(pieceColour).setCheck(false);
            for (Piece piece : getPiecesForColour(pieceColour)) {
                for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {
                        if (piece.moveValidation(this, piece, piece.getxPos(), piece.getyPos(), i, j)) {
                            Move move = new Move(piece, piece.getxPos(), piece.getyPos(), i, j);
                            if (piece instanceof King){
                                piece.move(this, piece, i, j);
                                if(!kingInDanger(pieceColour))
                                    movesAvailableToPlayer.add(move);
                                undoMove(move);
                            }
                            else {
                                if (isPieceAtLocationCapturable(piece.getColour(), i, j)) {
                                    move.setCapturedPiece(pieceAtLocation(i, j));
                                }
                                movesAvailableToPlayer.add(move);
                            }
                        }
                    }
                }
            }
        }
        return movesAvailableToPlayer;
    }

    public Piece getKing(Piece.Colour pieceColour){
        for (Piece piece : getPiecesForColour(pieceColour)) {
            if(piece instanceof King)
                return piece;
        }
        return null;
    }

    public List<Move> availableMoves(Piece piece){
        List<Move> availableMoves = new ArrayList<>();
        int sourceX = piece.getxPos();
        int sourceY = piece.getyPos();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (piece.moveValidation(this, piece, sourceX, sourceY, i, j)) {
                    Move move = new Move(piece, sourceX, sourceY, i, j);
                    if (isPieceAtLocationCapturable(piece.getColour(), i, j)) {
                        move.setCapturedPiece(pieceAtLocation(i, j));
                    }
                    availableMoves.add(move);
                }
            }
        }

        return availableMoves;
    }

    public boolean kingInDanger (Piece.Colour pieceColour){
        for (Piece piece : getPiecesForColour(Game.switchColour(pieceColour))) {
            if(!piece.isCaptured()) {
                for (Move availableMove : availableMoves(piece)) {
                    if (availableMove.getCapturedPiece() instanceof King) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public List<Move> kingPossibleMoves(Piece.Colour pieceColour){
        List<Move> possibleMoves = new ArrayList<>();
        for (Piece piece : getPiecesForColour(pieceColour)) {
            for (Move move : availableMoves(piece)) {
                piece.move(this, piece, move.getTargetX(), move.getTargetY());
                if(!kingInDanger(pieceColour))
                    possibleMoves.add(move);
                undoMove(move);
            }
        }
        return possibleMoves;
    }


    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public boolean isPieceAtLocationCapturable(Piece.Colour pieceColour, int x, int y){
        for(Piece piece: pieces) {
            if (piece.getxPos() == x && piece.getyPos() == y && piece.getColour() != pieceColour) {
                return true;
            }
        }
        return false;
    }


    public boolean isPieceAtLocation(int x, int y){
        for(Piece piece: pieces){
            if(piece.getxPos() == x && piece.getyPos() == y )
                return true;
        }
        return false;
    }

    public Piece pieceAtLocation(int x, int y){
        for(Piece piece: pieces){
            if(piece.getxPos() == x && piece.getyPos() == y)
                return piece;
        }
        return null;
    }

    public boolean isPieceBetweenLocations(int sourceX, int sourceY, int targetX, int targetY, int xChange, int yChange){
        while (sourceX != targetX || sourceY != targetY){
            sourceX += xChange;
            sourceY += yChange;
            if (isPieceAtLocation(sourceX, sourceY) && (sourceX != targetX || sourceY != targetY) )
                return true;
        }
        return false;
    }


    private Bishop createBishop (boolean isCaptured, int xPos, int yPos, Piece.Colour colour){
        return new Bishop(isCaptured, xPos, yPos, colour);
    }

    private King createKing (boolean isCaptured, int xPos, int yPos, Piece.Colour colour){
        return new King(isCaptured, xPos, yPos, colour);
    }

    private Knight createKnight (boolean isCaptured, int xPos, int yPos, Piece.Colour colour){
        return new Knight(isCaptured, xPos, yPos, colour);
    }

    private Pawn createPawn (boolean isCaptured, int xPos, int yPos, Piece.Colour colour){
        return new Pawn(isCaptured, xPos, yPos, colour);
    }

    private Queen createQueen (boolean isCaptured, int xPos, int yPos, Piece.Colour colour){
        return new Queen(isCaptured, xPos, yPos, colour);
    }

    private Rook createRook (boolean isCaptured, int xPos, int yPos, Piece.Colour colour){
        return new Rook(isCaptured, xPos, yPos, colour);
    }

    private void createPieces(){
        whitePieces.add(createBishop(false, 3,8, Piece.Colour.WHITE));
        whitePieces.add(createBishop(false, 6,8, Piece.Colour.WHITE));
        whitePieces.add(createKing(false, 4,8, Piece.Colour.WHITE));
        whitePieces.add(createRook(false, 1,8, Piece.Colour.WHITE));
        whitePieces.add(createRook(false, 8,8, Piece.Colour.WHITE));
        whitePieces.add(createKnight(false, 2,8, Piece.Colour.WHITE));
        whitePieces.add(createKnight(false, 7,8, Piece.Colour.WHITE));
        whitePieces.add(createQueen(false, 5,8, Piece.Colour.WHITE));
        blackPieces.add(createRook(false, 1,1, Piece.Colour.BLACK));
        blackPieces.add(createRook(false, 8,1, Piece.Colour.BLACK));
        blackPieces.add(createKnight(false, 2,1, Piece.Colour.BLACK));
        blackPieces.add(createKnight(false, 7,1, Piece.Colour.BLACK));
        blackPieces.add(createBishop(false, 3,1, Piece.Colour.BLACK));
        blackPieces.add(createBishop(false, 6,1, Piece.Colour.BLACK));
        blackPieces.add(createKing(false, 4,1, Piece.Colour.BLACK));
        blackPieces.add(createQueen(false, 5,1, Piece.Colour.BLACK));
        for (int i = 0; i<8;i++){
            whitePieces.add(createPawn(false, i+1,7, Piece.Colour.WHITE));
            blackPieces.add(createPawn(false, i+1,2, Piece.Colour.BLACK));
        }
        pieces.addAll(whitePieces);
        pieces.addAll(blackPieces);
    }



    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }
}
