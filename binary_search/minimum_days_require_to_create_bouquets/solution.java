class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        long numberOfRoses = (long) m * k;
        if (numberOfRoses > n) {
            return -1;
        }

       int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;

        for (int day : bloomDay) {
            low = Math.min(low, day);
            high = Math.max(high, day);
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int bouquestsBuilt = countBouquetsBuilt(bloomDay, mid, k);

            if (bouquestsBuilt >= m) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private int countBouquetsBuilt(int[] bloomDay, int day, int k) {
        int count = 0;
        int countOfFlowers = 0;

        for (int flower: bloomDay) {
            if (flower <= day) {
                countOfFlowers++;
                if (countOfFlowers == k) {
                    count++;
                    countOfFlowers = 0;
                }
            } else {
                countOfFlowers = 0;
            }
        }

        return count;
    }
}