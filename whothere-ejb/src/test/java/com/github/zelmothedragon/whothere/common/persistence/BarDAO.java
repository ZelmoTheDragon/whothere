package com.github.zelmothedragon.whothere.common.persistence;

import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class BarDAO implements Serializable {

    @Inject
    transient EntityManager em;

    public BarDAO() {
        // Ne pas appeler explicitement.
    }

}
