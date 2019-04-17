package misc.debug;

public class Debug {
    public static void printThread(){
        System.out.println("*****************************");
        System.out.println("Current thread is " + Thread.currentThread().getName()+
                ", \nId:"+ Thread.currentThread().getId() );
        System.out.println("*****************************");

    }

    public static void printThread(String tag)
    {
        System.out.println("*****************************");
        System.out.println("TAG:" + tag);
        printThread();
    }

    public static void log(String tag, Object ... vals){
        System.out.println("TAG:" + tag);
        for(Object val: vals){
            System.out.print(val.toString()+" ");
        }
        System.out.println();
    }

    public static void err(String tag, Object ... vals) {
        System.err.println("TAG:" + tag);
        for(Object val: vals){
            System.err.print(val.toString()+" ");
        }
        System.err.println();
    }
}
