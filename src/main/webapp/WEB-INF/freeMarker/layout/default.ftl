<#-- =====================================================================
     DEFAULT layout
        - for authentication and error pages
        - with access IS_AUTHENTICATED_ANONYMOUSLY
     ===================================================================== -->
<#macro page title>
<#include "../common/header.ftl"/>
     <div class="container">
         <#nested/>
     </div>
<#include "../common/footer.ftl"/>
</#macro>