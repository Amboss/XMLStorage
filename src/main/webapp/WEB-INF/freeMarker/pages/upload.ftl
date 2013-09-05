<#-- ==============================================================
     UPLOAD page
     ============================================================== -->
<#import "/util/spring.ftl" as spring />

<#import "/layout/common.ftl" as com />

<#assign pageTitle="Upload"/>

<@com.page title="${pageTitle}">
    <div class="row-fluid">
        <h2>${pageTitle}</h2>
        <hr></br>
        <form method="POST" enctype="multipart/form-data" action="fup.cgi">
            <div class="control-group info">
                <label class="control-label" for="server_name">File to upload:</label>
                <div class="controls">
                    <#--@spring.formInput "", "input-xlarge" /-->
                    <#--@spring.showErrors " ", "alert alert-error"/-->
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input class="btn btn-primary"
                            type='submit'
                            name='create'
                            value='Upload'/>
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

