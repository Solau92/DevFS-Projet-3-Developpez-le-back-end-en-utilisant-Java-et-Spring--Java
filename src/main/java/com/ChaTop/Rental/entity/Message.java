package com.ChaTop.Rental.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
@DynamicUpdate
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int rental_id;

    @Column
    private int user_id;

    @Column
    private String message;

    @Column
    private LocalDate created_at;

    @Column
    private LocalDate updated_at;


    public Message() {
    }

    public Message(int id, int rental_id, int user_id, String message, LocalDate created_at, LocalDate updated_at) {
        this.id = id;
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.message = message;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Message(int rental_id, int user_id, String message, LocalDate created_at) {
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.message = message;
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRental_id() {
        return this.rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", rental_id='" + getRental_id() + "'" +
            ", user_id='" + getUser_id() + "'" +
            ", message='" + getMessage() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            "}";
    }

}
