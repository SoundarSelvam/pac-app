package pac.app.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pac.app.dto.PrimeFinderResponse;
import pac.app.service.EcfService;

import javax.inject.Singleton;

@Singleton
@Controller("/ecf")
public class EcfPostCntroller {
    private static final Logger LOG = LoggerFactory.getLogger(EcfPostCntroller.class);

    private final EcfService ecfService;

    public EcfPostCntroller(EcfService primeFinderService) {
        this.ecfService = primeFinderService;
    }

    @Post("/pe")
    @Produces(MediaType.APPLICATION_JSON)
    public String saveEvent(@Body String body) {
        return body;
    }

    @Get("/find/{number}")
    public PrimeFinderResponse findPrimesBelow(int number) {
        PrimeFinderResponse resp = new PrimeFinderResponse();
        if (number >= ecfService.MAX_SIZE) {
            if (LOG.isInfoEnabled()) {
                LOG.info("This number is too big, you can't possibly want to know all the primes below a number this big.");
            }
            resp.setMessage("This service only returns lists for numbers below " + ecfService.MAX_SIZE);
            return resp;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Computing all the primes smaller than {} ...", number);
        }
        resp.setMessage("Success!");
        resp.setPrimes(ecfService.findPrimesLessThan(number));
        return resp;
    }
}