package com.shebao.test.test.dataStructure.skipListDemo;


public class SkipNode<T>{
    int key;
    T value;
    //左右上下四个方向的指针
    SkipNode<T> right,down;
    public SkipNode (int key,T value) {
        this.key=key;
        this.value=value;
    }

    @Override
    public String toString() {
        return "SkipNode{" +
                "key=" + key +
                ", value=" + value +
                ", right=" + right +
                ", down=" + down +
                '}';
    }
}
