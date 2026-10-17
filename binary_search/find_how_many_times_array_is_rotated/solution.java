class Solution {
    public int findKRotation(ArrayList<Integer> nums) {
        int low = 0;
        int high = nums.size() - 1;
        int minIndex = 0;
        int ans = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums.get(low) <= nums.get(mid)) {
                if (nums.get(low) < ans) {
                    ans = nums.get(low);
                    minIndex = low;
                }
                low = mid + 1;
            } else if (nums.get(mid) < nums.get(high)) {
                if (nums.get(mid) < ans) {
                    ans = nums.get(mid);
                    minIndex = mid;
                }
                high = mid - 1;
            }
        }

        return minIndex;
    }
}