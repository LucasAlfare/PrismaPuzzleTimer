package lucas.main;//package teste_com_cstimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FullCube implements Comparable<FullCube> {

    static Random r = new Random();
    int ul = 0x011233;
    int ur = 0x455677;
    int dl = 0x998bba;
    int dr = 0xddcffe;
    int ml = 0;
    int[] arr = new int[16];
    byte[] prm = new byte[8];

    FullCube(String s) {
        //TODO
    }

    public FullCube() {

    }

    public static FullCube randomCube() {
        return randomCube(r);
    }

    @Deprecated
    public static FullCube randomCube(NovosShapes algum) {
        int shape = Shape.ShapeIdx[algum.randomIndex()];
        FullCube f = new FullCube();
        int corner = 0x01234567 << 1 | 0x11111111;
        int edge = 0x01234567 << 1;
        int n_corner = 8, n_edge = 8;
        buildRandomCube(r, shape, f, corner, edge, n_corner, n_edge);
        f.ml = r.nextInt(2);
        return f;
    }

    public static FullCube randomCube(CubeShapeIndex algum) {
        int shape = Shape.ShapeIdx[algum.randomIndex()];
        FullCube f = new FullCube();
        int corner = 0x01234567 << 1 | 0x11111111;
        int edge = 0x01234567 << 1;
        int n_corner = 8, n_edge = 8;
        buildRandomCube(r, shape, f, corner, edge, n_corner, n_edge);
        f.ml = r.nextInt(2);
        return f;
    }

    public static FullCube randomCube(Random r) {
        int shape = Shape.ShapeIdx[r.nextInt(3678)];
        FullCube f = new FullCube();
        int corner = 0x01234567 << 1 | 0x11111111;
        int edge = 0x01234567 << 1;
        int n_corner = 8, n_edge = 8;
        buildRandomCube(r, shape, f, corner, edge, n_corner, n_edge);
        f.ml = r.nextInt(2);
        return f;
    }

    public static FullCube randomCube(Random r, int indiceDoShape) {
        int shape = Shape.ShapeIdx[indiceDoShape];
        FullCube f = new FullCube();
        int corner = 0x01234567 << 1 | 0x11111111;
        int edge = 0x01234567 << 1;
        int n_corner = 8, n_edge = 8;
        buildRandomCube(r, shape, f, corner, edge, n_corner, n_edge);
        f.ml = r.nextInt(2);
        return f;
    }

    private static void buildRandomCube(Random r, int shape, FullCube f, int corner, int edge, int n_corner, int n_edge) {
        int rnd;
        int m;
        for (int i = 0; i < 24; i++) {
            if (((shape >> i) & 1) == 0) {//edge
                rnd = r.nextInt(n_edge) << 2;
                f.setPiece(23 - i, (edge >> rnd) & 0xf);
                m = (1 << rnd) - 1;
                edge = (edge & m) + ((edge >> 4) & ~m);
                --n_edge;
            } else {//corner
                rnd = r.nextInt(n_corner) << 2;
                f.setPiece(23 - i, (corner >> rnd) & 0xf);
                f.setPiece(22 - i, (corner >> rnd) & 0xf);
                m = (1 << rnd) - 1;
                corner = (corner & m) + ((corner >> 4) & ~m);
                --n_corner;
                ++i;
            }
        }
    }

    public static FullCube customCube() {
        return null;
    }

    @Override
    public int compareTo(FullCube f) {
        if (ul != f.ul) {
            return ul - f.ul;
        }
        if (ur != f.ur) {
            return ur - f.ur;
        }
        if (dl != f.dl) {
            return dl - f.dl;
        }
        if (dr != f.dr) {
            return dr - f.dr;
        }
        return ml - f.ml;
    }

    public FullCube custom(String topo, String base) {
        String cs_s = topo.replaceAll("11", "1").concat(base.replaceAll("11", "1")).replaceAll(" ", "");
        byte[] bins = new byte[cs_s.length()];
        for (int i = 0; i < bins.length; i++) {
            bins[i] = Byte.parseByte(cs_s.charAt(i) + "");
        }

        ArrayList<Byte> p = new ArrayList<>();
        FullCube custom = new FullCube();
        byte[] meios = {0, 2, 4, 6, 8, 10, 12, 14};
        byte[] cantos = {1, 3, 5, 7, 9, 11, 13, 15};

        byte meio = 0;
        byte canto = 0;
        int i = 0;
        for (/*pass*/; p.size() < 12; i++) {
            if (bins[i] % 2 == 0) {
                p.add(meios[meio]);
                meio++;
            } else {
                p.add(cantos[canto]);
                p.add(cantos[canto]);
                canto++;
            }
        }

        for (/*pass*/; i < bins.length; i++) {
            if (bins[i] % 2 == 0) {
                p.add(meios[meio]);
                meio++;
            } else {
                p.add(cantos[canto]);
                p.add(cantos[canto]);
                canto++;
            }
        }

        Collections.swap(p, 12, 14);
        Collections.swap(p, 15, 17);
        Collections.swap(p, 18, 20);
        Collections.swap(p, 21, 23);

        System.out.println(p);

        return custom;
    }

    void copy(FullCube c) {
        this.ul = c.ul;
        this.ur = c.ur;
        this.dl = c.dl;
        this.dr = c.dr;
        this.ml = c.ml;
    }

    /**
     * Applies a move to a cube face (true for top and false to bottom).
     * The {@code move} param is taked as we have in standard scrambles.
     * <p>
     * Example:
     * (6, -5) -> move(true, 6) | move(false, -5)
     *
     * @param face The face you want to move.
     * @param move the value of the move you want to do.
     */
    public void move(boolean face, int move) {
        this.doMove(face ? (move > 0 ? move : 12 - (move * -1)) : (move > 0 ? move : 12 - (move * -1)) * -1);
    }

    /**
     * Applies a twist (slash "/") to current cube.
     */
    public void twist() {
        if (this.isTwistable()) {
            this.doMove(0);
        }
    }

    /**
     * @param move 0 = twist
     *             [1, 11] = top move
     *             [-1, -11] = bottom move
     *             for example, 6 == (6, 0), 9 == (-3, 0), -4 == (0, 4)
     */
    void doMove(int move) {
        move <<= 2;
        if (move > 24) {
            move = 48 - move;
            int temp = ul;
            ul = (ul >> move | ur << (24 - move)) & 0xffffff;
            ur = (ur >> move | temp << (24 - move)) & 0xffffff;
        } else if (move > 0) {
            int temp = ul;
            ul = (ul << move | ur >> (24 - move)) & 0xffffff;
            ur = (ur << move | temp >> (24 - move)) & 0xffffff;
        } else if (move == 0) {
            int temp = ur;
            ur = dl;
            dl = temp;
            ml = 1 - ml;
        } else if (move >= -24) {
            move = -move;
            int temp = dl;
            dl = (dl << move | dr >> (24 - move)) & 0xffffff;
            dr = (dr << move | temp >> (24 - move)) & 0xffffff;
        } else if (move < -24) {
            move = 48 + move;
            int temp = dl;
            dl = (dl >> move | dr << (24 - move)) & 0xffffff;
            dr = (dr >> move | temp << (24 - move)) & 0xffffff;
        }
    }

    public byte pieceAt(int idx) {
        int ret;
        if (idx < 6) {
            ret = ul >> ((5 - idx) << 2);
        } else if (idx < 12) {
            ret = ur >> ((11 - idx) << 2);
        } else if (idx < 18) {
            ret = dl >> ((17 - idx) << 2);
        } else {
            ret = dr >> ((23 - idx) << 2);
        }
        return (byte) (ret & 0x0f);
    }

    public void setPiece(int idx, int value) {
        if (idx < 6) {
            ul &= ~(0xf << ((5 - idx) << 2));
            ul |= value << ((5 - idx) << 2);
        } else if (idx < 12) {
            ur &= ~(0xf << ((11 - idx) << 2));
            ur |= value << ((11 - idx) << 2);
        } else if (idx < 18) {
            dl &= ~(0xf << ((17 - idx) << 2));
            dl |= value << ((17 - idx) << 2);
        } else if (idx < 24) {
            dr &= ~(0xf << ((23 - idx) << 2));
            dr |= value << ((23 - idx) << 2);
        } else {
            ml = value;
        }
    }

    int getParity() {
        int cnt = 0;
        arr[0] = pieceAt(0);
        for (int i = 1; i < 24; i++) {
            if (pieceAt(i) != arr[cnt]) {
                arr[++cnt] = pieceAt(i);
            }
        }
        int p = 0;
        for (int a = 0; a < 16; a++) {
            for (int b = a + 1; b < 16; b++) {
                if (arr[a] > arr[b]) {
                    p ^= 1;
                }
            }
        }
        return p;
    }

    boolean isTwistable() {
        return pieceAt(0) != pieceAt(11)
                && pieceAt(5) != pieceAt(6)
                && pieceAt(12) != pieceAt(23)
                && pieceAt(17) != pieceAt(18);
    }

    int getShapeIdx() {
        int urx = ur & 0x111111;
        urx |= urx >> 3;
        urx |= urx >> 6;
        urx = (urx & 0xf) | ((urx >> 12) & 0x30);
        int ulx = ul & 0x111111;
        ulx |= ulx >> 3;
        ulx |= ulx >> 6;
        ulx = (ulx & 0xf) | ((ulx >> 12) & 0x30);
        int drx = dr & 0x111111;
        drx |= drx >> 3;
        drx |= drx >> 6;
        drx = (drx & 0xf) | ((drx >> 12) & 0x30);
        int dlx = dl & 0x111111;
        dlx |= dlx >> 3;
        dlx |= dlx >> 6;
        dlx = (dlx & 0xf) | ((dlx >> 12) & 0x30);
        return Shape.getShape2Idx(getParity() << 24 | ulx << 18 | urx << 12 | dlx << 6 | drx);
    }

    void print() {
        System.out.println(Integer.toHexString(ul));
        System.out.println(Integer.toHexString(ur));
        System.out.println(Integer.toHexString(dl));
        System.out.println(Integer.toHexString(dr));
    }

    void getSquare(Square sq) {
        for (int a = 0; a < 8; a++) {
            prm[a] = (byte) (pieceAt(a * 3 + 1) >> 1);
        }
        //convert to number
        sq.cornperm = Square.get8Perm(prm);

        int a, b;
        //Strip top layer edges
        sq.topEdgeFirst = pieceAt(0) == pieceAt(1);
        a = sq.topEdgeFirst ? 2 : 0;
        for (b = 0; b < 4; a += 3, b++) {
            prm[b] = (byte) (pieceAt(a) >> 1);
        }

        sq.botEdgeFirst = pieceAt(12) == pieceAt(13);
        a = sq.botEdgeFirst ? 14 : 12;

        for (; b < 8; a += 3, b++) {
            prm[b] = (byte) (pieceAt(a) >> 1);
        }
        sq.edgeperm = Square.get8Perm(prm);
        sq.ml = ml;
    }

    /**
     * Returns a "binary" representation to this cube.
     * Each edge is a 0 and each corner is a 1.
     * This is usefull to shape analysis.
     * <p>
     * The pieces from top are represented from UB edge to UBL corner (clock-wise).
     * The pieces from bottom are represented from DF edge to DFL corner (clock-wise).
     *
     * @param face the face you want to see binary representation.
     * @return a binary representation of a square-1 face.
     */
    public String binString(boolean face) {
        String r = "";

        r += pieceAt(face ? 0 : 17) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 11 : 16) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 10 : 15) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 9 : 14) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 8 : 13) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 7 : 12) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 6 : 23) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 5 : 22) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 4 : 21) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 3 : 20) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 2 : 19) % 2 == 0 ? 0 : 1;
        r += pieceAt(face ? 1 : 18) % 2 == 0 ? 0 : 1;

        return r;
    }

    @Override
    public String toString() {

        /*
         * COMEÇA A CONTAR NA PEÇA WO E VAI INDO ANTIHORÁRIO
         *
         * 0 = WO
         * 1 = WOG
         * 2 = WG
         * 3 = WGR
         * 4 = WR
         * 5 = WRB
         * 6 = WB
         * 7 = WBO
         *
         * 8 = YB
         * 9 = YBO
         * 10 = YR
         * 11 = YRB
         * 12 = YG
         * 13 = YRG
         * 14 = YO
         * 15 = YGO
         */

        ArrayList<Byte> aux = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            aux.add(pieceAt(i));
        }

        return aux.toString();
        //return fullBin();
    }
}
