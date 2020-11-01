package com.alejandrachirinos.mydictionary;

import android.os.Parcel;
import android.os.Parcelable;

public class modelWords implements Parcelable {
    private long id;
    private String name;
    private String description;

    public modelWords(long id, String name, String description) {
        this.id=id;
        this.name=name;
        this.description=description;
    }

    protected modelWords(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<modelWords> CREATOR = new Creator<modelWords>() {
        @Override
        public modelWords createFromParcel(Parcel in) {
            return new modelWords(in);
        }

        @Override
        public modelWords[] newArray(int size) {
            return new modelWords[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
    }
}


