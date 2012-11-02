package org.peaceful.banana.git;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.scribe.model.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ekun
 * Date: 02.11.12
 * Time: 09:14
 */
public class GitHubService {

    private GitHubClient gitHubClient;
    private RepositoryService repositoryService;
    private UserCommitService userCommitService;

    public GitHubService(Token gitHubToken) {
        gitHubClient = new GitHubClient();
        this.gitHubClient.setOAuth2Token(gitHubToken.getToken());
        this.repositoryService = new RepositoryService(gitHubClient);
        this.userCommitService = new UserCommitService(gitHubClient);
    }

    public void setToken(Token gitHubToken) {
        this.gitHubClient.setOAuth2Token(gitHubToken.getToken());
    }

    /**
     * Retrivies all the repositories the currently authenticated user is connected with
     * @return
     */
    public List<Repository> getRepositories() {
        try {
            return repositoryService.getRepositories();
        } catch (IOException e) {
            return new ArrayList<Repository>();
        }
    }

    /**
     * Will return a list withe the number of commits each user have done to the repo
     * @param repository
     */
    public ArrayList<Object[]> getRepositoryCommitStats(Repository repository) {
        List<Contributor> collaborators = null;
        ArrayList<Object[]> commitsPerUser = new ArrayList();
        try {
            collaborators = repositoryService.getContributors(repository, true);
            for(Contributor contributor : collaborators) {
                Object[] array = new Object[2];
                if (contributor.getLogin() != null) {
                    array[0] = contributor.getLogin();
                    array[1] = userCommitService.getCommits(repository, contributor.getLogin()).size();
                } else {
                    array[0] = contributor.getName();
                    array[1] = userCommitService.getCommits(repository, contributor.getName()).size();
                }
                commitsPerUser.add(array);
            }
        } catch (IOException e) {
            return new ArrayList<Object[]>();
        }
        return commitsPerUser;
    }

    public Repository getRepository(long repositoryId) {
        List<Repository> repositoryList = getRepositories();
        for(Repository repository : repositoryList) {
            if(repository.getId() == repositoryId)
                return repository;
        }
        return null;
    }
}
