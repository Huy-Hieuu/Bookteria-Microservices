package org.huyhieu.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *
 * @author huy-hieu
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractAuditableEntity extends AbstractEntity {

    @Size(max = 128)
    @Column(name = "USR_LOG_I", updatable = false)
    private String createdBy;

    @Column(name = "DTE_LOG_I", updatable = false)
    private LocalDateTime creationDate;

    @Size(max = 128)
    @Column(name = "USR_LOG_U")
    private String lastModifiedBy;

    @Column(name = "DTE_LOG_U")
    private LocalDateTime lastModificationDate;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @PrePersist
    public void beforeSave() {//todo: temp comment out
//        setCreatedBy(UserUtils.getUserName());
//        setCreationDate(DateUtils.getCurrentDateTime());
//        setLastModifiedBy(UserUtils.getUserName());
//        setLastModificationDate(DateUtils.getCurrentDateTime());
    }

    @PreUpdate
    public void beforeUpdate() {
//        setLastModifiedBy(UserUtils.getUserName());
//        setLastModificationDate(DateUtils.getCurrentDateTime());
    }
}
