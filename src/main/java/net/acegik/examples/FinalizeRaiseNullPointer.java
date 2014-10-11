package net.acegik.examples;

import java.util.Date;

/**
 *
 * @author pnhung177
 */
public class FinalizeRaiseNullPointer {

    public static void main(String[] args) {
        try {
            ParentClass obj = new ChildClass();
            System.runFinalizersOnExit(true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

class ParentClass {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("ParentClass finalize!");
        doSomething();
    }

    public void doSomething() throws Throwable {
        System.out.println("This is ParentClass!");
    }
}

class ChildClass extends ParentClass {

    private Date d;

    protected ChildClass() {
        d = new Date();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("ChildClass finalize!");
        try {
            d = null;
        } finally {
            super.finalize();
        }
    }

    @Override
    public void doSomething() throws Throwable {
        System.out.println("This is ChildClass! The date object is: " + d);
    }
}
