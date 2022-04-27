package stefan.learning.dailyAlgorithm.day02;

import jdk.internal.org.objectweb.asm.Handle;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/9p0yy1/
 * 剑指 Offer 35. 复杂链表的复制
 * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，
 * 每个节点除了有一个 next 指针指向下一个节点，
 * 还有一个 random 指针指向链表中的任意节点或者 null。
 *
 * @author stefan
 * @date 2022/4/27 17:41
 */
public class CopyRandomList {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 哈希表
     *
     * 1、key=原链表节点，value=新链表节点
     * 2、遍历原链表节点的，next、random从map中新节点
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        // 构建一个map
        Map<Node, Node> nodeMap = new HashMap<Node, Node>();
        while (cur != null) {
            nodeMap.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        // 遍历构建原节点对应的新节点的next、random
        while (cur != null) {
            nodeMap.get(cur).next = nodeMap.get(cur.next);
            nodeMap.get(cur).random = nodeMap.get(cur.random);
            cur = cur.next;
        }
        return nodeMap.get(head);
    }

    /**
     * 拼接 + 拆分
     *
     * 1、拼接，新节点.next=原节点.next，新节点=原节点.next
     * node1-->node1new-->node2-->node2new-->null
     *
     * 2、构建新节点的random指向
     * 新节点.random = 原节点.random.next
     *
     * 3、拆分新旧链表
     * res = head.next
     * cur = res
     * pre = head
     * loop:
     * pre.next = pre.next.next
     * cur.next = cur.next.next
     * pre = pre.next
     * cur = cur.next
     * loop end
     * pre.next = null
     * return res
     *
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        //合成一个长链表
        Node cur = head;
        while (cur != null) {
            Node tmp = new Node(cur.val);
            tmp.next = cur.next;
            cur.next = tmp;
            cur = tmp.next;
        }
        // 构建新节点的random
        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        // 拆分出两个链表
        Node res = head.next;

        cur = head.next;
        Node pre = head;
        while (cur.next != null) {
            pre.next = pre.next.next;
            cur.next = cur.next.next;
            pre = pre.next;
            cur = cur.next;
        }
        // 单独处理原链表尾节点
        pre.next = null;
        return res;
    }

    public static void main(String[] args) {
        CopyRandomList randomList = new CopyRandomList();
    }
}
