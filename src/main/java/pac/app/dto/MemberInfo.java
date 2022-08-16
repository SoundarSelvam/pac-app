package pac.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
public class MemberInfo {
    @JsonProperty("MEMBER_RANK")
    private String memberRank;

    @JsonProperty("POINT_ALL")
    private String pointAll;

    @JsonProperty("PROMOTION")
    private List<Promotion> promotion;

    public String getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(String memberRank) {
        this.memberRank = memberRank;
    }

    public String getPointAll() {
        return pointAll;
    }

    public void setPointAll(String pointAll) {
        this.pointAll = pointAll;
    }

    public List<Promotion> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<Promotion> promotion) {
        this.promotion = promotion;
    }
}
