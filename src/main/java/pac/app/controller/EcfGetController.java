package pac.app.controller;
import pac.app.dto.Book;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller("/ecfGet")
public class EcfGetController {
    private static final Log LOG = LogFactory.getLog(EcfGetController.class);

    @Get("/pe001")
    public String getEvent(@Body String body) {
        LOG.info("Local Test7");
        body = "jan:1234567ABCDEF";
        LOG.info(body);
        String [] s1 = body.split(":");
        String jan = s1[1];
        LOG.info(jan + "::" + s1[1].length());
        amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.US_EAST_1).build();
        HashMap<String, Condition> scanFilter = new HashMap<>();
        Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(jan));
        scanFilter.put("jan", condition);
        ScanRequest scanRequest1 = new ScanRequest("pac_all").withScanFilter(scanFilter);
        ScanResult scanResult1 = amazonDynamoDBClient.scan(scanRequest1);
        List<java.util.Map<String, AttributeValue>> aa = scanResult1.getItems();
        LOG.info(aa.size());
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
                    base_masterStoreCode = jan.substring(0, 4);
                    base_maStoreCode = jan.substring(4, 6);
                    base_promotionCode = jan.substring(6, 10);
                    base_rewardCode = jan.substring(10);
                }
            }
            LOG.info(cc.toString());
            LOG.info(base_masterStoreCode);
        }
        return "{\"MasterStroreCode\":\"" + base_masterStoreCode + "\",\"MaStoreCode\":\"" + base_maStoreCode + "\",\"PromotionCode\":\"" + base_promotionCode + "\",\"RewardCode\":\"" + base_rewardCode + "\"}";
    }

}
