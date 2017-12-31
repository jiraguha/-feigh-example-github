package feign.example.github;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GitHubClient {

    @RequestLine("GET /users/{username}/repos?sort=full_name")
    List<Repository> repos(@Param("username") String owner);

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

}
