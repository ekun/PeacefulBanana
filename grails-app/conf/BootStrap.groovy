
import org.peaceful.banana.Role
import org.peaceful.banana.User
import org.peaceful.banana.UserRole
import org.peaceful.banana.Team
import org.peaceful.banana.TeamUser

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def testUser = new User(username: 'even', enabled: true, password: 'password', firstName: 'Even', lastName: 'Stene', email: 'najtrox@gmail.com')
        testUser.save(flush: true)
        UserRole.create testUser, adminRole, true

        testUser = new User(username: 'marius', enabled: true, password: 'password', firstName: 'Marius', lastName: 'Glittum', email: 'ekunamatata@gmail.com')
        testUser.save(flush:  true)

        def team = new Team(name: "Test")
        team.save(flush: true)

        TeamUser.create testUser, team, true

        UserRole.create testUser, userRole, true

    }
    def destroy = {
    }
}
