package com.example.concedii;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "oferte")
public class HomeExchange implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo(name="adresa")
    private String adresa;
    @ColumnInfo(name="numarCamere")
    private int numarCamere;
    @ColumnInfo(name="suprafata")
    private float suprafata;
    @ColumnInfo(name="perioada")
    private Date perioada;
    @ColumnInfo(name="tipLocuinta")
    private String tipLocuinta;

    public HomeExchange(long id, String adresa, int numarCamere, float suprafata, Date perioada, String tipLocuinta) {
        this.id = id;
        this.adresa = adresa;
        this.numarCamere = numarCamere;
        this.suprafata = suprafata;
        this.perioada = perioada;
        this.tipLocuinta = tipLocuinta;
    }

    @Ignore
    public HomeExchange(String adresa, int numarCamere, float suprafata, Date perioada, String tipLocuinta) {
        this.adresa = adresa;
        this.numarCamere = numarCamere;
        this.suprafata = suprafata;
        this.perioada = perioada;
        this.tipLocuinta = tipLocuinta;
    }

    protected HomeExchange(Parcel in) {
        adresa = in.readString();
        numarCamere = in.readInt();
        suprafata = in.readFloat();
        tipLocuinta = in.readString();
        perioada = new Date(in.readLong());
    }

    public static final Creator<HomeExchange> CREATOR = new Creator<HomeExchange>() {
        @Override
        public HomeExchange createFromParcel(Parcel in) {
            return new HomeExchange(in);
        }

        @Override
        public HomeExchange[] newArray(int size) {
            return new HomeExchange[size];
        }
    };

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNumarCamere() {
        return numarCamere;
    }

    public void setNumarCamere(int numarCamere) {
        this.numarCamere = numarCamere;
    }

    public float getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(float suprafata) {
        this.suprafata = suprafata;
    }

    public Date getPerioada() {
        return perioada;
    }

    public void setPerioada(Date perioada) {
        this.perioada = perioada;
    }

    public String getTipLocuinta() {
        return tipLocuinta;
    }

    public void setTipLocuinta(String tipLocuinta) {
        this.tipLocuinta = tipLocuinta;
    }

    @Override
    public String toString() {
        return "HomeExchange{" +
                "adresa='" + adresa + '\'' +
                ", numarCamere=" + numarCamere +
                ", suprafata=" + suprafata +
                ", perioada=" + perioada +
                ", tipLocuinta='" + tipLocuinta + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adresa);
        dest.writeInt(numarCamere);
        dest.writeFloat(suprafata);
        dest.writeString(tipLocuinta);
        dest.writeLong(this.getPerioada().getTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
