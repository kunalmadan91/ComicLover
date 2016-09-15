package com.kunalmadan.android.comiclover;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by KUNAL on 08-Sep-16.
 */
public class Comic implements Parcelable{

    private String title;
    private String description;
    private String price;
    private String image;
    private String thumbnnail;
    private String onSaleDate;
    private ArrayList<Comic> list;
    private String diamondCode;
    private String issueNumber;
    private String digitalDate;
    private String digitalPrie;

    public Comic(String title1, String description, String price,
                 String image, String thumbnail, String date, String diamondCode, String issueNumber) {

        title = title1;
        this.description = description;
        this.price = price;
        this.image = image;
        this.thumbnnail = thumbnail;
        this.onSaleDate = date;
        this.diamondCode = diamondCode;
        this.issueNumber = issueNumber;
        this.digitalDate = digitalDate;
        this.digitalPrie = digitalPrie;

    }

    protected Comic(Parcel in) {
        title = in.readString();
        description = in.readString();
        price = in.readString();
        image = in.readString();
        thumbnnail = in.readString();
        onSaleDate = in.readString();
        list = in.createTypedArrayList(Comic.CREATOR);
        diamondCode = in.readString();
        issueNumber = in.readString();
        digitalDate = in.readString();
        digitalPrie = in.readString();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public String getDiamondCode() {
        return diamondCode;
    }

    public void setDiamondCode(String diamondCode) {
        this.diamondCode = diamondCode;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getDigitalDate() {
        return digitalDate;
    }

    public void setDigitalDate(String digitalDate) {
        this.digitalDate = digitalDate;
    }

    public String getDigitalPrie() {
        return digitalPrie;
    }

    public void setDigitalPrie(String digitalPrie) {
        this.digitalPrie = digitalPrie;
    }





    public ArrayList<Comic> getList() {
        return list;
    }

    public void setList(ArrayList<Comic> list) {
        this.list = list;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnnail() {
        return thumbnnail;
    }

    public void setThumbnnail(String thumbnnail) {
        this.thumbnnail = thumbnnail;
    }

    public String getOnSaleDate() {
        return onSaleDate;
    }

    public void setOnSaleDate(String onSaleDate) {
        this.onSaleDate = onSaleDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeString(thumbnnail);
        dest.writeString(onSaleDate);
        dest.writeTypedList(list);
        dest.writeString(diamondCode);
        dest.writeString(issueNumber);
        dest.writeString(digitalDate);
        dest.writeString(digitalPrie);
    }
}
