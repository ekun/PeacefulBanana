package org.peaceful.banana

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
    String email
    String firstName
    String lastName
    long selectedRepo
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    String oAuthToken


	static constraints = {
		username blank: false, unique: true
		password blank: false
        oAuthToken nullable: true
        email email: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
