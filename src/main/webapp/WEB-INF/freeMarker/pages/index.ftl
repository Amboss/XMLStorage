<#-- ==============================================================
        INDEX page
        ============================================================== -->

    <#import "/util/spring.ftl" as spring />

    <#import "/layout/common.ftl" as com />

    <#assign pageTitle>Home</#assign>

<@com.page title="${pageTitle}">
    <div class="row-fluid">
        <h1>Portfolio task</h1>

        <h3>to qualify for the position of junior java developer </h3>
    </div>
    <hr>
    <div class="row-fluid"  id="light_grey">
        <h2>Main technologies used at production:</h2>

        <div class="span5">
            <h3>Back-end</h3>
            <ul>
                <li>Tomcat 7</li>
                <li>JDK 1.7</li>
                <li>Spring(Core, MVC)</li>
                <li>STax parser</li>
                <li>DOM parser</li>
            </ul>
        </div>
        <div class="span5">
            <h3>Front-end</h3>
            <ul>
                <li>FreeMarker</li>
                <li>HTML4/CSS3</li>
                <li>Bootstrap</li>
                <li>JQuery</li>
                <li>JQuery dataTable plugin</li>
            </ul>
        </div>
    </div>
    <!--/span 12-->
</@com.page>