package com.zk.zookeeperlock.zk;

public abstract class AbstractLock implements Lock{
    @Override
    public void getLock() {

        if (tryLock()){
            System.out.println(Thread.currentThread().getName()+"==="+"获取锁资源");
        }else{
           waitLock();
           //递归调用
           getLock();
        }
    }

    public abstract boolean tryLock();

    public abstract void waitLock();


}
