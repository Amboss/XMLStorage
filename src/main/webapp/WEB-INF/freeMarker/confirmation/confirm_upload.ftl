<#-- ==============================================================
     UPLOAD confirmation alert
     ============================================================== -->

 <#import "/util/spring.ftl" as spring />

 <#import "/layout/common.ftl" as com />

 <#assign pageTitle=confirm.label />
 <#assign messageTitle=confirm.message />

 <@com.page title="${pageTitle}">
     <div class="modal" id="employee_removal" tabindex="-1" role="dialog"
              aria-labelledby="myModalLabel" aria-hidden="true" >
         <form class="modal-body" method="post">
             <center>
                 <h3>${messageTitle}!</h3>
                 <hr>
                 <input class="btn"
                         data-dismiss='modal'
                         type='submit'
                         aria-hidden='true'
                         name='ok'
                         value='Ok' />
             </center>
          </form>
     </div>
 </@com.page>