package net.acegik.examples;

/**
 *
 * @author pnhung177
 */
public class FinalizeMayNotBeCalled {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("main() running ...");
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new MyThread(i));
            t.start();
        }
        System.out.println("main() is done!");
        
        // uncomment the following to force finalizers to execute, but:
        // “This method is inherently unsafe. It may result in finalizers 
        //   being called on live objects while other threads are concurrently
        //   manipulating those objects, resulting in erratic behavior or
        //   deadlock.”
        //System.runFinalizersOnExit(true);
    }
}

class MyThread implements Runnable {

    public MyThread(int i) {
        this.index = i;
    }

    private int index = 0;
    
    @Override
    public void run() {
        System.out.println("MyThread[" + index + "] is running ...");
        try {
            Thread.sleep(Math.round(1 + Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyThread[" + index + "] is done!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("MyThread[" + index + "] finalize");
        super.finalize();
    }
}
