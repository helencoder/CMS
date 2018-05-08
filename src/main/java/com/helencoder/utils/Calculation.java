package com.helencoder.utils;

/**
 * 计算任务
 *
 * Created by zhenghailun on 2018/5/8.
 */
public class Calculation {
    final long num;
    long count;

    public Calculation(long num) {
        this.num = num;
    }

    public void run() {
        long count = 0;
        for (int i = 0; i < num; i++) {
            count += i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        this.count = count;
        System.out.println("总和为: " + count);
        System.out.println("计算任务结束时间: " + System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Calculation{" + "num='" + num + '\'' + ", count=" + count + '}';
    }

}
