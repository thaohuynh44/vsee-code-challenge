package com.github.api;

import com.github.api.enums.AllowedGetOrgRepoQueryParam;
import com.github.api.client.RepositoryClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoriesTests {

    private RepositoryClient repositoryClient;
    private String ORGANIZATION = "SeleniumHQ";

    @BeforeTest
    public void setupClient() {
        this.repositoryClient = new RepositoryClient();
    }

    @Test
    public void getTotalOpenIssues() {

        // How many open issues are there across all repositories
        Response response = repositoryClient.listOrganizationRepositories(ORGANIZATION);

        response.then().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map<String, ?>> repositories = jsonPathEvaluator.getList("$");

        long totalOpenIssues = 0;
        for (Map<String, ?> repo : repositories) {
            if (repo.containsKey("open_issues_count") && repo.get("open_issues_count") instanceof Integer) {
                totalOpenIssues += ((Integer) repo.get("open_issues_count")).longValue();
            }
        }

        System.out.println("Total open issues across all repositories for " + ORGANIZATION + ": " + totalOpenIssues);
    }

    @Test
    public void sortRepositoriesByDateUpdated() {

        // Sort the repositories by date updated in descending order.

        Map<AllowedGetOrgRepoQueryParam, String> queryParams = new HashMap<>();
        queryParams.put(AllowedGetOrgRepoQueryParam.TYPE, "all");
        queryParams.put(AllowedGetOrgRepoQueryParam.SORT, "updated");
        queryParams.put(AllowedGetOrgRepoQueryParam.DIRECTION, "desc");
        queryParams.put(AllowedGetOrgRepoQueryParam.PER_PAGE, "100");

        Response response = repositoryClient.listOrganizationRepositoriesWithValidParams(ORGANIZATION, queryParams);
        response.then().statusCode(200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map<String, String>> repositories = jsonPathEvaluator.getList("$");

        System.out.println("Top 10 repositories by last update:");
        for (int i = 0; i < Math.min(repositories.size(), 10); i++) {
            Map<String, String> repo = repositories.get(i);
            System.out.println("- " + repo.get("name") + " (Last Updated: " + repo.get("updated_at") + ")");
        }
        if (repositories.isEmpty()) {
            System.out.println("No repositories found or could not retrieve.");
        }

    }

    @Test
    public void getRepositoryWithMostWatchers() {

        // Which repository has the most watchers?

        Map<AllowedGetOrgRepoQueryParam, String> queryParams = new HashMap<>();
        queryParams.put(AllowedGetOrgRepoQueryParam.PER_PAGE, "100");

        Response response = repositoryClient.listOrganizationRepositoriesWithValidParams(ORGANIZATION, queryParams);
        response.then().statusCode(200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map<String, ?>> repositories = jsonPathEvaluator.getList("$");

        String repoWithMostWatchersName = "";
        long maxWatchers = -1;

        if (repositories == null || repositories.isEmpty()) {
            System.out.println("No repositories found to determine the one with most watchers.");
            return;
        }

        for (Map<String, ?> repo : repositories) {
            // 'stargazers_count' is typically what people mean by "watchers" for public repos.
            // The actual 'subscribers_count' is for genuine "watchers" (receiving notifications),
            // but is less commonly used for popularity metrics.
            if (repo.containsKey("stargazers_count") && repo.get("stargazers_count") instanceof Integer) {
                int currentWatchers = (Integer) repo.get("stargazers_count");
                if (currentWatchers > maxWatchers) {
                    maxWatchers = currentWatchers;
                    repoWithMostWatchersName = (String) repo.get("name");
                }
            }
        }

        if (maxWatchers != -1) {
            System.out.println("Repository with the most watchers: " + repoWithMostWatchersName + " (" + maxWatchers + " watchers)");
        } else {
            System.out.println("Could not determine repository with most watchers.");
        }
    }
}
