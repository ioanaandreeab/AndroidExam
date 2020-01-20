package com.example.autovehicul;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "autovehicule")
public class Autovehicul implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name="numarAuto")
    private String numarAuto;
    @ColumnInfo(name = "dataInregistrarii")
    private Date dataInregistrarii;
    @ColumnInfo(name="idLocParcare")
    private int idLocParcare;
    @ColumnInfo(name = "aPlatit")
    private boolean aPlatit;


    @Ignore
    public Autovehicul(String numarAuto, Date dataInregistrarii, int idLocParcare, boolean aPlatit) {
        this.numarAuto = numarAuto;
        this.dataInregistrarii = dataInregistrarii;
        this.idLocParcare = idLocParcare;
        this.aPlatit = aPlatit;
    }

    public Autovehicul(int id, String numarAuto, Date dataInregistrarii, int idLocParcare, boolean aPlatit) {
        this.id = id;
        this.numarAuto = numarAuto;
        this.dataInregistrarii = dataInregistrarii;
        this.idLocParcare = idLocParcare;
        this.aPlatit = aPlatit;
    }

    protected Autovehicul(Parcel in) {
        numarAuto = in.readString();
        idLocParcare = in.readInt();
        aPlatit = in.readByte() != 0;
        dataInregistrarii = new Date(in.readLong());
    }

    public static final Creator<Autovehicul> CREATOR = new Creator<Autovehicul>() {
        @Override
        public Autovehicul createFromParcel(Parcel in) {
            return new Autovehicul(in);
        }

        @Override
        public Autovehicul[] newArray(int size) {
            return new Autovehicul[size];
        }
    };

    public String getNumarAuto() {
        return numarAuto;
    }

    public void setNumarAuto(String numarAuto) {
        this.numarAuto = numarAuto;
    }

    public Date getDataInregistrarii() {
        return dataInregistrarii;
    }

    public void setDataInregistrarii(Date dataInregistrarii) {
        this.dataInregistrarii = dataInregistrarii;
    }

    public int getIdLocParcare() {
        return idLocParcare;
    }

    public void setIdLocParcare(int idLocParcare) {
        this.idLocParcare = idLocParcare;
    }

    public boolean getAPlatit() {
        return aPlatit;
    }

    public void setaPlatit(boolean aPlatit) {
        this.aPlatit = aPlatit;
    }

    @Override
    public String toString() {
        return "Autovehicul{" +
                "numarAuto='" + numarAuto + '\'' +
                ", dataInregistrarii=" + dataInregistrarii +
                ", idLocParcare=" + idLocParcare +
                ", aPlatit=" + aPlatit +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numarAuto);
        dest.writeInt(idLocParcare);
        dest.writeByte((byte) (aPlatit ? 1 : 0));
        dest.writeLong(dataInregistrarii.getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
