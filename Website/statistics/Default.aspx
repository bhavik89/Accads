<%@ Page Language="C#"
    AutoEventWireup="true" %>

<%@ Import Namespace="edu.neu.ccis.rasala" %>
<%@ Import Namespace="System.Collections.Generic" %>

<script runat="server">
    
    public const bool onlyPublic = true;

    List<string> directoryList;
    List<string> tildeDirectoryList;

    
    void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            SourceTools.LoadCSSandJavascript(this);

            InitRootData();
            InitDirectoryList();

            CheckQueryStringForDirectory();
        }
    }


    void InitRootData()
    {
        RootPath.Text = FileTools.GetRoot(this);
        RootOffset.Text = FileTools.GetRootOffset(this);
    }


    void InitDirectoryList()
    {
        string rootpath = RootPath.Text;
        
        directoryList  =
            SourceTools.MakeDirectoryList(rootpath, onlyPublic);

        tildeDirectoryList =
            FileTools.GetTildePaths(rootpath, directoryList);

        tildeDirectoryList.Insert(0, "");
        DirectoryList.DataSource = tildeDirectoryList;
        DirectoryList.DataBind();
    }


    void CheckQueryStringForDirectory()
    {
        string[] parts = RequestTools.QueryParts(Request);

        int n = parts.Length;

        if (n == 0)
            return;

        string directory = parts[0];

        if (directory.Length == 0)
            return;

        int index = 0;

        int length = tildeDirectoryList.Count;

        for (int i = 1; i < length; i++)
        {
            if (tildeDirectoryList[i] == directory)
            {
                index = i;
                break;
            }
        }

        if (index > 0)
        {
            DirectoryList.SelectedIndex = index;
            ShowStatistics();
        }
    }

    
    string GetSelectedDirectory()
    {
        if (DirectoryList.Items.Count == 0)
            return "";
        else
            return DirectoryList.SelectedValue;
    }


    public void DirectoryList_SelectedIndexChanged(object sender, EventArgs e)
    {
        ShowStatistics();
    }


    public void FileListChoice_Changed(object sender, EventArgs e)
    {
        ShowStatistics();
    }


    void ShowStatistics()
    {
        string tildeDirectoryPath = GetSelectedDirectory();

        if (tildeDirectoryPath == "") {
            Statistics.Text = "";
            return;
        }
        
        bool viewable = FileListChoice.SelectedValue == "Viewable";

        bool showfiles = true;
        bool summarize = true;
        
        // These out parameters are not used here
        int fileCount;
        long totalBytes;
        DateTime? createdFirst;
        DateTime? createdLast;
        DateTime? modifiedFirst;
        DateTime? modifiedLast;

        Statistics.Text =
            SourceTools.StatisticsMarkupForDirectory
                (this,
                    tildeDirectoryPath,
                    onlyPublic,
                    viewable,
                    showfiles,
                    summarize,
                    out fileCount,
                    out totalBytes,
                    out createdFirst,
                    out createdLast,
                    out modifiedFirst,
                    out modifiedLast);
    }

</script>


<!DOCTYPE html>

<html lang='en'>

<head id="head1" runat="server">
    <meta charset="utf-8" />
    <title>Statistics Server</title>
</head>

<body>

    <form id="form1" runat="server">

    <h1>Statistics Server</h1>
    
    <h4><asp:HyperLink
            ID="HomeLink"
            NavigateUrl="~/Default.aspx"
            Target="_blank"
            runat="server">Home</asp:HyperLink>
            
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        
        Open New
        <asp:HyperLink
            ID="OpenNewLink"
            NavigateUrl="Default.aspx"
            Target="_blank"
            runat="server">Statistics Server</asp:HyperLink>
    </h4>
    
    <asp:ScriptManager ID="ScriptManagerForSource" runat="server">
    </asp:ScriptManager>
    
    <asp:UpdatePanel ID="UpdatePanelForDirectoryList" runat="server">
    <ContentTemplate>
    
    <p class="fs120">Directory List for the Web Site:</p>
    
    <asp:DropDownList ID="DirectoryList"
        Runat="server"
        AutoPostBack="True"
        Width="700px"
        CssClass="fs115"
        OnSelectedIndexChanged="DirectoryList_SelectedIndexChanged">
    </asp:DropDownList>
    
    <br />
    <br />
    
    <hr />
    
    <p class="fs120">File List Choices:</p>
    
    <asp:RadioButtonList ID="FileListChoice" runat="server"
        OnSelectedIndexChanged="FileListChoice_Changed"
        AutoPostBack="True" >
    
        <asp:ListItem Value="Viewable">
            <span class="fs120">List Viewable Files</span>
        </asp:ListItem>
            
        <asp:ListItem Selected="True" Value="All" >
            <span class="fs120">List All Files</span>
        </asp:ListItem>
    
    </asp:RadioButtonList>
    
    <br />
    
    <hr />
    
    <asp:Label ID="Statistics" runat="server" />
    
    <br />
    
    <asp:Label ID="Debug" runat="server" />
    
    <asp:Label
        ID="RootPath"
        runat="server"
        Visible="False" />
    
    <asp:Label
        ID="RootOffset"
        runat="server"
        Visible="False" />
    
    </ContentTemplate>
    </asp:UpdatePanel>
    <!-- End UpdatePanelForDirectoryList -->
    
    </form>
    
</body>

</html>
