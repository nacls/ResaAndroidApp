package ir.ceit.resa.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Announcement implements Comparable<Announcement>, Serializable {

    private Integer id;

    @SerializedName("timestamp")
    private Date creationDate;

    @SerializedName("message")
    private String message;

    @SerializedName("writer")
    private String writer;

    Announcement() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Override
    public int compareTo(Announcement o) {
        if (getCreationDate() == null) {
            return (o.getCreationDate() == null) ? 0 : 1;
        }
        if (o.getCreationDate() == null) {
            return -1;
        }

        return (getCreationDate().compareTo(o.getCreationDate())) * -1;
    }
}
