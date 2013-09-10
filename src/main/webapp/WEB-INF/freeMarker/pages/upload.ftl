<#-- ==============================================================
        UPLOAD page
        ============================================================== -->
    <#import "/util/spring.ftl" as spring />

    <#import "/layout/common.ftl" as com />

    <#--assign spring=JspTaglibs["/WEB-INF/tlds/spring.tld"] /-->

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

    <div class="span6">
        <h2>${pageTitle}</h2>
        <hr>
        </br>
        <form modelAttribute="uploadedItem" method="POST" enctype="multipart/form-data">

            <div class="control-group info">
                <@spring.bind "uploadedItem.multipartFile" />
                <#if spring.status.error>
                    <div class="alert alert-error" >
                        <#list spring.status.errorMessages as error>
                            ${error?html}
                        </#list>
                    </div>
                </#if>
                <label class="control-label" for="multipartFile">File to upload:</label>
                <div class="controls">
                    <input id="fileinput" type="file" name='uploadedItem.multipartFile'
                           style="display:none;" onchange="CopyMe(this, 'txtFileName');"/>
                    <input id="txtFileName" class="input-xlarge" type="text" />
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input class="btn" id="false_btn" value='Select file'
                           style="width:185px;"/>
                    <input class="btn btn-primary" type='submit' name='upload' value='Upload' />
                </div>
            </div>
        </form>
        </br>
        <hr>
    </div>
</@com.page>

