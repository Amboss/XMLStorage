<#-- ==============================================================
        DOWNLOAD page
        ============================================================== -->
    <#import "/util/spring.ftl" as spring />

    <#import "/layout/common.ftl" as com />

    <#assign pageTitle>Download stored XML file</#assign>
    <@com.page title="${pageTitle}">

    <div class="row-fluid" id="download" style="min-height:250px">
        <div class="span12">
            <h2>${pageTitle}</h2>

            <form method="POST" enctype="multipart/form-data">

                <hr style="width:70%;">
                </br>
                <h3>Press to download file</h3>
                <div class="span4">
                    <div class="control-group">
                        <div class="controls">
                            <input class="btn btn-large btn-primary"
                                   type='submit'
                                   name='download'
                                   value='Download'/>
                            <input class="btn btn-large"
                                   type='submit'
                                   name='cancel'
                                   value='Cancel'/>
                        </div>
                    </div>
                </div>
                <div class="span6">
                    <span class="text-success"><b>Size of target file is: ${fileinfo} Kb</b></span>
                </div>
            </form>
        </div>
    </div>

</@com.page>