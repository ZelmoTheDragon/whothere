package com.github.zelmothedragon.whothere.absence;

import com.github.zelmothedragon.whothere.common.persistence.Agent;
import com.github.zelmothedragon.whothere.common.persistence.AbstractEntity;
import java.time.LocalDateTime;
import javax.enterprise.context.Dependent;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Absence d'un agent pendant une période.
 *
 * @author MOSELLE Maxime
 */
@Dependent
@Entity
@Table(
        name = "absence",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"agent_id", "state_of_absence_id", "start_date", "end_date"}
        )
)
@Access(AccessType.FIELD)
public class Absence extends AbstractEntity {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Agent absenté.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    /**
     * État d'absence.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "state_of_absence_id", nullable = false)
    private State state;

    /**
     * Date de début d'absence.
     */
    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * Date de fin d'absence.
     */
    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public Absence() {
        // Ne pas appeler explicitement.
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
