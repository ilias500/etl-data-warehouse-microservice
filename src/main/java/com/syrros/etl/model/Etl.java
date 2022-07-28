package com.syrros.etl.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "etl")
public class Etl {

  @Id
  @Column(name = "id",nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "datasource")
  private String datasource;

  @Column(name = "campaign")
  private String campaign;

  @Column(name = "clicks")
  private Integer clicks;

  @Column(name = "impressions")
  private Integer impressions;

  @Column(name = "daily")
  private LocalDate daily;

}
