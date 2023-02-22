package socialinsider.model;

public class BrandDetails {
    private String id;
    private Integer engagements;
    private String profile_type;
    private Integer fans;

    public String getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(String profile_type) {
        this.profile_type = profile_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getEngagements() {
        return engagements;
    }

    public void setEngagements(Integer engagements) {
        this.engagements = engagements;
    }
}
