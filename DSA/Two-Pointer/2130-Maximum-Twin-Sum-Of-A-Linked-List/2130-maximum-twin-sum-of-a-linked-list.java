/**
 Definition for singly-linked list.
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public ListNode reverseLL(ListNode curr){
      ListNode prev = null;
      ListNode next;

      while(curr != null){
        next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
      }
      return prev;
    }
    public int pairSum(ListNode head) {
      int maxSum = 0;
      ListNode slow = head;
      ListNode fast = head;

      while(fast != null && fast.next != null){
        slow = slow.next;
        fast = fast.next.next;
      }
      ListNode p1 = reverseLL(slow);
      ListNode p2 = head;

      while(p1 != null){
        int sum = p1.val + p2.val;
        maxSum = Math.max(maxSum,sum);
        p1 = p1.next;
        p2 = p2.next;
      }
      return maxSum;

    }
}