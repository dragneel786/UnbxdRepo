import ro.pippo.core.Pippo;

/**
 * @author Decebal Suiu
 */
public class BasicDemo {

    public static void main(String[] args) {
        Pippo pippo = new Pippo(new BasicApplication());
//        pippo.getServer().setPippoFilterPath("/web/*");
        pippo.start();
    }

}