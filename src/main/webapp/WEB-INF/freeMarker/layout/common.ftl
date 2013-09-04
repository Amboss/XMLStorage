<#-- =====================================================================
     COMMON layout
     ===================================================================== -->
<#macro page title>
    <#include "../common/header.ftl"/>
         <div class="container">
             <#nested/>
         </div>
    <#include "../common/footer.ftl"/>
</#macro>