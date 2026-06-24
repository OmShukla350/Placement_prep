class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        int i = 0 , j = 0;
        Set<Character> set = new HashSet<>();

        while(j < s.length()){
          char ch = s.charAt(j);

          while(set.contains(ch)){
            set.remove(s.charAt(i));
            i++;
          }
          set.add(ch);
          maxLen = Math.max(maxLen,j-i+1);
          j++;
        }
        return maxLen;
   }
}
