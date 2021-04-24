package com.m76.justnote.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Note.TABLE_NAME)
public class Note {

    public static final String TABLE_NAME = "notes";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 1, max = 30)
    private String noteTitle;

    @Length(min = 1, max = 500)
    private String noteContent;
}
