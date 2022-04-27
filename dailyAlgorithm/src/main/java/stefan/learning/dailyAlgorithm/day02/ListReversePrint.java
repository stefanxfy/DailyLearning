package stefan.learning.dailyAlgorithm.day02;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/5dt66m/
 * 剑指 Offer 06. 从尾到头打印链表
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * @author stefan
 * @date 2022/4/27 9:49
 */
public class ListReversePrint {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }

        public ListNode next(int val) {
            ListNode  node = new ListNode(val);
            if (next == null) {
                next = node;
                return node;
            }
            next.next = node;
            return node;
        }
    }

    /**
     * 用一个 栈 做倒置
     * @param head
     * @return
     */
    public static int[] reversePrint(ListNode head) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        while (head != null) {
            linkedList.addFirst(head.val);
            head = head.next;
        }
        int[] rt = new int[linkedList.size()];
        int i = 0;
        for (Integer val : linkedList) {
            rt[i] = val;
            i++;
        }
        return rt;
    }

    /**
     * 1、先统计出size
     * 2、倒序插入数组
     * 最佳
     * @param head
     * @return
     */
    public static int[] reversePrint2(ListNode head) {
        int size = 0;
        ListNode headTmp = head;
        while (headTmp != null) {
            headTmp = headTmp.next;
            size++;
        }
        int[] rt = new int[size];
        while (head != null) {
            rt[--size] = head.val;
            head = head.next;
        }
        return rt;
    }

    /**
     * 递归
     * 并不是最好的方法，只是让你知道可以用递归做倒置
     * @param head
     * @return
     */
    public static int[] reversePrint3(ListNode head) {
        List<Integer> tmp = new ArrayList<Integer>();
        recur(tmp, head);
        int [] rt = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            rt[i] = tmp.get(i);
        }
        return rt;
    }

    /**
     * 利用递归进行倒置
     * @param tmp
     * @param head
     */
    static void recur(List<Integer> tmp, ListNode head) {
        if (head == null) {
            return;
        }
        recur(tmp, head.next);
        tmp.add(head.val);
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(10);
        head.next(9).next(8).next(7);
        for (int i : reversePrint3(head)) {
            System.out.println(i);
        }
    }
}
