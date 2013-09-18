<#-- ==============================================================
        UPLOAD page
        ============================================================== -->
    <#import "/util/spring.ftl" as spring />

    <#import "/layout/common.ftl" as com />

    <#assign pageTitle="Upload XML file"/>

    <@com.page title="${pageTitle}">

    <script type="text/javascript">
        /* invoking fileInput onclick of false button */
        $(document).ready( function() {
        $('#false_btn').click(function(){
        $("#fileinput").click();
        });
        });

        /* illustrating selected file on change of input type=‘file’> */
        function CopyMe(oFileInput, sTargetID) {
        var arrTemp = oFileInput.value.split('\\');
        document.getElementById(sTargetID).value = arrTemp[arrTemp.length - 1];
        }
    </script>
    <div class="row-fluid" id="upload" style="min-height:250px">

        <h2>${pageTitle}</h2>

        <style>
            element.style { margin: 0 0 0 0; }
            ul, ol { margin: 0 0 0 0; padding: 0; }
            ul, li{ list-style-type:none; }
        </style>
        <form action="/XMLStorage/upload_file/save" method="POST" enctype="multipart/form-data">
            <hr style="width:70%;">
            </br>
            <div class="span4">
                <div class="control-group info">
                    <label class="control-label" for="txtFileName"><b>File to upload:</b></label>

                    <div class="controls">
                        <input id="fileinput"
                               type="file"
                               name='file'
                               style="display:none;"
                               onchange="CopyMe(this, 'txtFileName');"/>
                        <input id="txtFileName"
                               class="input-xlarge"
                               type="text"/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input class="btn btn-large btn-primary"
                               type='submit'
                               name='upload'
                               value='Upload'
                               style="width:120px"/>
                        <input class="btn btn-large"
                               id="false_btn"
                               value='Select file'
                               style="width:50px"/>
                    </div>
                </div>
            </div>
            <div class="span6">
                <@spring.bind "fileUploadForm.multipartFile" />
                <#if spring.status.error>
                    <#list spring.status.errorMessages as error>
                        <ul>
                            <li class="alert alert-error" style="width:230px;">${error?html}</li>
                        </ul>
                    </#list>
                </#if>
            </div>
        </form>
    </div>
</@com.page>

