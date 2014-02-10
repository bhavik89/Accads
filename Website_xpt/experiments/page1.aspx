<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script>
    var data = new Array();
    data[0] = "one";
    data[1] = "two";
    data[2] = "three";
    data[3] = "four ";
</script>

<script>
    function sendData() {
        // Initialize 
        var dataArray = "";
        for (i = 0; (i < data.length); i++) {
            if (i > 0) {
                dataArray += ",";
            }
            dataArray += escape(data[i]);
        }

        url = "page2.aspx?" + dataArray;
        //window.location = "page2.aspx?" + dataArray;
        window.open(url, '_blank');
    }
</script>

<style type="text/css">
body
{
 max-width:980px;
 width:auto;
 margin-left:auto;
 margin-right:auto;
}    

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title></title>
    <style type="text/css">
        .style2
        {
            font-size: x-large;
            text-align: center;
        }
        .style3
        {
            font-size: medium;
        }
    .style4
    {
        font-size: x-large;
        text-align: center;
    }
    </style>
</head>
<body>
<p class="style4"><strong><em>Experiment: Passing data in query String with Javascript</em></strong></p>
    <p><strong><em><span class="style3">Experiment:</span></em></strong><span 
            class="style3"> This experiment demonstrates the passing of data variables betwwen 
        two pages using Javascript</span></p>
    <hr />
    <p class="style3"><strong><em>Documentation:</em></strong></p>
    <p class="style3">In this experiment an array in javascript is made and passed to another page 
        using query string. The query string is made in javascript consisting of the 
        data array. The array data is passed in url and on the destination page, the 
        data is extracted from the url.</p>
    <p class="style3">The main problem with the query string approach is that the amount of data you can pass is limited. The maximum total safe length of a URL is 2,083 characters, and "escaping" of data like whitespace and punctuation will consume quite a bit of this space. So this technique should be used only for small amounts of information.&nbsp;</p>
    <hr />
    <p class="style3"><strong><em>Experiment Demo:</em></strong></p>




    <form id="form1" runat="server">
    <div>
    <h1 class="style3">This is what the array contains:</h1>
<ul class="style3">
<script>
    for (i = 0; (i < data.length); i++) {
        document.write("<li>" + data[i] + "</li>\n");
    }
</script>
</ul>
<a href="javascript:sendData();"><span class="style3">Go to Page Two</span></a><span 
            class="style3"> </span>
    </div>
    </form>
    <p class="style3">
        <strong><em>Source:</em></strong></p>
    <p>
      <a href="../fileview/Default.aspx?~/experiments/page1.aspx" target="_blank">Page1</a></p>
    <p>
      <a href="../fileview/Default.aspx?~/experiments/page2.aspx" target="_blank">Page2</a></p>
</body>
</html>
