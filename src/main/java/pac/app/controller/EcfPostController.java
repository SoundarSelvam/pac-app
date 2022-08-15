package pac.app.controller;

import pac.app.dto.PrimeFinderResponse;
import pac.app.service.TestService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.net.URL;
import java.net.*;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
//import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
//import com.amazonaws.http.apache.client.impl.ApacheConnectionManagerFactory;
//import com.amazonaws.http.apache.client.impl.ApacheHttpClientFactory;
//import com.amazonaws.http.apache.client.impl.ConnectionManagerAwareHttpClient;
//import com.amazonaws.http.client.ConnectionManagerFactory;
//import com.amazonaws.http.client.HttpClientFactory;
//import com.amazonaws.http.conn.ClientConnectionManagerFactory;
//import com.amazonaws.http.settings.HttpClientSettings;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import java.io.IOException;
import java.util.Iterator;
import io.micronaut.http.annotation.*;
import pac.app.dto.GetBody;

@Controller("/ecf")
public class EcfPostController {
    private static final Logger LOG = LoggerFactory.getLogger(EcfPostController.class);
    private final TestService testService;
    private static AmazonDynamoDB amazonDynamoDBClient = null;
    private static Table table = null;
    static HttpURLConnection connection=null;
    static URL url;
    public EcfPostController(TestService primeFinderService) {
        this.testService = primeFinderService;
    }
    @Post("/pe")
    @Produces(MediaType.APPLICATION_JSON)
    public String saveEvent(@Body String body) {
        amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.AP_NORTHEAST_1).build();
        LOG.info("Local Test3 murugan");
        body = "jan:1234567890234";
        LOG.info(body);
        String[] s1 = body.split(":");
        String jan = s1[1];
        AttributeValue cc = new AttributeValue();
        HashMap<String, Condition> scanFilter = new HashMap<>();
        Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(jan));
        scanFilter.put("jan", condition);
        ScanRequest scanRequest = new ScanRequest("pac_val").withScanFilter(scanFilter);
        ScanResult scanResult = amazonDynamoDBClient.scan(scanRequest);
        List<java.util.Map<String, AttributeValue>> aa = scanResult.getItems();
        String base_point = "";
        String base_promotionDesc = "";
        String base_rank = "";
        for (int i = 0; i < aa.size(); i++) {
            java.util.Map<String, AttributeValue> bb = aa.get(i);
            Iterator<String> iterator = bb.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                cc = bb.get(key);
                if (key.equals("PromotionDesc")) {
                    base_promotionDesc = cc.toString().substring(4);
                    base_promotionDesc = base_promotionDesc.substring(0, base_promotionDesc.length() - 2);
                }
                if (key.equals("point")) {
                    base_point = cc.toString().substring(4);
                    base_point = base_point.substring(0, base_point.length() - 2);
                }
                if (key.equals("rank")) {
                    base_rank = cc.toString().substring(4);
                    base_rank = base_rank.substring(0, base_rank.length() - 2);
                }
                LOG.info(key);
                LOG.info(cc.toString());
                LOG.info(base_rank);
            }
        }
        String s = String.valueOf(cc);
        return "{\"jan\":\"" + jan + "\",\"point\":\"" + base_point + "\",\"PromotionDesc\":\"" + base_promotionDesc + "\",\"rank\":\"" + base_rank + "\"}";
    }
    @Post("/pepost")
    @Produces(MediaType.APPLICATION_JSON)
    public String postEvent() {
        //url = new URL("https://3bd3af9o6a.execute-api.us-east-1.amazonaws.com/p/js");
        amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.AP_NORTHEAST_1).build();
//        String jan = body.getJan();
//        String rank = body.getRank();
//        String point = body.getPoint();
        String jan = "1234567890234";
        String rank = "2";
        LOG.info("Local_Test4_murugan");
        LOG.info(jan);
        HashMap<String, Condition> scanFilter = new HashMap<>();
        scanFilter.put("jan", new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
                        .withAttributeValueList(new AttributeValue().withS(jan)));
        scanFilter.put("rank", new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(rank)));
        ScanRequest scanRequest = new ScanRequest("pac_all").withScanFilter(scanFilter);
        ScanResult scanResult = amazonDynamoDBClient.scan(scanRequest);
        List<java.util.Map<String, AttributeValue>> aa = scanResult.getItems();
        AttributeValue cc;
        String base_masterStoreCode = "";
        String base_maStoreCode = "";
        String base_promotionCode = "";
        String base_promotionDesc = "";
        String base_point = "";
        String s = jan.substring(0,5);
        LOG.info(s);
        for (int i = 1; i < aa.size(); i++) {
            java.util.Map<String, AttributeValue> bb = aa.get(i);
            Iterator<String> iterator = bb.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                cc = bb.get(key);
                if (key.equals("jan")) {
                    base_masterStoreCode = jan.substring(0, 5);
                    base_maStoreCode = jan.substring(5, 6);
                    base_promotionCode = jan.substring(6, 10);
                }
                LOG.info(base_masterStoreCode);
                if (key.equals("promotionDesc")) {
                    base_promotionDesc = cc.toString().substring(4);
                    base_promotionDesc = base_promotionDesc.substring(0, base_promotionDesc.length() - 2);
                }
//                if (key.equals("point")) {
//                    base_point = cc.toString().substring(4);
//                    base_point = base_point.substring(0, base_point.length() - 2);
//                }
                LOG.info(cc.toString());
                LOG.info(base_masterStoreCode);
            }
            // return "{\"jan\":\"" + jan + "\",\"MasterStroreCode\":\"" + base_masterStoreCode + "\",\"MaStoreCode\":\"" + base_maStoreCode + "\",\"PromotionCode\":\"" + base_promotionCode + "\",\"RewardCode\":\"" + base_rewardCode + "\"}";
        }
        return "{\n" +
                "  \"MEMBER_INFO\": {\n" +
                "    \"MEMBER_RANK\":\"" + rank + "\",\n" +
                "    \"PROMOTION\": [\n" +
                "      {\n" +
                "        \"PROMOTION_CODE\":\"" + base_promotionCode + "\",\n" +
                "        \"PROMOTION_DESC\":\"" + base_promotionDesc + "\",\n" +
                "        \"ITERM-INFO\": [\n" +
                "          {\"JAN_CODE\":\"" + jan + "\", \"POINT_PLUS\":\"" + base_point + "\",\"STORE_CODE\":\"" + base_maStoreCode + "\"},\n" +
                "        ]\n" +
                "      },\n" +
                "    ]\n" +
                "  }\n" +
                "\"}";
            }

        @Get("/find/{number}")
        public PrimeFinderResponse findPrimesBelow ( int number){
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

        @Get("/test")
        public String test() {
            return "successfully tested";
        }
    }