package io.insource.spring.ws.examples.rest.service.impl;

import io.insource.spring.ws.examples.rest.service.CommitService;
import io.insource.spring.ws.examples.rest.service.GitHubProxy;

import com.github.api.repos.commits.CommitRs;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CommitServiceImpl implements CommitService {
    private final GitHubProxy gitHubProxy;

    @Inject
    public CommitServiceImpl(GitHubProxy gitHubProxy) {
        this.gitHubProxy = gitHubProxy;
    }

    @Override
    public CommitRs[] getRecentCommits(String owner, String repo) {
        return gitHubProxy.getRecentCommits(owner, repo);
    }
}
