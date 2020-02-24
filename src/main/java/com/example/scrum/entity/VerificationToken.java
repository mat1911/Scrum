package com.example.scrum.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "verification_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expireDate;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expireDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
