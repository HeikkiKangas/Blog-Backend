package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long postID;
    private String author;
    private String text;
    private long likes;
    private Date postTime;
}
