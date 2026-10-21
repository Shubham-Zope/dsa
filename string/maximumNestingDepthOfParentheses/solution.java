class Solution {
    public int maxDepth(String s) {
        int n= s.length();
        int cnt=0;
        int ans=0;
        
        for(int i=0;i<n;i++){
            char ch=s.charAt(i);
            if(ch=='(') cnt++;
            ans=Math.max(ans,cnt);
            if(ch==')') cnt--;
        }

        return ans;

    }
}
