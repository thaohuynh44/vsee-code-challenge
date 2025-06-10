package com.github.api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class InitialSpecs {

    private InitialSpecs() {}

    public static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder().
                setBaseUri("https://api.github.com").
                build();
    }

    public static ResponseSpecification defaultResponseSpec() {
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }
}
