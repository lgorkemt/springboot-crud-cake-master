package com.waracle.cakemgr;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(com.waracle.cakemgr.dao.CakeRepositoryTest.class, com.waracle.cakemgr.rest.CakeRestControllerTest.class);

        if (result.wasSuccessful()) {
            System.out.println("===============================================");
            System.out.println("   All the " + result.getRunCount()+" tests passed !  ");
            System.out.println("===============================================");
            return;
        }
        else {
            System.out.println("===============================================");
            System.out.println("ERROR :  " + result.getFailures().size() + " tests failed!");
            for(Failure failure : result.getFailures()) {
                System.out.println("ERROR : " +  failure.getTestHeader() + " failed !");
            }
            System.out.println("===============================================");
        }

        result.getFailures()
                .stream()
                .forEach(System.out::println);
    }
}