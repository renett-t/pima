<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />

<#macro contents>
    <footer>
        <p class="float-end"></p>
        <p> <@spring.message 'page.footer.copyright'/> <a target="_blank" href="<@spring.url'#'/>"><@spring.message 'page.footer.privacy'/></a> &middot; <a target="_blank" href="<@spring.url'#'/>"><@spring.message 'page.footer.terms'/></a></p>
    </footer>
</#macro>
