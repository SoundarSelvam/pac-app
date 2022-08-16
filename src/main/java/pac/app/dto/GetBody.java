package pac.app.dto;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;

@Introspected
public class GetBody {
    @NotBlank
    private String jan;
    @NotBlank
    private String rank;
    @NotBlank
    private String point;
//    @NotBlank
//    private String storeCode;
    public String getJan() {
        return jan;
    }
    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPoint() {
        return point;
    }
    public void setPoint(String point) {
        this.point = point;
    }

//    public String getStoreCode() {
//        return storeCode;
//    }
//    public void setStoreCode(String storeCode) {
//        this.storeCode = storeCode;
//    }


    @Override
    public String toString() {
        return "GetBody{" +
                "jan='" + jan + '\'' +
                ", rank='" + rank + '\'' +
                ", point='" + point + '\'' +
                '}';
    }
}