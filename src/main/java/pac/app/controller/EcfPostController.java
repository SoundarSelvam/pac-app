package pac.app.controller;

import pac.app.dto.PrimeFinderResponse;
import pac.app.service.TestService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.apache.client.impl.ApacheConnectionManagerFactory;
import com.amazonaws.http.apache.client.impl.ApacheHttpClientFactory;
import com.amazonaws.http.apache.client.impl.ConnectionManagerAwareHttpClient;
import com.amazonaws.http.client.ConnectionManagerFactory;
import com.amazonaws.http.client.HttpClientFactory;
import com.amazonaws.http.conn.ClientConnectionManagerFactory;
import com.amazonaws.http.settings.HttpClientSettings;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Iterator;
import io.micronaut.http.annotation.*;

@Controller("/test")
public class EcfPostController {
    private static final Logger LOG = LoggerFactory.getLogger(EcfPostController.class);

    private final TestService testService;

    public EcfPostController(TestService primeFinderService) {
        this.testService = primeFinderService;
    }
    @Post("/pe")
    @Produces(MediaType.APPLICATION_JSON)
    public String saveEvent(@Body String body) {
        return body;
    }

    @Get("/find/{number}")
    public PrimeFinderResponse findPrimesBelow(int number) {
        PrimeFinderResponse resp = new PrimeFinderResponse();
        if (number >= testService.MAX_SIZE) {
            if (LOG.isInfoEnabled()) {
                LOG.info("This number is too big, you can't possibly want to know all the primes below a number this big.");
            }
            resp.setMessage("This service only returns lists for numbers below " + testService.MAX_SIZE);
            return resp;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Computing all the primes smaller than {} ...", number);
        }
        resp.setMessage("Success!");
        resp.setPrimes(testService.findPrimesLessThan(number));
        return resp;
    }
}