package com.github.api.enums;

public enum AllowedGetOrgRepoQueryParam {
    TYPE("type"),
    SORT("sort"),
    DIRECTION("direction"),
    PER_PAGE("per_page"),
    PAGE("page");


    private final String text;

    AllowedGetOrgRepoQueryParam(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
