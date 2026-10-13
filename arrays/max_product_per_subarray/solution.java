class Solution {
    public int maxProduct(int[] nums) {
        int maxResult = nums[0];
        int minResult = nums[0];
        int answer = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int newMax = Math.max(nums[i],
                    Math.max(maxResult * nums[i], minResult * nums[i]));

            int newMin = Math.min(nums[i],
                    Math.min(maxResult * nums[i], minResult * nums[i]));

            maxResult = newMax;
            minResult = newMin;

            answer = Math.max(answer, maxResult);
        }

        return answer;
    }
}
