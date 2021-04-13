package yelm.io.template.item.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Modifier implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("value")
    @Expose
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modifier modifier = (Modifier) o;
        return Objects.equals(name, modifier.name);
//        return Objects.equals(name, modifier.name) &&
//                Objects.equals(value, modifier.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NotNull
    @Override
    public String toString() {
        return "Modifier{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public Modifier(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
