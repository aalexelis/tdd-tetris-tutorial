package tetris;

import static java.lang.System.out;

/**
 * Created by andreas on 8/9/14.
 */

public class Tetromino {
    private Piece piece;
    public static Tetromino I_SHAPE = new Tetromino(""+
            ".....\n" +
            ".....\n" +
            "IIII.\n" +
            ".....\n" +
            ".....\n");
    public static Tetromino O_SHAPE = new Tetromino(""+
            ".OO\n" +
            ".OO\n" +
            "...\n");
    public static Tetromino T_SHAPE = new Tetromino(""+
            ".T.\n"+
            "TTT\n"+
            "...\n");
    public static Tetromino J_SHAPE = new Tetromino(""+
            "...\n"+
            "JJJ\n"+
            "..J\n");
    public static Tetromino L_SHAPE = new Tetromino(""+
            "...\n"+
            "LLL\n"+
            "L..\n");
    public static Tetromino S_SHAPE = new Tetromino(""+
            ".SS\n"+
            "SS.\n"+
            "...\n");
    public static Tetromino Z_SHAPE = new Tetromino(""+
            "ZZ.\n"+
            ".ZZ\n"+
            "...\n");

    private Tetromino(String repr) {
        this.piece = new Piece(repr);
    }

    private Tetromino(Piece piece) {
        this.piece = piece;
    }

    public Tetromino rotateRight() {
        return new Tetromino(this.piece.rotateRight());
    }
    public Tetromino rotateLeft() {
        return new Tetromino(this.piece.rotateLeft());
    }
    public String toString() {
        return piece.toString();
    }
}
