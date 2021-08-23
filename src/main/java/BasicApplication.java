import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.core.Application;
import ro.pippo.core.HttpConstants;
import ro.pippo.core.route.DirectoryHandler;
import ro.pippo.core.route.RouteContext;
import ro.pippo.core.route.RouteHandler;
import ro.pippo.metrics.Metered;
import java.io.File;

/**
 * @author Decebal Suiu
 */
public class BasicApplication extends Application {

//    private final static Logger log = LoggerFactory.getLogger(BasicApplication.class);

    @Override
    protected void onInit() {
        // send 'Hello World' as response
        GET("/", routeContext -> routeContext.send("Hello World"));

        // send a file as response
        GET("/file", routeContext -> routeContext.send(new File("pom.xml")));

        // send a json as response
        GET("/json", routeContext -> {
            Contact contact = createContact();
            routeContext.json().send(contact);
        });

        // send xml as response
        GET("/xml", routeContext -> {
            Contact contact = createContact();
            routeContext.xml().send(contact);
        });

        // send an object and negotiate the Response content-type, default to XML
        GET("/negotiate", routeContext -> {
            Contact contact = createContact();
            routeContext.xml().negotiateContentType().send(contact);
        });

        // send a template as response
        GET("/template", routeContext -> {
            routeContext.setLocal("greeting", "Hello");
            routeContext.render("hello");
        });
    }

    private Contact createContact() {
        Contact contact = new Contact();
        contact.setId(12345);
        contact.setName("John");
        contact.setPhone("0733434435");
        contact.setAddress("Sunflower Street, No. 6");
        return contact;
    }

}