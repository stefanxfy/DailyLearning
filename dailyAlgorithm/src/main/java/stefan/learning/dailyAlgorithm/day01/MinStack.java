package stefan.learning.dailyAlgorithm.day01;

import java.util.LinkedList;

/**
 * 剑指 Offer 30. 包含 min 函数的栈
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，
 * 调用 min、push 及 pop、top的时间复杂度都是 O(1)。
 * https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/50je8m/
 * 思路：
 * 栈push、pop、top的时间复杂度为O(1)
 * 要想找到栈中的元素min，需要遍历整个栈进行排序，时间复杂度为O(n)
 * 但是min操作需要O(1)
 *
 * 1、用两个栈实现，栈a和栈b
 * 2、a正常出入栈，b的栈顶存放a中最小的元素
 * 3、需要a的push和pop操作都要维护栈b中栈顶是a中最小元素
 * 4、对a push x，同时判断b的是否有元素，没有就也入栈b，有就和b栈顶元素比较，x小于b栈顶元素就入栈b
 * 5、对a pop x，如果b栈顶的元素和x相等，则同时出栈
 * 6、对a top，b不做任何处理
 *
 *
 */
public class MinStack {
    LinkedList<Integer> a,b;
    public MinStack() {
        a = new LinkedList<Integer>();
        b = new LinkedList<Integer>();
    }

    public void push(int x) {
        a.addLast(x);
        if (b.isEmpty()) {
            b.addLast(x);
            return;
        }
        // >=  等于很重要，因为有可能两个元素相等
        // 如果b少入一次栈的话，可能就导致ab数据不一致了
        if (b.getLast() >= x) {
            b.addLast(x);
        }
    }

    public void pop() {
        int x = a.removeLast();
        if (b.getLast() == x) {
            b.removeLast();
        }
    }

    public int top() {
        return a.getLast();
    }

    public int min() {
        return b.getLast();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(10);
        minStack.push(2);
        minStack.push(3);
        System.out.println(minStack.min());
    }
}
