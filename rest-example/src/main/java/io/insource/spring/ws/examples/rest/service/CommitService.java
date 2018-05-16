package io.insource.spring.ws.examples.rest.service;

import com.github.api.repos.commits.CommitRs;

public interface CommitService {
    CommitRs[] getRecentCommits(String owner, String repo);
}
