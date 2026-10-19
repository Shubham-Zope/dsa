class Solution {
    public int kthElement(int[] a, int[] b, int k) {
        if (a.length > b.length) {
            return kthElement(b, a, k);
        }

        int m = a.length;
        int n = b.length;

        int low = Math.max(0, k-n);
        int high = Math.min(k, m);

        while (low <= high) {
            int cut1 = low + (high - low) / 2;
            int cut2 = k - cut1;

            int left1 = cut1 == 0 ? Integer.MIN_VALUE : a[cut1 - 1];
            int right1 = cut1 == m ? Integer.MAX_VALUE: a[cut1];
            int left2 = cut2 == 0 ? Integer.MIN_VALUE : b[cut2 - 1];
            int right2 = cut2 == n ? Integer.MAX_VALUE : b[cut2];

             if (left1 <= right2 && left2 <= right1) {
                return Math.max(left1, left2);
            } else if (left1 > right2) {
                high = cut1 - 1;
            } else {
                low = cut1 + 1;
            }
        }

        return -1;
    }
}