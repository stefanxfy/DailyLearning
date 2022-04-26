package stefan.learning.dailyAlgorithm.day01;

import java.util.LinkedList;

/**
 * 剑指 Offer 09. 用两个栈实现队列
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 * https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/5d3i87/
 * 
 * 解析：
 * 队列：先进先出
 * 栈：先进后出
 * 用两个栈实现 队列，假设有两个栈A、B
 * 1、添加元素先入栈A
 * 2、删除元素按照队列的特点，是让栈A的最底部的元素出栈
 * 3、但是没办法直接删除栈A最底部的元素，所以将栈中的元素顺序出栈到栈B
 * 4、这样栈A底部的元素就到了栈B的顶部
 * 5、删除B栈顶的元素，就起到了队列先进先出的效果
 */
public class CQueue {
    LinkedList<Integer> a, b;
    public CQueue() {
        a = new LinkedList<Integer>();
        b = new LinkedList<Integer>();
    }
    public void appendTail(int value) {
        a.addLast(value);
    }
    public int deleteHead() {
        // 判断b中还有元素，出队，先把b中的元素删完
        if (!b.isEmpty()) {
            return b.removeLast();
        }
        // 如果b中为空，那就直接返回-1
        if (a.isEmpty()) {
            return -1;
        }
        // b为空，且a 不为空，
        // 则先将a中的所有元素出栈到b中，让顺序颠倒
        while (!a.isEmpty()) {
            b.addLast(a.removeLast());
        }
        // b栈顶元素出栈
        return b.removeLast();
    }

    public static void main(String[] args) {
        CQueue queue = new CQueue();
        queue.appendTail(1);
        queue.appendTail(2);
        queue.appendTail(3);
        queue.appendTail(4);

        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());


    }
}
