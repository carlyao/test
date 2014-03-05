<html>
<head>
<title>Barbarian - Value Object</title>
<style type="text/css">
body {
    font-family: Calibri, arial, sans-serif;
    font-size: 14px;
}
table.vo {
    border-collapse: collapse;
    border-top: 1px solid #BBBBBB;
    border-right: 1px solid #BBBBBB;
    margin-top: 5px;
    margin-bottom: 10px;
}
table.vo th,td {
    border-left: 1px solid #BBBBBB;
    border-bottom: 1px solid #BBBBBB;
    padding: 3px;
}
table.vo td.vo-name {
    font-weight: bold;
    background: none repeat scroll 0 0 #EEEEEE;
    color: #0000CC;
}
table.vo td.ctp {
    text-align: right;
}
table.vo a:visited {
    color: blue;
}
.sel-mav {
    margin-bottom: 10px;
}
</style>
</head>
<body>
    <div class="sel-mav">
        <select onchange="javascript:location.href='#'+this.options[this.selectedIndex].value;">
            <#list valueObjects as valueObject>
            <option value="${valueObject.name}">${valueObject.name}</option>
            </#list>
        </select>
    </div>
    
    <#list valueObjects as valueObject>
    <table class="vo" border="0" id="${valueObject.name}">
        <tr>
            <td class="vo-name" colspan="2">(${valueObject_index+1}) ${valueObject.name}</td>
        </tr>
        <tr align="center">
            <th>Type</th>
            <th>Name</th>
        </tr>
        <#list valueObject.properties as prop>
        <tr>
            <td class="ctp"><#if builderUtil.isVo(prop.typeClass)><a href="#${prop.type}">${prop.type}</a><#else>${prop.type}</#if><#if prop.genericType != "">&lt;<a href="#${prop.genericType}">${prop.genericType}</a>&gt;</#if></td>
            <td>${prop.name}</td>
        </tr>
        </#list>
    </table>
    
    </#list>
</body>
</html>