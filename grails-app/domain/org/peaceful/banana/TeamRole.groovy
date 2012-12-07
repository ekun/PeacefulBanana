package org.peaceful.banana

/**
 * Created with IntelliJ IDEA.
 * User: ekun
 * Date: 07.12.12
 * Time: 10:06
 */
public enum TeamRole {

    MANAGER('project_manager'),
    DEVELOPER('developer'),
    TESTER('tester')

    private String role

    public TeamRole(String role){
        this.role = role
    }
}