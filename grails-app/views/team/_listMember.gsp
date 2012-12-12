<g:each in="${users}">
    <tr>
        <td>
            ${it.id}
        </td>
        <td>
            ${it.toString()}
        </td>
        <td>
            ${it.teamRole()}
        </td>
        <td>
            <div class="pull-right">
                asdf
            </div>
        </td>
    </tr>
</g:each>