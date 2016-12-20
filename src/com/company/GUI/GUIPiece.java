package com.company.GUI;

import com.company.Logic.Pices.*;

import javax.swing.*;
import java.awt.*;


public class GUIPiece {

    private static final Image BLACK_BISHOP = new ImageIcon("Chess Pieces/BBishop.png").getImage();
    private static final Image BLACK_KING = new ImageIcon("Chess Pieces/BKing.png").getImage();
    private static final Image BLACK_KNIGHT= new ImageIcon("Chess Pieces/BKnight.png").getImage();
    private static final Image BLACK_PAWN = new ImageIcon("Chess Pieces/BPawn.png").getImage();
    private static final Image BLACK_QUEEN= new ImageIcon("Chess Pieces/BQueen.png").getImage();
    private static final Image BLACK_ROOK = new ImageIcon("Chess Pieces/BRook.png").getImage();
    private static final Image WHITE_BISHOP = new ImageIcon("Chess Pieces/WBishop.png").getImage();
    private static final Image WHITE_KING = new ImageIcon("Chess Pieces/WKing.png").getImage();
    private static final Image WHITE_KNIGHT = new ImageIcon("Chess Pieces/WKnight.png").getImage();
    private static final Image WHITE_PAWN = new ImageIcon("Chess Pieces/WPawn.png").getImage();
    private static final Image WHITE_QUEEN = new ImageIcon("Chess Pieces/WQueen.png").getImage();
    private static final Image WHITE_ROOK = new ImageIcon("Chess Pieces/WRook.png").getImage();


    private final int HEIGHT =60, WIDTH =60;//TODO make variable as size of board changes
    private Piece piece;
    private Image pieceImage;
    private int X, Y;



    GUIPiece(Piece piece, Image pieceImage, int X, int Y) {
        this.piece = piece;
        this.pieceImage = pieceImage;
        this.X = X;
        this.Y = Y;
    }

    Image getPieceImage() {
        return pieceImage;
    }

    public void setPieceImage(Image pieceImage) {
        this.pieceImage = pieceImage;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public Piece getPiece() {
        return piece;
    }

    int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public static Image getImageForPiece (Piece piece, Piece.Colour colour){
        if(piece instanceof Bishop){
            if (colour == Piece.Colour.BLACK)
                return BLACK_BISHOP;
            else
                return WHITE_BISHOP;
        }
        if(piece instanceof King){
            if (colour == Piece.Colour.BLACK)
                return BLACK_KING;
            else
                return WHITE_KING;
        }
        if(piece instanceof Knight){
            if (colour == Piece.Colour.BLACK)
                return BLACK_KNIGHT;
            else
                return WHITE_KNIGHT;
        }
        if(piece instanceof Pawn){
            if (colour == Piece.Colour.BLACK)
                return BLACK_PAWN;
            else
                return WHITE_PAWN;
        }
        if(piece instanceof Queen){
            if (colour == Piece.Colour.BLACK)
                return BLACK_QUEEN;
            else
                return WHITE_QUEEN;
        }
        if(piece instanceof Rook){
            if (colour == Piece.Colour.BLACK)
                return BLACK_ROOK;
            else
                return WHITE_ROOK;
        }
        return null;
    }

}
