package li.winston.leetcode.s51;

import org.junit.Test;

import static li.winston.leetcode.s51.Solution.*;

/**
 * Created by winston on 10/07/2017.
 */
public class SolutionTest {

    Board queens = new Board(8, 8);

    Board attacked = new Board(8, 8);

    @Test
    public void testList() {
        System.err.println(queens);
        queens.set(3, 0, true);
        queens.set(4, 0, true);
        System.err.println(queens);
        queens.set(3, 0, false);
        System.err.println(queens);
    }

    @Test
    public void testPlaceQueen() {
        placeQueen(queens, attacked, 3, 3);
        System.err.println(queens);
        System.err.println(attacked);
        placeQueen(queens, attacked, 5, 4);
        System.err.println(queens);
        System.err.println(attacked);
    }

    @Test
    public void testSolveNQueens() {
        solveNQueens__(4).forEach(System.err::println);
    }

}