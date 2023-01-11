public class Demo {

    public static void main(String[] args) {

        Thread thread = new MyThread();
        Thread runnable = new Thread(new MyRunnable() {
        });
        // 启动线程
        thread.start();
        runnable.start();
    }

}

/**
 * 继承Thread
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("start new Thread!");
    }
}

/**
 * 实现runnable接口
 */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("start new Runnable!");
    }
}

