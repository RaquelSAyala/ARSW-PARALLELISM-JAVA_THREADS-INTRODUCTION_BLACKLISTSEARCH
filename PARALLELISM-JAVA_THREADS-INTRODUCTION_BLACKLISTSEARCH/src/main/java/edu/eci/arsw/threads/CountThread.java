/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */

public class CountThread extends Thread {

    private final int start;
    private final int end;

    public CountThread(int start, int end, int threadNumber) {
        this.start = start;
        this.end = end;
        this.setName("CountThread-" + threadNumber);
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println(Thread.currentThread().getName() + " -> " + i);
        }
    }
}
