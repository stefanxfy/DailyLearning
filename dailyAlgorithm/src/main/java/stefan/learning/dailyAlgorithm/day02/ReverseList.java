package stefan.learning.dailyAlgorithm.day02;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/9pdjbm/
 * 剑指 Offer 24. 反转链表
 * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
 * @author stefan
 * @date 2022/4/27 10:48
 */
public class ReverseList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }

        public ListNode next(int val) {
            ListNode node = new ListNode(val);
            if (next == null) {
                next = node;
                return node;
            }
            next.next = node;
            return node;
        }
    }

    /**
     * 借助 数组做倒置，构建新的链表 时间复杂度O(2n)
     * 执行用时：0 ms，在所有 Java 提交中击败了100.00%的用户
     * 内存消耗：41.3 MB, 在所有 Java 提交中击败了5.06%的用户
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        if (list.isEmpty()) {
            return null;
        }
        int last = list.size() - 1;
        head = list.get(last);
        list.get(0).next = null;
        ListNode next = head;
        for (int i = last - 1; i >= 0; i--) {
            next.next = list.get(i);
            next = next.next;
        }
        return head;
    }

    /**
     * 双指针(最佳)
     * 遍历链表采用尾插法生成新的链表
     * 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗：40.5 MB, 在所有 Java 提交中击败了82.63%的用户
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        // 10-->9-->8-->7-->null
        // h = 10, h.next = null, p = 10
        // h = 9,  h.next = 10,   p = 9
        // h = 8,  h.next = 9,    p = 8
        // h = 7,  h.next = 8,    p = 7
        // 前驱指针
        ListNode pre = null;
        // 当前指针
        ListNode cur = null;
        while (head != null) {
            cur = head;
            head = head.next;
            // 当前节点next指向前驱节点
            cur.next = pre;
            pre = cur;
        }
        return pre;
    }

    /**
     * 递归
     * 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗：41 MB, 在所有 Java 提交中击败了28.31%的用户
     * @param head
     * @return
     */
    public static ListNode reverseList3(ListNode head) {
        // 10-->9-->8-->7
        //当到达尾部，则停止递归
        if(head == null || head.next == null){
            //这里head是尾部
            return head;
        }
        ListNode tail = reverseList3(head.next);
        //回溯，翻转
        // 从 倒数第二个开始
        // 8<--7
        // 9<--8<--7
        // 10<--9<--8<--7
        head.next.next = head;
        head.next = null;
        return tail;
    }

    /**
     * 递归2
     * 时间复杂度 O(N) ： 遍历链表使用线性大小时间。
     * 空间复杂度 O(N) ： 遍历链表的递归深度达到 N ，系统使用 O(N) 大小额外空间。
     * @param head
     * @return
     */
    public ListNode reverseList4(ListNode head) {
        // 10-->9-->8-->7-->null
        return recur(head, null);    // 调用递归并返回
    }
    private ListNode recur(ListNode cur, ListNode pre) {
        // cur = 10, pre = null
        // cur = 9,  pre = 10
        // cur = 8,  pre = 9
        // cur = 7,  pre = 8
        // cur=null, pre = 7
        if (cur == null) {
            return pre; // 终止条件
        }
        // 递归后继节点
        // cur=null, pre = 7
        // 最终返回 res = 7
        ListNode res = recur(cur.next, cur);
        // cur = 7, pre = 8
        // cur = 8,  pre = 9
        // cur = 9,  pre = 10
        // cur = 10, pre = null
        cur.next = pre;              // 修改节点引用指向
        return res;                  // 返回反转链表的头节点
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(10);
        head.next(9).next(8).next(7);
        ListNode headnew = reverseList3(head);
        while (headnew != null) {
            System.out.println(headnew.val);
            headnew = headnew.next;
        }

    }
}
