package io.insource.spring.ws.examples.rest.dto;

public class CommitsDto {
    private final String owner;
    private final String repo;
    private final int recentCommits;

    public CommitsDto(String owner, String repo, int recentCommits) {
        this.owner = owner;
        this.repo = repo;
        this.recentCommits = recentCommits;
    }

    public String getOwner() {
        return owner;
    }

    public String getRepo() {
        return repo;
    }

    public int getRecentCommits() {
        return recentCommits;
    }
}
