package com.github.api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RepositorySpecs {

    private RepositorySpecs() {}

    public static RequestSpecification getOrganizationRepositories() {

        return new RequestSpecBuilder()
                .addRequestSpecification(InitialSpecs.defaultRequestSpec())
                .setBasePath("/orgs/{org}/repos")
                .setContentType(ContentType.JSON)
                .build();
    }
}
