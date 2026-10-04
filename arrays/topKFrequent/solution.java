import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i:nums) {
            map.put(i,map.getOrDefault(i,0)+1);
        }

        int n = nums.length;

        List<Integer>[] list = new List[n+1];

        for(int i=0;i<=n;i++) {
            list[i] = new ArrayList<>();
        }

        for(int key: map.keySet()) {
            list[map.get(key)].add(key);
        }

        int[] res = new int[k];

        int idx = 0;

        for(int i=n;i>=0;i--) {
            for(int num: list[i]) {
                res[idx++] = num;
                if(idx==k) break;
            }
            if(idx==k) break;
        }

        return res;
    }

    public static void main(String[] args) {
        solution s = new solution();
        int[] nums = {1, 2, 1 ,2, 1, 2, 3, 1, 3, 2};
        int k = 2;
        int[] result = s.topKFrequent(nums, k);
        System.out.println(Arrays.toString(result));
    }
}
