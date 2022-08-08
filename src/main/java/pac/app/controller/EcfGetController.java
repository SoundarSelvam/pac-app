package pac.app.controller;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.io.IOException;
import java.util.Iterator;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller("/ecfGet")
public class EcfGetController {
    private static final Logger LOG = LoggerFactory.getLogger(EcfGetController.class);
    private static AmazonDynamoDB amazonDynamoDBClient = null;
    private static Table table = null;
    @Get("/pe001")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEvent(@Body String body) {
        amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.AP_NORTHEAST_1).build();
        LOG.info("Local Test7 murugan");
        body = "jan:1234567890234";
        LOG.info(body);
        String [] s1 = body.split(":");
        String jan = s1[1];
        String rank="2";
        LOG.info(jan + "::" + s1[1].length());
        HashMap<String, Condition> scanFilter = new HashMap<>();
        Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(jan));
        Condition condition1 = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(rank));
        scanFilter.put("jan", condition);
        scanFilter.put("rank",condition1);
        ScanRequest scanRequest1 = new ScanRequest("pac_all").withScanFilter(scanFilter);
        ScanResult scanResult1 = amazonDynamoDBClient.scan(scanRequest1);
        List<java.util.Map<String, AttributeValue>> aa = scanResult1.getItems();
        AttributeValue cc = new AttributeValue();
        String base_masterStoreCode = "";
        String base_maStoreCode = "";
        String base_promotionCode = "";
        String base_rewardCode = "";
        String base_promotionDesc = "";
        String base_point = "";
        for (int i = 1; i < aa.size(); i++) {
            java.util.Map<String, AttributeValue> bb = aa.get(i);
            Iterator<String> iterator = bb.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                cc = bb.get(key);
                if (key.equals("jan")) {
                    base_masterStoreCode = jan.substring(0,5);
                    base_maStoreCode = jan.substring(5,6);
                    base_promotionCode = jan.substring(6,10);
                    base_rewardCode = jan.substring(10);
                }
                if(key.equals("PromotionDesc"))
                {
                    base_promotionDesc = cc.toString().substring(4);
                    base_promotionDesc = base_promotionDesc.substring(0, base_promotionDesc.length() - 2);
                }
                if (key.equals("point")) {
                    base_point = cc.toString().substring(4);
                    base_point = base_point.substring(0, base_point.length() - 2);
                }
            LOG.info(cc.toString());
            LOG.info(base_masterStoreCode);
        }
       // return "{\"jan\":\"" + jan + "\",\"MasterStroreCode\":\"" + base_masterStoreCode + "\",\"MaStoreCode\":\"" + base_maStoreCode + "\",\"PromotionCode\":\"" + base_promotionCode + "\",\"RewardCode\":\"" + base_rewardCode + "\"}";
        return  "{{\n" +
                "  \"MEMBER_INFO\": {\n" +
                "    \"MEMBER_RANK\": \"" +rank+ "\",\n" +
                "    \"PROMOTION\": [\n" +
                "      {\n" +
                "        \"PROMOTION_CODE\":\""+ base_promotionCode +"\""; +
                "        \"PROMOTION_DESC\":\"" + base_promotionDesc +"\"";  +
                "        \"ITERM-INFO\": [\n" +
                "          {\"JAN_CODE\":\""+jan+"\"", "\"POINT_PLUS\": \""+base_point+"\"","\"STORE_CODE\": \""+base_maStoreCode}" +
                "        ]\n" +
                "      },\n" +
                "    ]\n" +
                "  }\n" +
                "}" +
                "}"
    }

}
