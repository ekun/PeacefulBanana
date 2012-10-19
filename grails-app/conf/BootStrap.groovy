
import org.peaceful.banana.Role
import org.peaceful.banana.User
import org.peaceful.banana.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def testUser = new User(username: 'user', enabled: true, password: 'password')
        testUser.save(flush: true)

        UserRole.create testUser, adminRole, true

    }
    def destroy = {
    }
}
