<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro contents>
    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="<@spring.url'/main'/>">pIMa</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="<@spring.url'/'/>">Главная</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Статьи
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="<@spring.url'/articles'/>">Все статьи</a></li>
                                <li><a class="dropdown-item" href="<@spring.url'/articles?tag=guitar'/>">Гитара</a></li>
                                <li><a class="dropdown-item" href="<@spring.url'/articles?tag=music-theory'/>">Теория музыки</a></li>
                                <li><a class="dropdown-item" href="<@spring.url'/articles?tag=songs'/>">Разборы песен</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="nav-profile-wrapper">
                <@security.authorize access="isAuthenticated()">
                    <a href="<@spring.url'/profile'/>">
                        <@security.authentication property="principal.username" />
                        <img class="nav-profile-icon" src="<@spring.url'/resources/icons/profile.png'/>" alt="profile icon">
                    </a>
                </@security.authorize>
                <@security.authorize access="! isAuthenticated()">
                    <a href="<@spring.url'/signIn'/>">
                        <img class="nav-profile-icon" src="<@spring.url'/resources/icons/profile.png'/>" alt="profile icon">
                    </a>
                </@security.authorize>
            </div>
        </nav>
    </header>
</#macro>
