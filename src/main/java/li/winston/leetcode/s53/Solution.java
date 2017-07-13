package li.winston.leetcode.s53;

/**
 * Created by winston on 13/07/2017.
 */
public class Solution {

    // DP solution for 2D down to 1D
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) return 0;
        int[] sums = new int[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            sums[i] = nums[i] + sums[i - 1];
        }
        int best = Integer.MIN_VALUE;
        int min = 0;
        for (int sum : sums) {
            best = Math.max(best, sum - min);
            min = Math.min(min, sum);
        }
        return best;
    }

}
