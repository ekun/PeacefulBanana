// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = "org.peaceful.banana" // change this to alter the default package name and Maven publishing destination
grails.app.context = "/"
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Spring security core
grails.plugins.springsecurity.password.algorithm='SHA-512'

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password', 'oldPassword', 'password2', 'confirmPassword']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.dbconsole.urlRoot = '/admin/dbconsole'
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://vm-6121.idi.ntnu.no:8080"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '[%d{HH:mm:ss.SSS}] %-5p %c{2}: %m%n')
    }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    debug   'grails.app.org.peaceful.banana'

    warn 'grails.app.services.grails.plugins.springsecurity.ui.SpringSecurityUiService'
}


grails.plugins.springsecurity.securityConfigType = "InterceptUrlMap"

grails.plugins.springsecurity.interceptUrlMap = [
        '/secure/**':           ['ROLE_ADMIN'],
        '/admin/**':            ['ROLE_ADMIN'],
        '/repositories/**':     ['ROLE_ADMIN', 'ROLE_USER'],
        '/notification/**':     ['ROLE_ADMIN', 'ROLE_USER'],
        '/reflection/**':       ['ROLE_ADMIN', 'ROLE_USER'],
        '/settings/**':         ['ROLE_ADMIN', 'ROLE_USER'],
        '/about/*':             ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**':                  ['IS_AUTHENTICATED_ANONYMOUSLY']
]

environments {
    development {
        oauth {
            providers {
                github {
                    api = org.peaceful.banana.api.GitHubApi
                    scope = 'user,repo'

                   // For testing localy
                    key = '8ee7aa535906d0157db2'
                    secret = 'b09ebba58897895fa81c8879d5269d54cee6efaa'
                    callback = "http://localhost:8080/oauth/github/callback"
                    successUri = "http://localhost:8080/githubResponse"
                }
            }
            debug = true
        }
    }
    production {
        oauth {
            providers {
                github {
                    api = org.peaceful.banana.api.GitHubApi
                    scope = 'user,repo'

                    // For deploy
                    key = '7261cb7dcc394d1addb0'
                    secret = '08c76b315f8bde88e2abe84ea63b2c6b337dc9a9'
                    callback = "http://vm-6121.idi.ntnu.no:8080/oauth/github/callback"
                    successUri = "http://vm-6121.idi.ntnu.no:8080/githubResponse"
                }
            }
        }
    }
}


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = "org.peaceful.banana.User"
grails.plugins.springsecurity.userLookup.authorityJoinClassName = "org.peaceful.banana.UserRole"
grails.plugins.springsecurity.authority.className = "org.peaceful.banana.Role"

// Registration
grails.plugins.springsecurity.ui.encodePassword = false
grails.plugins.springsecurity.ui.register.postRegisterUrl = '/team'
grails.plugins.springsecurity.ui.register.emailFrom = 'fredfullbanan@gmail.com'
grails.plugins.springsecurity.ui.register.emailSubject = 'Peaceful Banana - Registration'
grails.plugins.springsecurity.ui.register.defaultRoleNames = ['ROLE_USER']
grails.plugins.springsecurity.ui.password.minLength = 6

// Added by the Spring Security OAuth plugin:
grails.plugins.springsecurity.oauth.domainClass = 'org.peaceful.banana.OAuthID'

grails.plugins.twitterbootstrap.fixtaglib = true

tomcat.deploy.username="admin"
tomcat.deploy.password="Teech6ha"
tomcat.deploy.url="http://vm-6121.idi.ntnu.no:8080/manager/html"

// Mail configuration
grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "fredfullbanan@gmail.com"
        password = "Peaceful123"
        props = ["mail.smtp.auth":"true",
                "mail.smtp.socketFactory.port":"465",
                "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                "mail.smtp.socketFactory.fallback":"false"]
    }
}