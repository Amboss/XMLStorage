<#-- ==============================================================
     DOWNLOAD page
     ============================================================== -->
 <#import "/util/spring.ftl" as spring />

 <#import "/layout/common.ftl" as com />

 <#assign pageTitle>Download stored XML file</#assign>
 <@com.page title="${pageTitle}">

     <div class="span6">
         <h2>${pageTitle}</h2>
         <hr></br>
         <h2>Press to download file</h2>
         <div class="alert alert-info">
            <span>Size of target file is: ${fileinfo} Kb</span></br>
         </div>
         <form method="POST" enctype="multipart/form-data">

             <div class="control-group">
                 <div class="controls">
                     <input class="btn btn-primary"
                             type='submit'
                             name='download'
                             value='Download'/>
                     <input class="btn"
                             type='submit'
                             name='cancel'
                             value='Cancel'/>
                 </div>
             </div>
         </form>
         </br><hr>
     </div>
 </@com.page>