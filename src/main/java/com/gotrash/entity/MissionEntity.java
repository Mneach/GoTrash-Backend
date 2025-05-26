package com.gotrash.entity;

import com.gotrash.api.v1.model.TrashCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "missions", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionEntity {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID missionId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String goalType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal targetValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trash_category_id")
    private TrashCategoryEntity trashCategory;

    @Column(nullable = false)
    private BigDecimal rewardCoins;

    @Column(nullable = false)
    private LocalDate activeDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
