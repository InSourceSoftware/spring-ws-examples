package io.insource.spring.ws.examples.rest.service;

import com.github.api.repos.commits.CommitRs;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

/**
 * Proxy for the GitHub API.
 */
@Component
public class GitHubProxy {
    /**
     * API base URL.
     */
    private static final String BASE_URL = "https://api.github.com";

    /**
     * Rest client.
     */
    private final RestTemplate restTemplate;

    @Inject
    public GitHubProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve a list of commits for the last 7 days for a public repository.
     *
     * @param owner The organization owner
     * @param repo The repository name
     * @return The list of commits
     */
    public CommitRs[] getRecentCommits(String owner, String repo) {
        return doRequest(
            HttpMethod.GET,
            "/repos/{owner}/{repo}/commits?since={since}",
            null,
            CommitRs[].class,
            owner,
            repo,
            Instant.now().minus(Duration.ofDays(7)).toString()
        );
    }

    /**
     * Internal utility method to easily customize request, including headers.
     *
     * @param httpMethod The HTTP method, e.g. GET, POST, PUT, DELETE, etc.
     * @param route The route portion of the request URI, e.g. /users/101
     * @param requestBody The request object, or null if a GET request
     * @param responseClass The response type to unmarshall response into
     * @param uriVariables The list of values that map to path variables in the route
     * @param <T> The response type
     * @return An object containing the response
     */
    private <T> T doRequest(HttpMethod httpMethod, String route, Object requestBody, Class<T> responseClass, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // TODO: Add additional httpHeaders such as Authorization here...

        return restTemplate.exchange(
            BASE_URL + route,
            httpMethod,
            new HttpEntity<>(requestBody, httpHeaders),
            responseClass,
            uriVariables
        ).getBody();
    }
}
