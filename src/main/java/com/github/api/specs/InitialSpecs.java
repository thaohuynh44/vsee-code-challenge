package com.github.api.specs;

import com.vsee.utils.Config;
import com.vsee.utils.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class InitialSpecs {

    private InitialSpecs() {}

    public static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder().
                setBaseUri(Config.get(Constants.GITHUB_API_URL)).
                build();
    }

    public static ResponseSpecification defaultResponseSpec() {
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }
}
