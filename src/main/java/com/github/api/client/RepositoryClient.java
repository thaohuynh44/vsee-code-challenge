package com.github.api.client;

import com.github.api.enums.AllowedGetOrgRepoQueryParam;
import com.github.api.specs.InitialSpecs;
import com.github.api.specs.RepositorySpecs;
import io.restassured.response.Response;

import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RepositoryClient {

    public Response listOrganizationRepositories(String organizationName) {
        return given()
                .spec(RepositorySpecs.getOrganizationRepositories())
                .pathParam("org", organizationName)
                .when().get()
                .then()
                .spec(InitialSpecs.defaultResponseSpec())
                .extract().response();
    }

    public Response listOrganizationRepositoriesWithValidParams(String organizationName, Map<AllowedGetOrgRepoQueryParam, String> queryParams) {

        return given()
                .spec(RepositorySpecs.getOrganizationRepositories())
                .pathParam("org", organizationName)
                .queryParams(queryParams.entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toString(),
                                Map.Entry::getValue
                        )))
                .when().get()
                .then()
                .spec(InitialSpecs.defaultResponseSpec())
                .extract().response();
    }
}


