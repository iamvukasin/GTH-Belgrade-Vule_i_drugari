package io.github.iamvukasin.hacktravel.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Pet implements Parcelable {
    private String type;
    private String breed;
    private Integer age;
    private Integer weigth;

    public Pet(String type, String breed, Integer age, Integer weigth) {
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.weigth = weigth;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + type + "\"," +
                "\"breed\":\"" + breed + "\"," +
                "\"age\":" + age + "," +
                "\"weight\":" + weigth + "}";
    }

    protected Pet(Parcel in) {
        type = in.readString();
        breed = in.readString();
        age = in.readByte() == 0x00 ? null : in.readInt();
        weigth = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(breed);
        if (age == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(age);
        }
        if (weigth == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(weigth);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pet> CREATOR = new Parcelable.Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
}
