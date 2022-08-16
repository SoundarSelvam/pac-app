package pac.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class Promotion {
    @JsonProperty("PROMOTION_CODE")
    private String promotionCode;

    @JsonProperty("PROMOTION_DESC")
    private String promotionDesc;

    @JsonProperty("ITERM-INFO")
    private ItermInfo itermInfo;

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionDesc() {
        return promotionDesc;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promotionDesc = promotionDesc;
    }

    public ItermInfo getItermInfo() {
        return itermInfo;
    }

    public void setItermInfo(ItermInfo itermInfo) {
        this.itermInfo = itermInfo;
    }


}
