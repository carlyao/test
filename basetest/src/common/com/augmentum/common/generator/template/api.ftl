<html>
<head>
<title>Barbarian - Server API</title>
<style type="text/css">
body {
	font-family: Calibri, arial, sans-serif;
	font-size: 14px;
}
body {
    font-family: Calibri, arial, sans-serif;
    font-size: 14px;
}
table.api {
    border-collapse: collapse;
    border-top: 1px solid #BBBBBB;
    border-right: 1px solid #BBBBBB;
    float: left;
    margin-right: 15px;
    margin-bottom: 15px;
}
table.api th,td {
    border-left: 1px solid #BBBBBB;
    border-bottom: 1px solid #BBBBBB;
    padding: 3px;
}
table.api th {
    
}
table.api td.api-info {
    font-weight: bold;
    background: none repeat scroll 0 0 #EEEEEE;
    color: #0000CC;
}
table.api td.ctp {
    text-align: right;
}
table.api a:visited {
    color: blue;
}
.api-group {
    border: 1px solid #BBBBBB; 
    padding-left: 8px; 
    margin-bottom: 10px; 
    overflow-x: auto;
}
.api-group-name {
    margin-bottom: 10px;
    font-weight: bold;
    font-style: italic;
    font-size: 24px;
}
</style>
</head>
<body>
    <#assign count=0>
    <#list apiObjectGroup?keys as groupName>
    <div class="api-group">
    <div class="api-group-name">${groupName}</div>

    <#list apiObjectGroup[groupName] as apiObject>
    <#assign count=count+1>
	<table class="api">
		<tr>
			<td class="api-info" colspan="2">(${count}) ${apiObject.name}</td>
		</tr>
		<tr>
			<td class="ctp">Request</td>
			<td>${apiObject.method} ${apiObject.requestUrl}</td>
		</tr>
		<tr>
		    <td class="ctp">Parameter</td>
            <td>
            <#if apiObject.parameters?size==0>N/A</#if>
            <#list apiObject.parameters as parameter>
            <#if (parameter_index>0)><br/></#if>
            <#if builderUtil.isVo(parameter.typeClass)><a href="vo.html#${parameter.type}">${parameter.type}</a>
            <#else>${parameter.type}</#if> ${parameter.name}
            </#list>
            </td>
        </tr>
        <tr>
            <td class="ctp">Response</td>
            <td><#if builderUtil.isVo(apiObject.returnObjectClass)><a href="vo.html#${apiObject.returnObjectClass.getSimpleName()}">${apiObject.returnObjectClass.getSimpleName()?html}</a>
            <#elseif builderUtil.isVo(apiObject.returnObjectClass2)><a href="vo.html#${apiObject.returnObjectClass2.getSimpleName()}">${apiObject.returnObjectClass.getSimpleName()}&lt;${apiObject.returnObjectClass2.getSimpleName()}&gt;</a>
            <#else>${apiObject.returnObjectClass.getSimpleName()?html}</#if></td>
        </tr>
	</table>

    <#if (apiObject_index>0) && (apiObject_index+1)%4==0>
    <div style="clear: left;"></div>
    </#if>
	</#list>
	
	</div>
	</#list>
</body>
</html>