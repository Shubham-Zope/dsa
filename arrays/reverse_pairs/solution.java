class Solution {

    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int low, int high) {
        if (low >= high) {
            return 0;
        }

        int mid = low + (high - low) / 2;

        int count = 0;

        count += mergeSort(nums, low, mid);
        count += mergeSort(nums, mid + 1, high);

        count += merge(nums, low, mid, high);

        return count;
    }

    private int merge(int[] nums, int low, int mid, int high) {
        int count = 0;

        // Step 1: Count reverse pairs
        int j = mid + 1;

        for (int i = low; i <= mid; i++) {
            while (j <= high && nums[i] > 2L * nums[j]) {
                j++;
            }

            count += j - (mid + 1);
        }

        // Step 2: Merge the sorted halves
        int[] temp = new int[high - low + 1];

        int i = low;
        j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        while (j <= high) {
            temp[k++] = nums[j++];
        }

        for (int p = 0; p < temp.length; p++) {
            nums[low + p] = temp[p];
        }

        return count;
    }
}
