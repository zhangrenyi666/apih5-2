
<div class="page-content no-padding-bottom">
<div class="row">
	<div class="col-xs-12"  >
        <#assign name=form.formId+"_"+fields["姓名"].fieldId>
        <#assign auth=fields["姓名"].author>
        <#assign val=datas[fields['姓名'].dataKey]>
		<div class="form-group <#if auth=="check">has-error</#if>">
			<label class="col-xs-12 col-sm-3 control-label">姓名:</label>
			<div class="col-xs-12 col-sm-5 ">
                <#if (auth=="read")>
                    <label class="form-control form-read-lable">${val}</label>
                    <input type="hidden" name="${name}" value=">${val}"/>
                <#elseif  (auth=="hidden")>
                    <input type="hidden" name="${name}" value="${val}"/>
                <#else>
                    <input class="form-control" <#if (auth=="check")>required</#if> type="text"  name="${name}"  value="${val}"/>
                </#if>
			</div>
		</div>
	</div>
</div>
</div>
<input type="hidden" name="FORMID" value="${form.formId}"/>
<input type="hidden" name="${form.formId}_TABLENAME" value="${fields[form.formId].tableId}"/>
<input type="hidden" name="${form.formId}_DATAID" value="${form.formDataId}"/>
<input type="hidden" name="${form.formId}_ID" value="${form.isNewData}"/>
