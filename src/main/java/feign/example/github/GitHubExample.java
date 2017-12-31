/*
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package feign.example.github;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Inspired by {@code com.example.retrofit.GitHubClient}
 */
public class GitHubExample {

    private static GitHubClient github = ManualFeignClientConstructor.connect(GitHubClient.class,"https://api.github.com");

    public static void main(String... args) {
        System.out.println("Let's fetch and print a list of the contributors to this org.");
        List<String> contributors = contributors("netflix");
        for (String contributor : contributors) {
            System.out.println(contributor);
        }
        System.out.println("Now, let's cause an error.");
        try {
            github.contributors("netflix", "some-unknown-project");
        } catch (GitHubClientError e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all contributors for all repos owned by a user.
     */
    private static List<String> contributors(String owner) {
        return github.repos(owner).stream()
                .flatMap(repo -> github.contributors(owner, repo.getName()).stream())
                .map(Contributor::getLogin)
                .distinct()
                .collect(Collectors.toList());
    }
}
