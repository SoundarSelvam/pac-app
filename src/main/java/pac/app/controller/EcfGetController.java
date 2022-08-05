package pac.app.controller;
import pac.app.dto.Book;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pac.app.service.EcfService;

import javax.inject.Singleton;

@Singleton
@Controller("/aws")
public class EcfGetController {
    private static final Log LOG = LogFactory.getLog(EcfGetController.class);

    private final EcfService ecfService;

    public EcfGetController(EcfService primeFinderService) {
        this.ecfService = primeFinderService;
    }

    @Post(value = "/book", consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Status(HttpStatus.CREATED)
    Book search(@Body Book book) {
        LOG.info(book.toString());
        return book;
    }

    @Post("/pick/{rank}")
    public String kick(String rank) {
        LOG.info(rank.toString());
        return rank;
    }

    @Post("/pi/{rank}/{jan}")
    public String pi(String rank,String jan) {
        LOG.info(rank.toString());
        return rank+jan;
    }

}
