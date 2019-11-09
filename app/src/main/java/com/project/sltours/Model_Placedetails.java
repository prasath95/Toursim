package com.project.sltours;

public class Model_Placedetails {
    private String Placename;
    private String img;
    private String rating;
    private String description;
    private String refkey;
    private String getRefkey2;

    public Model_Placedetails(String placename, String img, String rating, String description, String refkey, String getRefkey2) {
        Placename = placename;
        this.img = img;
        this.rating = rating;
        this.description = description;
        this.refkey = refkey;
        this.getRefkey2 = getRefkey2;
    }

    public Model_Placedetails() {
    }

    public String getPlacename() {
        return Placename;
    }

    public void setPlacename(String placename) {
        Placename = placename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefkey() {
        return refkey;
    }

    public void setRefkey(String refkey) {
        this.refkey = refkey;
    }

    public String getGetRefkey2() {
        return getRefkey2;
    }

    public void setGetRefkey2(String getRefkey2) {
        this.getRefkey2 = getRefkey2;
    }
}
