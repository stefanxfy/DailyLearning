package stefan.learning.dailyTest.test;

import java.util.LinkedList;

/**
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
    LinkedList<Integer> A, B;
    public CQueue() {
        A = new LinkedList<Integer>();
        B = new LinkedList<Integer>();
    }
    public void appendTail(int value) {
        A.addLast(value);
    }
    public int deleteHead() {
        // 判断B中还有元素，出队，先把B中的元素删完
        if (!B.isEmpty()) {
            return B.removeLast();
        }
        // 如果A中为空，那就直接返回-1
        if (A.isEmpty()) {
            return -1;
        }
        // B为空，且A 不为空，
        // 则先将A中的所有元素出栈到B中，让顺序颠倒
        while (!A.isEmpty()) {
            B.addLast(A.removeLast());
        }
        // B栈顶元素出栈
        return B.removeLast();
    }
}
