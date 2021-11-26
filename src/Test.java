abstract class AbstractA {

}

class A {
    private int a;
    private int b;

    public A() {
    }

    public A(int a1, int b1) {
        a = a1;
        b = b1;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return "A obj: a=" + this.a + ", b=" + this.b;
    }
}

class A1 extends A {
    A a;

    public A1(A a1) {
        super(a1.getA(), a1.getB());
        a = a1;
    }

    @Override
    public int getA() {
        return super.getA();
    }
    @Override
    public int getB() {
        return super.getB();
    }
    @Override
    public void setA(int a) {
        super.setA(a);
    }
    @Override
    public void setB(int b) {
        super.setB(b);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

public class Test {
    public static void main(String[] args) {
        A testA = new A(1, 2);
        System.out.println(testA);
        A1 testA1 = new A1(testA);
        System.out.println(testA1);
        System.out.println(testA1.getB());
    }
}
