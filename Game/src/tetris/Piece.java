package tetris;

import java.awt.*;

public class Piece {
    public Point[][] getPiece() {
        return Piece;
    }

    public void setPiece(Point[][] piece) {
        Piece = piece;
    }

    private Point[][] Piece;

    public Piece(int type) {
        switch (type) {
            case 0:
                Piece = new Point[][]{
                        { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 1), new Point(2, 1) },
                        { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 0), new Point(1, 2) },
                        { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 1), new Point(2, 1) },
                        { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 0), new Point(1, 2) }
                };
                break;
            case 1:
                Piece = new Point[][]{
                        { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                        { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
                        { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
                        { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
                };
                break;
            case 2:
                Piece = new Point[][]{
                        { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                        { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                        { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                        { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
                };
                break;
            case 3:
                Piece = new Point[][]{
                        { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                        { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                        { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                        { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
                };
                break;
            case 4:
                Piece = new Point[][]{
                        { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                        { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                        { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                        { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
                };
                break;
            case 5:
                Piece = new Point[][]{
                        { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0), new Point(1,2), new Point(2,0), new Point(2,2) },
                        { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0), new Point(2,1), new Point(2,0), new Point(2,2) },
                        { new Point(0, 0), new Point(2, 1), new Point(0, 2), new Point(1, 0), new Point(1,2), new Point(2,0), new Point(2,2) },
                        { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(2, 1), new Point(1,2), new Point(2,0), new Point(2,2) }
                };
                break;
            case 6:
                Piece = new Point[][]{
                        { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                        { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
                        { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                        { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
                };
                break;
            case 7:
                Piece = new Point[][]{
                        { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
                        { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0) },
                        { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
                        { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0) }
                };
                break;
            case 8:
                Piece = new Point[][]{
                        { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(1, 1), new Point(1,2) },
                        { new Point(2, 0), new Point(2, 1), new Point(0, 1), new Point(1, 1), new Point(2,2) },
                        { new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(1, 1), new Point(1,0) },
                        { new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(1, 1), new Point(2,1) },
                };
                break;
        }
        this.Piece = Piece;
    }


}
