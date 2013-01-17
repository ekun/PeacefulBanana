<g:each in="${users}">
    <tr ${!it.active ? 'class="error"' : ''}>
        <td>
            ${it.user.id}
        </td>
        <td>
            ${it.user.toString()}
        </td>
        <td>
            ${it.role}
        </td>
        <td>
            <div class="pull-right">
                asdf
            </div>
        </td>
    </tr>
</g:each>