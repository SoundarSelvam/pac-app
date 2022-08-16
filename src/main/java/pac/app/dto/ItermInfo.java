package pac.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ItermInfo {

    @JsonProperty("JAN_CODE")
    private String janCode;

    @JsonProperty("POINT_PLUS")
    private String pointPlus;

    @JsonProperty("STORE_CODE")
    private String storeCode;

    public String getJanCode() {
        return janCode;
    }

    public void setJanCode(String janCode) {
        this.janCode = janCode;
    }

    public String getPointPlus() {
        return pointPlus;
    }

    public void setPointPlus(String pointPlus) {
        this.pointPlus = pointPlus;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Override
    public String toString() {
        return "ItermInfo{" +
                "janCode='" + janCode + '\'' +
                ", pointPlus='" + pointPlus + '\'' +
                ", storeCode='" + storeCode + '\'' +
                '}';
    }
}
