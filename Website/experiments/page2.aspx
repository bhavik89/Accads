<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script>
    var query = window.location.search;
    // Skip the leading ?, which should always be there, 
    // but be careful anyway
    if (query.substring(0, 1) == '?') {
        query = query.substring(1);
    }
    var data = query.split(',');
    for (i = 0; (i < data.length); i++) {
        data[i] = unescape(data[i]);
    }
</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>page 2</title>

    <style type="text/css">
body
{
 max-width:980px;
 width:auto;
 margin-left:auto;
 margin-right:auto;
}    
</style>
</head>

<body>
    <form id="form1" runat="server">
    <div>
   <h1>This is what the array contains:</h1>
<ul>
<script>
    for (i = 0; (i < data.length); i++) {
        document.write("<li>" + data[i] + "</li>\n");
    }
</script>
</ul>
    </div>
    <div>
    <p>
        <strong><em>Source:</em></strong></p>
    <p>
      <a href="../fileview/Default.aspx?~/experiments/page1.aspx" target="_blank">Page1</a></p>
    <p>
      <a href="../fileview/Default.aspx?~/experiments/page2.aspx" target="_blank">Page2</a></p>
</div>
    </form>
</body>
</html>
