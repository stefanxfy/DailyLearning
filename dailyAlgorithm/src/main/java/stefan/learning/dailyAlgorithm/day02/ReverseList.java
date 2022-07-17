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

    public static ListNode reverseList5(ListNode head) {
        // 10-->9-->8-->7-->null
        // null<--10<--9<--8<--7
        // 前驱指针
        ListNode pre = null;
        // 当前指针
        ListNode cur = head;
        while (cur != null) {
            // 提前保存当前节点的next，保证向右遍历不会中断
            ListNode tmp = cur.next;
            // 反转
            cur.next = pre;
            // pre向右移到cur
            pre = cur;
            // cur向右移到tmp
            cur = tmp;
        }
        // 遍历到最后了，cur=null，那么pre就是尾节点返回
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
        // 10-->9-->8-->7-->null
        // null<--10<--9<--8<--7
        //当递归到尾部，则停止返回
        if(head == null || head.next == null){
            return head;
        }
        // 上半部分是 顺时针
        // 递归遇到终止条件返回尾节点
        ListNode tail = reverseList3(head.next);
        // 下半部分就是 逆时针
        //回溯反转
        // 第一次返回时，head 为 倒数第二个节点，head.next 为尾节点
        // 那么从尾节点开始反转操作： 尾节点 head.next 的 next 指向 倒数第二个节点 head，即 head.next.next = head
        // 8<--7
        // 第二次返回时，head 就是 倒数第三个节点，head.next 为 倒数第二个节点
        // 那么反转操作：倒数第二个节点 head.next 的next指向倒数第三个节点 head，即 head.next.next = head
        // 9<--8<--7
        // 以此类推，直到回溯到原链表的头节点 head，反转操作 head的下一个节点的next指向 head，即 head.next.next = head
        // 一切非常顺利，但已运行死循环了，那是因为 原链表的头节点next指针没有修改，依然指向的是头节点的下一个节点，而头节点的next已经反转指向头节点，这样就形成了一个环
        // 10<-->9<--8<--7
        head.next.next = head;
        // 所以还需要修改 原链表头节点的next指向null。
        // null<--10<--9<--8<--7
        // 其实 每个回溯过程 都把当前节点的next 和下一个节点 主动断开，防止最后形成环
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

        // 终止条件
        if (cur == null) {
            // cur== null 可能一开始 链表就为null
            // 或者遍历到了 尾节点的 next
            // 那么尾节点就是 pre
            return pre;
        }
        // cur=null, pre = 7
        // 最终返回 res = 7
        ListNode res = recur(cur.next, cur);
        // cur = 7, pre = 8
        // cur = 8,  pre = 9
        // cur = 9,  pre = 10
        // cur = 10, pre = null
        // 修改节点引用指向
        cur.next = pre;
        // 返回反转后链表的头节点
        return res;
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
