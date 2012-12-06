package org.peaceful.banana

import org.peaceful.banana.reflection.Note

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
    String gitLogin

	static constraints = {
		username blank: false, unique: true
		password blank: false
        oAuthToken nullable: true
        email email: true
        gitLogin nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

    List<Notification> getNotifications() {
        Notification.findAllByUserAndCleared(this, false, [sort: "dateCreated", order:'desc', max: '5']) as List
    }

    List<Notification> getAllNotifications() {
        Notification.findAllByUserAndCleared(this, false) as List
    }

    Notification getLatestReflectionNotification() {
        Notification.findByUserAndClearedAndNotificationType(this, false, NotificationType.REFLECTION, [sort: "dateCreated", order:'desc', max: '1'])
    }

    List<Note> getNotes() {
        Note.findAllByUser(this) as List
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
