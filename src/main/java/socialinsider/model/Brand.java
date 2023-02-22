package socialinsider.model;

import java.util.ArrayList;

public class Brand {
    private String brandName;
    private int totalFans;
    private int totalEngagements;

    private int totalProfiles;

    public int getTotalProfiles() {
        return totalProfiles;
    }

    public void setTotalProfiles(int totalProfiles) {
        this.totalProfiles = totalProfiles;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public int getTotalEngagements() {
        return totalEngagements;
    }

    public void setTotalEngagements(int totalEngagements) {
        this.totalEngagements = totalEngagements;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}
