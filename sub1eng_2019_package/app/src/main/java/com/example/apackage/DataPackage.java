package com.example.apackage;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "packages")
public class DataPackage implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name="packageId")
    private int packageId;
    @ColumnInfo(name="packageType")
    private String packageType;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name="timestamp")
    private Date timestamp;

    public DataPackage(int packageId, String packageType, double latitude, double longitude, Date timestamp) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    protected DataPackage(Parcel in) {
        packageId = in.readInt();
        packageType = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        timestamp = new Date(in.readLong());
    }

    public static final Creator<DataPackage> CREATOR = new Creator<DataPackage>() {
        @Override
        public DataPackage createFromParcel(Parcel in) {
            return new DataPackage(in);
        }

        @Override
        public DataPackage[] newArray(int size) {
            return new DataPackage[size];
        }
    };

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DataPackage{" +
                "packageId=" + packageId +
                ", packageType='" + packageType + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(packageId);
        dest.writeString(packageType);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeLong(timestamp.getTime());
    }
}
