package com.company.GUI;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.List;


class PieceActionListener implements MouseListener, MouseMotionListener {

    private List<GUIPiece> pieces;

    private GUIBoard board;
    private GUIPiece selectedPiece;
    private boolean pieceSelected;
    private Point2D p;


    PieceActionListener(List<GUIPiece> pieces, GUIBoard board) {
        this.pieces = pieces;
        this.board = board;
    }

    private boolean mouseOverPiece(GUIPiece piece, int x, int y) {
        return (piece.getX() <= x && piece.getX() + piece.getWIDTH() >= x
                && piece.getY() <= y && piece.getY()+ piece.getHEIGHT() >= y);
    }

    //TODO will always now be clicking on the bottom of the board.

    int getPixelCoordinate(double coordinate){
        coordinate = Math.ceil(coordinate/GUIBoard.SQUARE_SIZE);
        switch ((int)coordinate){
            case(1):
                return GUIBoard.BOARD_START;
            case(2):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE;
            case(3):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE*2;
            case(4):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE*3;
            case(5):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE*4;
            case(6):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE*5;
            case(7):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE*6;
            case(8):
                return GUIBoard.BOARD_START+GUIBoard.SQUARE_SIZE*7;
        }
        return 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        p = board.getTransform().transform(new Point2D.Double(e.getX(), e.getY()), null);
        board.getCurrentAvailableMovesForPiece().clear();
        if(selectedPiece == null) {
            for (int i = this.pieces.size() - 1; i >= 0; i--) {
                GUIPiece piece = this.pieces.get(i);
                if (mouseOverPiece(piece, (int)p.getX(), (int)p.getY())) {
                    if(piece.getPiece().getColour() == board.getGame().turn()) {
                        this.selectedPiece = piece;
                        this.pieces.add(this.selectedPiece);
                        board.findAvailableMovesForPiece(selectedPiece.getPiece());
                        board.repaint();
                        break;
                    }
                }
            }
        }
        else {
            if(selectedPiece.getPiece().getColour() == board.getGame().turn()) {
                this.pieces.remove(this.selectedPiece);

                board.moveGUIPiece(selectedPiece, (int)p.getX(), (int)p.getY());
                //board.repaint();
            }
            selectedPiece = null;
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}


