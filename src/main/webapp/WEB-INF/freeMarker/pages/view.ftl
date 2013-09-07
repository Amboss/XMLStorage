<#-- ==============================================================
     VIEW page
     ============================================================== -->
<#import "/util/spring.ftl" as spring />

<#import "/layout/common.ftl" as com />

<#assign pageTitle>View</#assign>

<@com.page title="${pageTitle}">
    <div class="row-fluid">
        <h2>${pageTitle}</h2>
        <hr></br>

        <table cellpadding="0" cellspacing="0" border="0"
                class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Artist</th>
                    <th>Country</th>
                    <th>Company</th>
                    <th>Price</th>
                    <th>Year</th>
                </tr>
            </thead>
            <tbody>
                <#if modelList?has_content>
                    <#list modelList as list>
                        <tr>
                            <td>${list.title}</td>
                            <td>${list.artist}</td>
                            <td>${list.country}</td>
                            <td>${list.company}</td>
                            <td>${list.price}</td>
                            <td>${list.year}</td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td>- - -</td>
                        <td>- - -</td>
                        <td>- - -</td>
                        <td>- - -</td>
                        <td>- - -</td>
                        <td>- - -</td>
                    </tr>
                </#if>
            </tbody>
        </table>
    </div>
</@com.page>