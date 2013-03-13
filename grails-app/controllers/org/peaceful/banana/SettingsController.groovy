package org.peaceful.banana

import grails.plugins.springsecurity.ui.RegisterCommand
import groovy.text.SimpleTemplateEngine
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import org.peaceful.banana.git.GitHubService
import org.peaceful.banana.git.GithubSyncController
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

class SettingsController {

    static allowedMethods = [changeSelectedRepo: 'POST']

    def springSecurityService
    def mailService
    GitHubService gitHubService
    OauthService oauthService

    def index() {
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        def user = User.get(springSecurityService.principal.id)
        def errors = []
        def updated = false

        if (gitToken != null) {
            gitHubService = new GitHubService(gitToken)

            if (params.oldPassword && params.password && params.confirmPassword) {
                errors << [error: (user.password != springSecurityService.encodePassword(params.oldPassword)),
                        message: 'Incorrect password',
                        value: params.oldPassword]
                errors << [error: passwordValidator(params.password,new RegisterCommand()),
                        message: g.message(code: passwordValidator(params.password,new RegisterCommand())),
                        value: params.password]
                errors << [error: params.password != params.confirmPassword,
                        message: g.message(code: password2Validator(params.confirmPassword,new RegisterCommand())),
                        value: params.confirmPassword]

                if (!(errors[0].error && errors[1].error && errors[2].error)) {
                    user.password = params.password

                    user.save()

                    errors.each {
                        it.value = ""
                    }

                    updated = true
                }
            } else {
                errors << [error: false, message: "", value: ""]
                errors << [error: false, message: "", value: ""]
                errors << [error: false, message: "", value: ""]
            }

            [user: user, gitUser: gitHubService.getAuthenticatedUser(), error: errors, updated: updated]
        } else {
            session.setAttribute("redirect", createLink(controller: 'settings', action: 'index'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        }
    }

    def github() {
        def user = User.get(springSecurityService.principal.id)
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        if (gitToken != null) {

            gitHubService = new GitHubService(gitToken)

            user.gitLogin = gitHubService.getAuthenticatedUser().login
            user.save()

            [user: user, gitUser: gitHubService.getAuthenticatedUser(), repositories: gitHubService.getRepositories()]
        }
    }

    def changeSelectedRepo() {
        new GithubSyncController().sync()

        render("Completed syncing data from github.")
    }

    def ajaxChangePassword() {
        def user = User.get(springSecurityService.principal.id)

        def registrationCode = new RegistrationCode(username: user.username)
        registrationCode.save(flush: true)

        String url = generateLink('resetPassword', [t: registrationCode.token])

        def conf = SpringSecurityUtils.securityConfig
        def body = conf.ui.forgotPassword.emailBody
        if (body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }
        mailService.sendMail {
            to user.email
            from conf.ui.forgotPassword.emailFrom
            subject conf.ui.forgotPassword.emailSubject
            html body.toString()
        }

        render "<div class='alert alert-success'>An email has been sent.</div>"
    }

    protected String generateLink(String action, linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: 'register', action: action,
                params: linkParams)
    }

    protected String evaluate(s, binding) {
        new SimpleTemplateEngine().createTemplate(s).make(binding)
    }

    static final passwordValidator = { String password, command ->
        if (!checkPasswordMinLength(password) ||
                !checkPasswordMaxLength(password) ||
                !checkPasswordRegex(password)) {
            return 'command.password.error.strength'
        }
    }

    static boolean checkPasswordMinLength(String password) {
        def conf = SpringSecurityUtils.securityConfig

        int minLength = conf.ui.password.minLength instanceof Number ? conf.ui.password.minLength : 8

        password && password.length() >= minLength
    }

    static boolean checkPasswordMaxLength(String password) {
        def conf = SpringSecurityUtils.securityConfig

        int maxLength = conf.ui.password.maxLength instanceof Number ? conf.ui.password.maxLength : 64

        password && password.length() <= maxLength
    }

    static boolean checkPasswordRegex(String password) {
        def conf = SpringSecurityUtils.securityConfig

        String passValidationRegex = conf.ui.password.validationRegex ?:
            '^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'

        password && password.matches(passValidationRegex)
    }

    static final password2Validator = { value, command ->
        if (command.password != command.password2) {
            return 'command.password2.error.mismatch'
        }
    }
}