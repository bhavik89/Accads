﻿<%@ Page Language="C#" EnableEventValidation="false" %>

<%@ Import Namespace="edu.neu.ccis.rasala" %>
<%@ Import Namespace="System.Collections.Generic" %>

<script runat="server">

    public const bool onlyPublic = true;


    void Page_Load(object sender, EventArgs e)
    {
        InitRootData();
        InitRootList();
    }


    void InitRootData()
    {
        RootPath.Text = FileTools.GetRoot(this);
    }


    void InitRootList()
    {
        string rootpath = RootPath.Text;

        List<string> DirectoryList =
            SourceTools.MakeDirectoryList(rootpath, onlyPublic);

        List<string> tildeRootList =
            FileTools.GetTildePaths(rootpath, DirectoryList);

        RootList.DataSource = tildeRootList;
        RootList.DataBind();
    }


</script>


<!DOCTYPE html>

<html lang='en'>

<head>
<meta charset='utf-8' />
<meta name="viewport" content="initial-scale=1, minimum-scale=1">
<title>File List</title>

<link rel="stylesheet" type="text/css" href="source/source.css" />
<link rel="stylesheet" type="text/css" href="source/print.css" media="print" />
<link rel="stylesheet" type="text/css" href="fileliststyle.css" />

<script type="text/javascript"
    src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<script type="text/javascript" src="javascript/print.js"></script>

<script type="text/javascript">

    // This function is solely for testing the acquisition of input
    // from the dropdown, text fields, and radio buttons.  It isn't
    // production code.
    function testinput() {
        var root = $(".rootlist>option:selected").val();
        var start = $(".startdate").val();
        var end = $(".enddate").val();
        var sort = $("input[name='sortoptions']:checked").val();

        var s = "<p class='test'>" + "root: " + root + "</p>\n";

        s += "<p class='test'>" + "start: " + start + "</p>\n";

        s += "<p class='test'>" + "end: " + end + "</p>\n";

        s += "<p class='test'>" + "sort: " + sort + "</p>\n";

        $("#content").html(s);
    }


    var url = "";


    // The function to fetch files based on the parameters
    // root, start, end, sort
    function fetchfiles() {
        var root = $(".rootlist>option:selected").val();
        var start = $(".startdate").val();
        var end = $(".enddate").val();
        var sort = $("input[name='sortoptions']:checked").val();

        url = "files.aspx?root=" + root + "&start=" + start
            + "&end=" + end + "&sort=" + sort + " .results";

        $("#content").load(url);
    }


    function errorhandler() {
        var s = "<p> Error with <code>" + url + "</code></p>";
        $("#content").html(s);
    }


    function initialize() {
        $.ajaxSetup({
            timeout: 60000,
            error: errorhandler
        });

        $(".button").click(fetchfiles);
    }
    

    $(initialize);
</script>

</head>

<body>

<p class="large">File List Server</p>

<p class="large"><a href="Default.aspx" target="_blank">Home</a></p>


<form id="form1" runat="server" enableviewstate="false">
<div>

<p class="normal">Root Directory for the File Selection</p>

<p>
<asp:DropDownList ID="RootList"
    Runat="server"
    CssClass="dropdown rootlist" >
</asp:DropDownList>
</p>
    
<asp:Label
    ID="RootPath"
    runat="server"
    Visible="False" />
    
</div>
</form>


<div>

<p class="monospace">
Date Time Format: yyyy-mm-dd_hh-mm-ss_xxx or a substring or 'now'
<br />
If no input is provided, defaults to the first moment of the current year.
</p>

<p class="normal">
Start Date Time
</p>

<p>
<input type="text" class="textinput startdate" />
</p>

<p class="normal">
End Date Time
</p>

<p>
<input type="text" class="textinput enddate" value="now" />
</p>

<p class="normal">Sort Options</p>

<ul class="plain">
  <li><input type="radio"" name="sortoptions" value="alpha" checked="checked" /> Alphabetic</li>
  <li><input type="radio"" name="sortoptions" value="date" /> By Date</li>
  <li><input type="radio"" name="sortoptions" value="size" /> By Size</li>
</ul>

<p>
<input type="button" class="button" value="Fetch Files" />
</p>


<hr />

<div id="content" class="content"></div>


</div>

</body>
</html>
