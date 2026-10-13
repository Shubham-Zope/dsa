class solution {
    public long numberOfInversions(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private long mergeSort(int[] nums, int low, int high) {
        long count = 0;
        if (low >= high) {
            return 0;
        }

        int mid = low + (high - low)/2;

        count += mergeSort(nums, low, mid);
        count += mergeSort(nums, mid+1, high);
        count += merge(nums, low, mid, high);

        return count;
    }

    private long merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high-low+1];

        int i = low;
        int j = mid + 1;
        int k = 0;

        long count = 0;

        while (i <= mid && j <= high) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];

                count += mid - i + 1;
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
