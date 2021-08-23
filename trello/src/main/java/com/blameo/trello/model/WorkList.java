package com.blameo.trello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_list")
public class WorkList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_list_id")
    private Long workListId;

    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "display_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long displayOrder;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "workList", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
