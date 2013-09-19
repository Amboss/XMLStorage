<#-- ==============================================================
        INDEX page
        ============================================================== -->

    <#import "/util/spring.ftl" as spring />

    <#import "/layout/common.ftl" as com />

    <#assign pageTitle>Home</#assign>

    <@com.page title="${pageTitle}">
    <div class="row-fluid">
        <h1>Test task</h1>

        <h3>to qualify for the position of junior java developer </h3>
    </div>
    <hr>
    <div class="row-fluid" id="light_grey">
        <div class="span12" >
            <h2>1. Web UI interface </h2>

            <p>Cтраница для аплоада данных (XML файл) в программу</br>
                Cтраница для отображения данных (таблица данных должен быть разбит по страницам)</br>
                Cтраница с ссылкой для доунлоада объединенного XML файла (см. ниже).</p>

            <h2>2. Серверная сторона</h2>

            <p>Отображать данные из существующего XML файла</br>
                Принимать новые данные и объединять их с уже существующими</br>
                Прим.1: на сервере должен быть только один объединеный файл</br>
                Прим.2: ключевое поле TITLE</br>
                Прим.3: записи с существующим ключевым словом должны полностью обновляться новыми данными.</p>

            <h2>3. Технологии</h2>

            <p>Обязательно использование Spring MVC или Struts последних версий;</P>
            <ul>
                <li>Tomcat 5.5</li>
                <li>JDK 1.5.х</li>
                <li>HTML/CSS</li>
            </ul>
            <hr>
        </div>
        <!--/span 12-->
    </div>
    <!--/row-->
    <div class="row-fluid">
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