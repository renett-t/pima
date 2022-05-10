<#ftl encoding="UTF-8"/>
<#-- Site Layout Macro -->
<#import "spring.ftl" as spring />
<#import "header.ftlh" as header/>
<#import "footer.ftlh" as footer/>
<#macro layout title>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes, height=device-height">
        <title>PIMA - ${title}</title>
        <link rel="stylesheet" href="<@spring.url'/css/main.css'/>">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body>
    <@header.contents/>

    <div class="content-container">
        <#nested>
    </div>

    <@footer.contents/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>