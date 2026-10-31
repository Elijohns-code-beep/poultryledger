package com.poultryledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chick_groups", uniqueConstraints = @UniqueConstraint(name = "uk_chick_group_code", columnNames = "code_name"))
public class ChickGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(name = "code_name", nullable = false, unique = true)
    private String codeName;
    @NotNull @Min(0) @Column(nullable = false)
    private Integer startingCount;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private GroupStatus status = GroupStatus.OPEN;
    @OneToOne @JoinColumn(name = "sitting_id", unique = true)
    private Sitting sitting;
    @OneToMany(mappedBy = "chickGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChickEvent> events = new ArrayList<>();

    public ChickGroup() {}
    public ChickGroup(String codeName, Integer startingCount) { this.codeName=codeName; this.startingCount=startingCount; }
    public Long getId() { return id; }
    public String getCodeName() { return codeName; }
    public void setCodeName(String codeName) { this.codeName = codeName; }
    public Integer getStartingCount() { return startingCount; }
    public void setStartingCount(Integer startingCount) { this.startingCount = startingCount; }
    public GroupStatus getStatus() { return status; }
    public void setStatus(GroupStatus status) { this.status = status; }
    public Sitting getSitting() { return sitting; }
    public void setSitting(Sitting sitting) { this.sitting = sitting; }
    public List<ChickEvent> getEvents() { return events; }
    public void setEvents(List<ChickEvent> events) { this.events = events; }
}
