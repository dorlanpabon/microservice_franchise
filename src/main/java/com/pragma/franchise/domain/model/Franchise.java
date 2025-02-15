package com.pragma.franchise.domain.model;

import java.util.List;

public class Franchise {

    private Long id;
    private String name;
    private List<Branch> subsidiaries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getSubsidiaries() {
        return subsidiaries;
    }

    public void setSubsidiaries(List<Branch> subsidiaries) {
        this.subsidiaries = subsidiaries;
    }
}
