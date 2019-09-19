package com.lambda;

public class LambdaDemo {

    public static void main(String[] args) {
        Runnable r1=new Runnable() {
            @Override
            public void run() {
                System.out.println("===========================");
            }
        };
        //简化内部类写法
        Runnable r2=() ->{
            System.out.println("2222222222222");
        };
        new Thread(r1).start();
        new Thread(r2).start();

    }


}
