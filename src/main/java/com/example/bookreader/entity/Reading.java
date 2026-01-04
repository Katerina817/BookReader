package com.example.bookreader.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "readings")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ReadingStatus status;

    private LocalDateTime dateStartOfReading;
    @PrePersist
    protected void onCreate() {
        dateStartOfReading = LocalDateTime.now();
    }

    private LocalDateTime dateFinishOfReading;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column
    @Min(1)@Max(10)
    private Integer evaluationOfCharacter;

    @Column
    @Min(1)@Max(10)
    private Integer evaluationOfPlot;

    @Column
    @Min(1)@Max(10)
    private Integer evaluationOfEmotions;

    @Column
    @Min(1)@Max(10)
    private Integer qualityOfDialog;

    @Column
    @Min(1)@Max(10)
    private Integer atmosphere;

    @Column(columnDefinition = "text")
    private String review;

    public Reading() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateStartOfReading() {
        return dateStartOfReading;
    }

    public void setDateStartOfReading(LocalDateTime dateStartOfReading) {
        this.dateStartOfReading = dateStartOfReading;
    }

    public LocalDateTime getDateFinishOfReading() {
        return dateFinishOfReading;
    }

    public void setDateFinishOfReading(LocalDateTime dateFinishOfReading) {
        this.dateFinishOfReading = dateFinishOfReading;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getEvaluationOfCharacter() {
        return evaluationOfCharacter;
    }

    public void setEvaluationOfCharacter(Integer evaluationOfCharacter) {
        this.evaluationOfCharacter = evaluationOfCharacter;
    }

    public Integer getEvaluationOfPlot() {
        return evaluationOfPlot;
    }

    public void setEvaluationOfPlot(Integer evaluationOfPlot) {
        this.evaluationOfPlot = evaluationOfPlot;
    }

    public Integer getEvaluationOfEmotions() {
        return evaluationOfEmotions;
    }

    public void setEvaluationOfEmotions(Integer evaluationOfEmotions) {
        this.evaluationOfEmotions = evaluationOfEmotions;
    }

    public Integer getQualityOfDialog() {
        return qualityOfDialog;
    }

    public void setQualityOfDialog(Integer qualityOfDialog) {
        this.qualityOfDialog = qualityOfDialog;
    }

    public Integer getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Integer atmosphere) {
        this.atmosphere = atmosphere;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
