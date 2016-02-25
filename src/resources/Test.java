package resources;

/**
 * Created by ghuan_000 on 11/14/2015.
 */
public class Test {
    public class Super{
        public Super(){
            System.out.println("super constructor");
            print();
        }
        public void print(){
            System.out.println("super print()");
        }
    }
    public class SubClass extends Super{
        public SubClass(){
            super();
        }
        public void print(){
            System.out.println("subclass print()");
        }
    }
    public static void main(String[] args){
        Test test = new Test();
        Super superClass = test.new SubClass();
    }
}
