<%--<%@ Page Language="C#" AutoEventWireup="true" Inherits="experiments_SQLexpt" %>--%>

<%@ Page Language="C#" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script runat="server">

        
    protected void Button1_Click(object sender, EventArgs e)
    {
        
        data.Visible = true;
        data.Text = "Thank You for giving your Data";
        SQLData.Insert();
        name.Text = "";
        age.Text = "";
        city.Text = "";
        GridViewData.DataBind();
           
    }

    protected void Button2_Click(object sender, EventArgs e)
    {
        SQLData.Delete();
    }
    </script>

    

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>SQLExpt</title>
    <style type="text/css">
        
        body
        {
            max-width:990px;
            margin-left:auto;
            margin-right:auto;
            }
        
        
        .style1
        {
            width: 46%;
        }
        .style2
        {
            width: 41px;
            text-align: left;
        }
        .style3
        {
            width: 344px;
        }
        .style4
        {
            text-align: left;
        }
        .style5
        {
            text-align: center;
            font-size: x-large;
        }
        .style6
        {
            font-size: medium;
        }
        .style7
        {
            text-align: left;
            font-size: medium;
        }
        .style8
        {
            text-align: justify;
        }
        .style9
        {
            text-align: justify;
            font-size: medium;
        }
    </style>
    
