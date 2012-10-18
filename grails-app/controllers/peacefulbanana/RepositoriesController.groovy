package peacefulbanana

import org.peaceful.banana.domain.Repository

class RepositoriesController {

    def index() {
        def Repositories = new ArrayList<Repository>()

        for (int i=0; i < 10; i++) {
            Repositories.add(new Repository(ident: i,title: i + "" , description: i + ""))
        }

        [myRepos: Repositories.findAll()]
    }
}
