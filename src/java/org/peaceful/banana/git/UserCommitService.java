package org.peaceful.banana.git;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_COMMITS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_FIRST;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_SIZE;

import com.google.gson.reflect.TypeToken;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.CommitService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ekun
 * Date: 31.10.12
 * Time: 14:45
 */
public class UserCommitService extends CommitService {

    public UserCommitService(){
        super();
    }

    public UserCommitService(GitHubClient gitHubClient) {
        super(gitHubClient);
    }

    public PageIterator<RepositoryCommit> pageCommits(
            IRepositoryIdProvider repository, String author, int size) {
        String id = getId(repository);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_COMMITS);
        PagedRequest<RepositoryCommit> request = createPagedRequest(PAGE_FIRST,
                size);
        request.setUri(uri);
        request.setType(new TypeToken<List<RepositoryCommit>>() {
        }.getType());

        if (author != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("author", author); //$NON-NLS-1$
            request.setParams(params);
        }

        return createPageIterator(request);
    }

    public PageIterator<RepositoryCommit> pageCommits(
            IRepositoryIdProvider repository,String author, Date since, Date until, int size) {
        String id = getId(repository);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_COMMITS);
        PagedRequest<RepositoryCommit> request = createPagedRequest(PAGE_FIRST,
                size);
        request.setUri(uri);
        request.setType(new TypeToken<List<RepositoryCommit>>() {
        }.getType());

        if (since != null || until != null || author != null) {
            Map<String, String> params = new HashMap<String, String>();
            DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
            if(since != null){
                params.put("since", formatter.print(since.getTime())); //$NON-NLS-1$
            }
            if(until != null){
                params.put("until", formatter.print(until.getTime())); //$NON-NLS-1$
            }
            if(author != null)
                params.put("author", author);
            request.setParams(params);
        }

        return createPageIterator(request);
    }

    public List<RepositoryCommit> getCommits(IRepositoryIdProvider repository,
                                             String login) throws IOException {
        return getAll(pageCommits(repository, login, PAGE_SIZE));
    }

    public List<RepositoryCommit> getCommits(IRepositoryIdProvider repository,
                                             String login, Date since, Date until) throws IOException {
        return getAll(pageCommits(repository, login, since, until, PAGE_SIZE));
    }
}
