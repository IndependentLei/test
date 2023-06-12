package com.shebao.test.test.dataStructure.skipListDemo;

public class SkipListTest {
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        skipList.add(new SkipNode<>(1,54));
        skipList.add(new SkipNode<>(2,33));
        skipList.add(new SkipNode<>(3,33));
        skipList.add(new SkipNode<>(4,33));
        skipList.add(new SkipNode<>(5,33));
        skipList.add(new SkipNode<>(6,33));
        skipList.add(new SkipNode<>(7,33));
        skipList.add(new SkipNode<>(8,33));
        SkipNode<Integer> search = skipList.search(1);
        System.out.println(search.toString());
        skipList.printList();

    }
}
