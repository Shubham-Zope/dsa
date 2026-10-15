class Solution {
    public int[] getFloorAndCeil(int[] nums, int x) {
        int[] answer = new int[2];

        answer[0] = getFloor(nums, x);
        answer[1] = getCeil(nums, x);

        return answer;
    }

    private int getFloor(int[] nums, int x) {
        int l = 0;
        int h = nums.length - 1;
        int ans = -1;

        while (l <= h) {
            int mid = l + (h - l) / 2;

            if (nums[mid] <= x) {
                ans = nums[mid];
                l = mid + 1;
            } else {
                h = mid - 1;
            }
        }

        return ans;
    }

    private int getCeil(int[] nums, int x) {
        int l = 0;
        int h = nums.length - 1;
        int ans = -1;

        while (l <= h) {
            int mid = l + (h - l) / 2;

            if (nums[mid] < x) {
                l = mid + 1;
            } else {
                ans = nums[mid];
                h = mid - 1;
            }
        }

        return ans;
    }
}