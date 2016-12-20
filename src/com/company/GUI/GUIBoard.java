package com.company.GUI;


import com.company.Logic.Game;
import com.company.Logic.Board;
import com.company.Logic.IPlayer;
import com.company.Logic.MoveTypes.Move;
import com.company.Logic.Pices.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUIBoard extends JPanel implements IPlayer{

    private static final Image BACKGROUND = new ImageIcon("Board.jpg").getImage();
    private static final Image AVAILABLE_SQUARE = new ImageIcon("AvailableSquare.png").getImage();
    private static final Image CHECK_SQUARE = new ImageIcon("CheckSquare.png").getImage();

    private Board board;
    private Game game;
    private AffineTransform transform;//TODO if AI playing don't switch board
    private Move lastMove, currentMove;
    private List<Move> currentAvailableMovesForPiece= new ArrayList<>();
    private List<Move> availableToPlayer = new ArrayList<>();
    PieceActionListener listener;


    static final int BOARD_START = 5;
    static final int SQUARE_SIZE = 75; //TODO be variable is game size changes ?

    private List<GUIPiece> whiteGUIPieces = new ArrayList<>();
    private List<GUIPiece> blackGUIPieces = new ArrayList<>();
    private List<GUIPiece> allGUIPieces = new ArrayList<>();

    private int rotation;

    public GUIBoard(Board board, Game game) {
        this.game = game;
        this.board = board;
        addPieces();
        listener = new PieceActionListener(this.allGUIPieces, this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        transform = new AffineTransform();

        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600,620));
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void startTurn(List<Move> availableToPlayer){
        this.setAvailableToPlayer(availableToPlayer);
    }

    public void setAvailableToPlayer(List<Move> availableToPlayer) {
        this.availableToPlayer = availableToPlayer;
    }

    void findAvailableMovesForPiece(Piece piece){
        for (Move move : availableToPlayer) {
            if(move.getPiece().equals(piece)){
                currentAvailableMovesForPiece.add(move);
            }
        }
        rotation = 0;
    }

    public Board getBoard() {
        return board;
    }

    Game getGame() {
        return game;
    }

    AffineTransform getTransform() {
        return transform;
    }

    private int coordinateToPixel(int column) {
        return BOARD_START + SQUARE_SIZE * (column - 1);
    }

    private int pixelToCoordinate(double position) {
        return (int) Math.ceil(position / GUIBoard.SQUARE_SIZE);
    }


    private void addPieces() {
        whiteGUIPieces.addAll(board.getWhitePieces().stream().map(piece ->
                createGUIPieces(piece, GUIPiece.getImageForPiece(piece, piece.getColour()), coordinateToPixel(piece.getxPos()), coordinateToPixel(piece.getyPos()))).collect(Collectors.toList()));
        blackGUIPieces.addAll(board.getBlackPieces().stream().map(piece ->
                createGUIPieces(piece, GUIPiece.getImageForPiece(piece, piece.getColour()), coordinateToPixel(piece.getxPos()), coordinateToPixel(piece.getyPos()))).collect(Collectors.toList()));
        allGUIPieces.addAll(whiteGUIPieces);
        allGUIPieces.addAll(blackGUIPieces);
    }

    private GUIPiece createGUIPieces(Piece piece, Image image, int x, int y) {
        return new GUIPiece(piece, image, x, y);
    }

    void removeCapturedPieces() {
        whiteGUIPieces.clear();
        blackGUIPieces.clear();
        for (GUIPiece GUIPiece : allGUIPieces) {
            if (GUIPiece.getPiece().getColour() == Piece.Colour.WHITE && !GUIPiece.getPiece().isCaptured())
                whiteGUIPieces.add(GUIPiece);
            else if (GUIPiece.getPiece().getColour() == Piece.Colour.BLACK && !GUIPiece.getPiece().isCaptured())
                blackGUIPieces.add(GUIPiece);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        removeCapturedPieces();
        Graphics2D g2d = (Graphics2D) g;
        transform.rotate(Math.toRadians(rotation), this.getWidth() / 2, this.getHeight() / 2);
        g2d.setTransform(transform);
        g2d.drawImage(BACKGROUND,0,0, null);
        for (GUIPiece whiteGUIPiece : whiteGUIPieces) {
            if(whiteGUIPiece.getPiece() instanceof King && whiteGUIPiece.getPiece().isCheck())
                g2d.drawImage(CHECK_SQUARE,coordinateToPixel(whiteGUIPiece.getPiece().getxPos())-6, coordinateToPixel(whiteGUIPiece.getPiece().getyPos())-6, null);
            g2d.drawImage(whiteGUIPiece.getPieceImage(), coordinateToPixel(whiteGUIPiece.getPiece().getxPos()), coordinateToPixel(whiteGUIPiece.getPiece().getyPos()), null);
        }
        for (GUIPiece blackGUIPiece : blackGUIPieces) {
            if(blackGUIPiece.getPiece() instanceof King && blackGUIPiece.getPiece().isCheck()) {
                System.out.println(currentAvailableMovesForPiece.size());
                for (Move move : currentAvailableMovesForPiece) {
                    System.out.println(move.toString());
                }
                g2d.drawImage(CHECK_SQUARE, coordinateToPixel(blackGUIPiece.getPiece().getxPos()) - 6, coordinateToPixel(blackGUIPiece.getPiece().getyPos()) - 6, null);
            }
            g2d.drawImage(blackGUIPiece.getPieceImage(), coordinateToPixel(blackGUIPiece.getPiece().getxPos()), coordinateToPixel(blackGUIPiece.getPiece().getyPos()), null);
        }
        if(!currentAvailableMovesForPiece.isEmpty()){
            for (Move move : currentAvailableMovesForPiece) {
                g2d.drawImage(AVAILABLE_SQUARE, coordinateToPixel(move.getTargetX())-3, coordinateToPixel(move.getTargetY())-4, null);
            }
        }
        g2d.getTransform();
    }


    public void moveGUIPiece(GUIPiece piece, int targetX, int targetY){
        int x = pixelToCoordinate(targetX);
        int y = pixelToCoordinate(targetY);
         Move attemptedMove = new Move(piece.getPiece(), piece.getPiece().getxPos(), piece.getPiece().getyPos(), x, y);
        for (Move move : board.allAvailableMovesForPlayer(piece.getPiece().getColour())) {
            if(move.isEqual(attemptedMove)) {
                currentMove = move;
                piece.setX(listener.getPixelCoordinate(targetX));
                piece.setY(listener.getPixelCoordinate(targetY));
            }
        }
    }

    public List<Move> getCurrentAvailableMovesForPiece() {return currentAvailableMovesForPiece;}

    @Override
    public Move getMove() {
        Move moveForExecution = this.currentMove;
        this.currentMove = null;
        return moveForExecution;
    }



    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}


