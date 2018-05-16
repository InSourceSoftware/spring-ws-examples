package io.insource.spring.ws.examples.rest.controller;

import io.insource.spring.ws.examples.rest.dto.CommitsDto;
import io.insource.spring.ws.examples.rest.service.CommitService;
import io.insource.spring.ws.examples.rest.service.GitHubProxy;

import com.github.api.repos.commits.CommitRs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/v1")
public class CommitController {
    private static final String OWNER = "torvalds";
    private static final String REPO = "linux";

    private final CommitService commitService;

    @Inject
    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping("/commits")
    public CommitsDto getRecentCommitsStats() {
        CommitRs[] commits = commitService.getRecentCommits(OWNER, REPO);
        return new CommitsDto(OWNER, REPO, commits.length);
    }
}
