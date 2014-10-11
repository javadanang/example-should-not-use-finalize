package net.acegik.examples;

/**
 *
 * @author pnhung177
 */
public class FinalizeRunsInAnyOrder {
    
    public static void main(String[] args) {
        try {
            ArrayContainer container = new ArrayContainer(new int[] {1, 2, 3});
    
            Class1 obj1 = new Class1(container);
            Class2 ob21 = new Class2(1, container);
            Class2 ob22 = new Class2(2, container);
            
            for(int i=0; i<1000000; i++) {
                String s = new String("Hello world" + Math.random());
            }
            
            obj1 = null;
            
            for(int i=0; i<1000000; i++) {
                String s = new String("Hello world" + Math.random());
            }
            
            ob22 = null;
            
            for(int i=0; i<1000000; i++) {
                String s = new String("Hello world" + Math.random());
            }
            System.gc();
            
            ob21 = null;
            
            for(int i=0; i<1000000; i++) {
                String s = new String("Hello world" + Math.random());
            }
            
            System.runFinalizersOnExit(true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

class ArrayContainer {
    
    public ArrayContainer(int[] a) {
        this.array = a;
    }
    
    private int[] array;

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }
}

class Class1 {

    public Class1(ArrayContainer c) {
        this.container = c;
    }
    
    private ArrayContainer container = null;
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Class1.finalize() running...");
        try {
            for(int i=0; i<1000000; i++) {
                if (container.getArray() == null) {
                    System.out.println("Class1 - array is null");
                    break;
                }
            }
        } finally {
            super.finalize();
        }
        System.out.println("Class1.finalize() done!");
    }
}

class Class2 {

    public Class2(int i, ArrayContainer c) {
        this.number = i;
        this.container = c;
    }
    
    private int number = 0;
    private ArrayContainer container = null;
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Class2[" + number + "].finalize() running...");
        try {
            container.setArray(null);
        } finally {
            super.finalize();
        }
        System.out.println("Class2[" + number + "].finalize() done!");
    }
}
