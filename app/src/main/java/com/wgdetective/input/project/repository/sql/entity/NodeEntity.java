package com.wgdetective.input.project.repository.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Node entity.
 */
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Node")
@Entity
public final class NodeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long parentId;

    @Column(name = "node_value")
    private String value;

}
