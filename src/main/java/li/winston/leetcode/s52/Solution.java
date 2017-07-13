package li.winston.leetcode.s52;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by winston on 13/07/2017.
 */
public class Solution {

    public List<List<String>> solveNQueens(int n) {
        return solveNQueens_(n);
    }

    public int totalNQueens(int n) {
        return solveNQueens(n).size();
    }

    static class Board {

        private final byte[] bitvec;

        private final int width;
        private final int height;

        private Board(byte[] bitvec, int width, int height) {
            this.bitvec = bitvec;
            this.width = width;
            this.height = height;
        }

        Board(int width, int height) {
            this(new byte[(width * height + 7) / 8], width, height);
        }

        Board(Board cp) {
            this(Arrays.copyOf(cp.bitvec, cp.bitvec.length),
                    cp.width, cp.height);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Board)) return false;
            Board board = (Board) o;
            return Arrays.equals(bitvec, board.bitvec);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(bitvec);
        }

        @Override
        public String toString() {
            return String.join("\n", toList()) + '\n';
        }

        void set(int x, int y, boolean val) {
            byte m = mask(x, y);
            if (val) {
                bitvec[toArrIdx(x, y)] |= m;
            } else {
                bitvec[toArrIdx(x, y)] &= ~m;
            }
        }

        boolean get(int x, int y) {
            return (bitvec[toArrIdx(x, y)] & mask(x, y)) != 0;
        }

        List<String> toList() {
            List<String> ret = new ArrayList<>(height);
            for (int y = 0; y < height; ++y) {
                StringBuilder row = new StringBuilder(width);
                for (int x = 0; x < width; ++x) {
                    row.append(get(x, y) ? 'Q' : '.');
                }
                ret.add(row.toString());
            }
            return ret;
        }

        int toIdx(int x, int y) {
            return width * y + x;
        }

        int toArrIdx(int x, int y) {
            return toIdx(x, y) / 8;
        }

        byte mask(int x, int y) {
            return (byte) (1 << (toIdx(x, y) % 8));
        }

    }

    static List<List<String>> solveNQueens_(int n) {
        return solveNQueens__(n)
                .stream()
                .map(Board::toList)
                .collect(Collectors.toList());
    }

    static List<Board> solveNQueens__(int n) {
        return solveNQueens__(0, n, new Board(n, n), new Board(n, n));
    }

    static List<Board> solveNQueens__(
            int row, int n,
            Board placed, Board attacked
    ) {
        if (row == n) return Collections.singletonList(placed);
        List<Board> result = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (attacked.get(i, row)) {
                continue;
            }
            Board placed_ = new Board(placed);
            Board attacked_ = new Board(attacked);
            placeQueen(placed_, attacked_, i, row);
            result.addAll(solveNQueens__(row + 1, n, placed_, attacked_));
        }
        return result;
    }

    static void placeQueen(Board placed, Board attacked, int x, int y) {
        int n = getN(placed, attacked);
        setIfValid(placed, x, y, true);
        for (int i = 0; i < n; ++i) {
            setIfValid(attacked, i, y, true);
            setIfValid(attacked, x, i, true);
            setIfValid(attacked, x + i, y + i, true);
            setIfValid(attacked, x + i, y - i, true);
            setIfValid(attacked, x - i, y - i, true);
            setIfValid(attacked, x - i, y + i, true);
        }
    }

    static void setIfValid(Board b, int x, int y, boolean val) {
        if (b == null) return;
        if (x < 0) return;
        if (y < 0) return;
        if (x >= b.width) return;
        if (y >= b.height) return;
        b.set(x, y, val);
    }

    static int getN(Board b, Board b_) {
        int n = 0;
        if (b != null) {
            n = Math.max(n, b.width);
            n = Math.max(n, b.height);
        }
        if (b_ != null) {
            n = Math.max(n, b_.width);
            n = Math.max(n, b_.height);
        }
        return n;
    }

}