</head>
<body>
    <form id="form1" runat="server">
    <div style="height: 32px">
        <p class="style5">
           
            <em><strong style="text-align: center">Experiment : Database Connection &amp; SQL 
            Queries</strong></em></p>
        <p class="style4">
           
            <strong><em><span class="style6">Experiment:</span></em></strong><span 
                class="style6"> This experiment demonstrates some basic 
            SQL queries in ASP form</span></p><hr />
        <p class="style7">
           
            <strong><em>Documentation:</em></strong></p>
        <p class="style9">
           
            This experiment builds the database from details of the person entered in the 
            given form. The form takes valid input of the person, and either adds the entry 
            into the database table or deletes the entry form the database.</p>
        <p class="style9">
           
            Firstly, the connection to the SQL database is established using the information 
            given on the Prof. Rasala&#39;s site and tweeking the connection strings.</p>
        <p class="style8">
           
            <span class="style6">To add the entry INSERT Query is used, usage: </span> <code>
            <span class="style6">INSERT INTO table_name (column1, column2, column3,...) VALUES (value1, value2, value3,...)</span></code><span 
                class="style6"> </span> </p>
        <p class="style8">
           
            <span class="style6">To remove the entry DELETE query is used, 
            usage : </span> <code><span class="style6">DELETE FROM table_name WHERE some_column1=some_value1 and 
            some_column2 = some_value2</span></code></p>
        <p class="style8">
           
            <span class="style6">SELECT query is used to 
            retrive the data from the data source, Usage:</span><code><span class="style6"> SELECT * FROM table_name 
            </span> </code> <span class="style6">or </span> <code> <span class="style6">SELECT column_name(s) FROM table_name</span></code><span 
                class="style6"> </span> </p>
        <p class="style7">
           <hr />
            <strong><em><span class="style6">Experiment Demo:</span></em></strong></p>
        <p class="style7">
           
            Enter your details and press Add Entry to add your details into database</p>
        <p class="style7">
           
            Enter each details in the form and press Delete Entry to remove the entry from 
            the database.</p>
        <p class="style7">
           
            When you add/delete the entry, it is reflected in the database table and the 
            updated table is shown below.</p>
        <p class="style6">
           
            <strong>Enter Your Details:</strong></p>
        <table class="style1">
            <tr>
                <td class="style2" align="char">
                    Name:
                </td>
                <td class="style3">
                    <asp:TextBox ID="name" runat="server" Width="180px"></asp:TextBox>
                    <br />
                        <asp:RequiredFieldValidator 
                        runat="server" 
                        ErrorMessage="Please enter your name"
                        ControlToValidate="name" 
                        Font-Italic="True" 
                        Font-Names="Aparajita" 
                        ForeColor="Red">
                        </asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="style2" align="char">
                    Age:
                </td>
                <td class="style3">
                    <asp:TextBox ID="age" runat="server" Width="180px"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator 
                    ID="RequiredFieldValidator_age" 
                    runat="server" 
                    ControlToValidate="age"
                    ErrorMessage="Please Enter Your age " 
                    Font-Italic="True" 
                    Font-Names="Aparajita"
                    ForeColor="Red">
                    </asp:RequiredFieldValidator>
                    <%--<asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="age"
                        ErrorMessage="Enter Your age in Number" Font-Italic="True" Font-Names="Aparajita"
                        ForeColor="Red" ValidationExpression="^(\d)?(\d)?$"></asp:RegularExpressionValidator>--%>
                    <asp:RangeValidator ID="RangeValidator1" 
                    ControlToValidate="age" 
                    runat="server" 
                    ErrorMessage="Please Enter your correct age" 
                    Font-Italic="True" 
                    MaximumValue="120" 
                    MinimumValue="1"
                    Display="Dynamic" 
                    Type="Integer"
                    Font-Names="Aparajita"
                    ForeColor="Red">
                     </asp:RangeValidator>
                    <br />
                </td>
            </tr>
            <tr>
                <td class="style2" align="char">
                    City:
                </td>
                <td class="style3">
                    <asp:TextBox ID="city" runat="server" Width="180px"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator 
                    runat="server" 
                    ControlToValidate="city" 
                    ErrorMessage="Please Enter your city name"
                    Font-Italic="True" 
                    Font-Names="Aparajita" 
                    ForeColor="Red">
                    </asp:RequiredFieldValidator>
                </td>
            </tr>
        </table>
        <br class="style6" />
        <span class="style6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </span>
        <asp:Button ID="Button1" runat="server" Text="Add Entry" Width="125px" 
            OnClick="Button1_Click" CssClass="style6" />
        <span class="style6">&nbsp;&nbsp;&nbsp;
        </span>
        <asp:Button ID="Button2" runat="server" Text="Delete Entry" Width="125px" 
            OnClick="Button2_Click" CssClass="style6" />
        <br class="style6" />
        <br class="style6" />
            <asp:Label ID="data" runat="server" Visible="False" ForeColor="#009933" 
            CssClass="style6"></asp:Label>
        <br class="style6" />
        <br class="style6" />
        <p class="style6">
            <strong>Entries in Table: </strong>
        </p>
        <asp:SqlDataSource ID="SQLData" runat="server" 
        ConnectionString="<%$ ConnectionStrings:bhavikCS %>"
            
            SelectCommand="SELECT * FROM bhavik.[personData]"
            InsertCommand="INSERT into bhavik.personData (name, age, city)
            VALUES (@name, @age, @city)"
            DeleteCommand="DELETE FROM bhavik.personData WHERE name = @name and age = @age and city = @city ">
        
            <InsertParameters>
            <asp:FormParameter Name="name" FormField="name"/>
            <asp:FormParameter Name="age" FormField="age"/>
            <asp:FormParameter Name="city" FormField="city"/>
          </InsertParameters>

          <DeleteParameters>
            <asp:FormParameter Name="name" FormField="name" />
            <asp:FormParameter Name="age" FormField="age"/>
            <asp:FormParameter Name="city" FormField="city"/>
        </DeleteParameters>

          </asp:SqlDataSource>
        <br/>
        <asp:GridView ID="GridViewData" runat="server" AutoGenerateColumns="False" CellPadding="4"
            DataSourceID="SQLData" ForeColor="#333333" GridLines="None" AllowSorting="True"
            AllowPaging="True">
            <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
            <Columns>
                <asp:BoundField DataField="name" HeaderText="name" SortExpression="name" />
                <asp:BoundField DataField="age" HeaderText="age" SortExpression="age" />
                <asp:BoundField DataField="city" HeaderText="city" SortExpression="city" />
            </Columns>
            <EditRowStyle BackColor="#999999" />
            <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
            <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
            <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
            <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
            <SortedAscendingCellStyle BackColor="#E9E7E2" />
            <SortedAscendingHeaderStyle BackColor="#506C8C" />
            <SortedDescendingCellStyle BackColor="#FFFDF8" />
            <SortedDescendingHeaderStyle BackColor="#6F8DAE" />
        </asp:GridView>
        <hr />
         <p class="style6">
             <strong><em><a href="../fileview/Default.aspx?~/experiments/SQLexpt.aspx" target="_blank">Page Source</a></em></strong></p>
        <p class="style6">
             <strong><em>References:
    </em></strong>
    </p>
    <a href="https://www.w3schools.com"><span class="style6">w3Sschools
    </span>
    </a>
        <br class="style6">
    </br>
        <a href="http://www.ccs.neu.edu/teaching/web/accessing_sql.htm">
        <span class="style6">Prof. Rasala Site for SQL connection</span></a><br 
            class="style6"></br>
        <a href="https://www.lynda.com"><span class="style6">Lynda.com
    </span>
    </a>
    </div>
   
    </form>
    
</body>
</html>
