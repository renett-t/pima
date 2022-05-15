<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="isAuthenticated" type="java.lang.Boolean" -->
<#import "spring.ftl" as spring />

<#macro contents>
    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="<@spring.url'/main'/>"><@spring.message 'page.header.brand'/></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="<@spring.url'/'/>"><@spring.message 'page.header.main'/></a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <@spring.message 'page.header.articles'/>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item"
                                       href="<@spring.url'/articles'/>"><@spring.message 'page.header.dropdown.all'/></a>
                                </li>
                                <li><a class="dropdown-item"
                                       href="<@spring.url'/articles?tag=guitar'/>"><@spring.message 'page.header.dropdown.guitar'/></a>
                                </li>
                                <li><a class="dropdown-item"
                                       href="<@spring.url'/articles?tag=music-theory'/>"><@spring.message 'page.header.dropdown.theory'/></a>
                                </li>
                                <li><a class="dropdown-item"
                                       href="<@spring.url'/articles?tag=songs'/>"><@spring.message 'page.header.dropdown.tutorials'/></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="nav-profile-wrapper">
                <#if isAuthenticated?? && isAuthenticated == true>
                    <a href="<@spring.url'/profile'/>" target="_blank">
                        <img class="nav-profile-icon" src="<@spring.url'/assets/icons/profile.png'/>"
                             alt="profile icon">
                    </a>
                <#else>
                    <a href="<@spring.url'/signIn'/>" target="_blank">
                        <img class="nav-profile-icon" src="<@spring.url'/assets/icons/profile.png'/>"
                             alt="profile icon">
                    </a>
                </#if>
            </div>
        </nav>
    </header>
</#macro>
