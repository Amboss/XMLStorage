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

<h2>Main technologies used at production:</h2>

<h3>Back-end</h3>
<ul>
    <li>JDK 1.7</li>
    <li>Spring(Core, MVC)</li>
    <li>STax parser</li>
    <li>DOM parser</li>
</ul>

<h3>Front-end</h3>
<ul>
    <li>FreeMarker</li>
    <li>HTML4/CSS3</li>
    <li>Bootstrap</li>
    <li>JQuery</li>
    <li>JQuery dataTable plugin</li>
</ul>

<h2>Project URL: /XMLStorage/index.html</h2>

<span>As it was mentioned in the above task project is designated to hold XML file in place of Data Base. Back-end
functionality of application, represented by the "XMLStorage.logic" catalog, contain CDModel service and upload validator.
</br>
The main to methods, that handel's application functionality, are supporting parsing from XML - for a view page and
  making search of similarities and storage of new uploaded info.</span>

<span>The Front-end is represented by four pages(index, upload, download and view)</span>
