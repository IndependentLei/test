package com.shebao.test.test.designpattern.责任链模式test.案例一;

public class Test {
    public static void main(String[] args) {

        ClassLeader testClassLeader = new ClassLeader();
        GradeLeader testGradeLeader = new GradeLeader();
        HeadmasterLeader testHeadMasterLeader = new HeadmasterLeader();

        testClassLeader.setDays(1);
        testClassLeader.setNext(testGradeLeader);

        testGradeLeader.setDays(4);
        testGradeLeader.setNext(testHeadMasterLeader);

        testHeadMasterLeader.setDays(9);
        testHeadMasterLeader.setNext(null);

        testClassLeader.handlerRequest(11);

    }
}
