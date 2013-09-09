<#-- ==============================================================
        UPLOAD page
        ============================================================== -->
    <#import "/util/spring.ftl" as spring />

    <#import "/layout/common.ftl" as com />

    <#assign pageTitle="Upload"/>

<@com.page title="${pageTitle}">

    <script type="text/javascript">

        /* invoking fileInput onclick of false button */
        $(document).ready( function() {
            $('#false').click(function(){
                $("#fileinput").click();
            });
        });

        /* illustrating selected file on change of input type=‘file’> */
        function CopyMe(oFileInput, sTargetID) {
            var arrTemp = oFileInput.value.split('\\');
            document.getElementById(sTargetID).value = arrTemp[arrTemp.length - 1];
        }
    </script>

    <div class="row-fluid">
        <h2>${pageTitle}</h2>
        <hr>
        </br>
        <form modelAttribute="uploadedItem" method="POST" enctype="multipart/form-data">
            <#--if sessionScope.message?has_content-->
                <#--div class="alert alert-error"-->
                    <#--p>${sessionScope.message}</p-->
                <#--/div-->
            <#--/#if-->
            <div class="control-group info">
                <label class="control-label" for="multipartFile">File to upload:</label>
                <div class="controls">
                    <input id="fileinput" type="file" name='uploadedItem.multipartFile'
                           style="display:none;" onchange="CopyMe(this, 'txtFileName');"/>
                    <input id="txtFileName" class="input-large" type="text" />
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input class="btn" id="false" value='Select file'/>
                    <input class="btn btn-primary" type='submit' name='upload' value='Upload'/>
                </div>
            </div>
        </form>
        </br>
        <hr>
    </div>
</@com.page>

