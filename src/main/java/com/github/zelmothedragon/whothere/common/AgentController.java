package com.github.zelmothedragon.whothere.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MOSELLE Maxime
 */
@Named
@ViewScoped
public class AgentController implements Serializable {

    @Inject
    CommonService service;

    private String keyword;

    private List<Agent> agents;

    public AgentController() {
        this.agents = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
    }

    public void search() {
        if (isKeywordEmpty()) {
            this.agents.clear();
        } else {
            this.agents = service.filter(Agent.class, keyword);
        }
    }

    public boolean isKeywordEmpty() {
        return Objects.isNull(keyword)
                || keyword.isBlank()
                || keyword.length() <= 1;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

}
