package com.yy;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

/**
 * @author ycr
 * @date 2022/4/10 10:44
 */
public class Test1 implements Comparable<Integer> {

    @Test
    public void test() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }


    @Override
    public int compareTo(@NotNull Integer o) {
        return 0;
    }
}

@Data
class Node<T>{
    private Node<T> first;

//    private Node<T> next;

    private T data;

    public <T>  T next(T node) {
        if (first == null) {
            return null;
        }

        return null;
    }



}
