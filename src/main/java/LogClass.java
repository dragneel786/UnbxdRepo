import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LogClass {
//    private final Logger log = LoggerFactory.getLogger(LogClass.class);

    public void logMessage(String input){
        log.error("Error Occured: {}", input);
    }
}
