package com.example.seekmeweb.Bean;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_ArticleComment")
public class ArticleComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reviewid",updatable = false, nullable = false)
    private Integer reviewid;
    @Column(name="parentsid", nullable = false)
    private Integer parentsid;
    @Column(name="commentusername", nullable = false)
    private String commentusername;
    @Column(name="comment", nullable = false)
    private String comment;
    @Column(name="visible", nullable = false)
    private Integer visible;

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public Integer getParentsid() {
        return parentsid;
    }

    public void setParentsid(Integer parentsid) {
        this.parentsid = parentsid;
    }

    public String getCommentusername() {
        return commentusername;
    }

    public void setCommentusername(String commentusername) {
        this.commentusername = commentusername;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }
}
