import ro.pippo.core.Initializer;
import ro.pippo.core.Pippo;

public class CheckPhrase {

    public static void main(String[] args) {
        Pippo pippo = new Pippo(new CheckPhraseApi());
        pippo.start();
    }
}