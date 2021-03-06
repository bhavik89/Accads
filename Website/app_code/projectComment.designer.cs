﻿#pragma warning disable 1591
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.1
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace edu.neu.ccis.bhavik.projectComment
{
	using System.Data.Linq;
	using System.Data.Linq.Mapping;
	using System.Data;
	using System.Collections.Generic;
	using System.Reflection;
	using System.Linq;
	using System.Linq.Expressions;
	using System.ComponentModel;
	using System;
	
	
	[global::System.Data.Linq.Mapping.DatabaseAttribute(Name="DataSource")]
	public partial class projectCommentDataContext : System.Data.Linq.DataContext
	{
		
		private static System.Data.Linq.Mapping.MappingSource mappingSource = new AttributeMappingSource();
		
    #region Extensibility Method Definitions
    partial void OnCreated();
    partial void InsertprojectComment(projectComment instance);
    partial void UpdateprojectComment(projectComment instance);
    partial void DeleteprojectComment(projectComment instance);
    #endregion
		
		public projectCommentDataContext() : 
				base(global::System.Configuration.ConfigurationManager.ConnectionStrings["bhavikCS"].ConnectionString, mappingSource)
		{
			OnCreated();
		}
		
		public projectCommentDataContext(string connection) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public projectCommentDataContext(System.Data.IDbConnection connection) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public projectCommentDataContext(string connection, System.Data.Linq.Mapping.MappingSource mappingSource) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public projectCommentDataContext(System.Data.IDbConnection connection, System.Data.Linq.Mapping.MappingSource mappingSource) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public System.Data.Linq.Table<projectComment> projectComment
		{
			get
			{
				return this.GetTable<projectComment>();
			}
		}
	}
	
	[global::System.Data.Linq.Mapping.TableAttribute(Name="bhavik.projectComment")]
	public partial class projectComment : INotifyPropertyChanging, INotifyPropertyChanged
	{
		
		private static PropertyChangingEventArgs emptyChangingEventArgs = new PropertyChangingEventArgs(String.Empty);
		
		private string _time;
		
		private string _date;
		
		private string _comments;
		
		private string _username;
		
		private int _commentID;
		
    #region Extensibility Method Definitions
    partial void OnLoaded();
    partial void OnValidate(System.Data.Linq.ChangeAction action);
    partial void OnCreated();
    partial void OntimeChanging(string value);
    partial void OntimeChanged();
    partial void OndateChanging(string value);
    partial void OndateChanged();
    partial void OncommentsChanging(string value);
    partial void OncommentsChanged();
    partial void OnusernameChanging(string value);
    partial void OnusernameChanged();
    partial void OncommentIDChanging(int value);
    partial void OncommentIDChanged();
    #endregion
		
		public projectComment()
		{
			OnCreated();
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_time", DbType="NVarChar(MAX) NOT NULL", CanBeNull=false)]
		public string time
		{
			get
			{
				return this._time;
			}
			set
			{
				if ((this._time != value))
				{
					this.OntimeChanging(value);
					this.SendPropertyChanging();
					this._time = value;
					this.SendPropertyChanged("time");
					this.OntimeChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_date", DbType="NVarChar(MAX) NOT NULL", CanBeNull=false)]
		public string date
		{
			get
			{
				return this._date;
			}
			set
			{
				if ((this._date != value))
				{
					this.OndateChanging(value);
					this.SendPropertyChanging();
					this._date = value;
					this.SendPropertyChanged("date");
					this.OndateChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_comments", DbType="NVarChar(MAX) NOT NULL", CanBeNull=false)]
		public string comments
		{
			get
			{
				return this._comments;
			}
			set
			{
				if ((this._comments != value))
				{
					this.OncommentsChanging(value);
					this.SendPropertyChanging();
					this._comments = value;
					this.SendPropertyChanged("comments");
					this.OncommentsChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_username", DbType="NVarChar(MAX) NOT NULL", CanBeNull=false)]
		public string username
		{
			get
			{
				return this._username;
			}
			set
			{
				if ((this._username != value))
				{
					this.OnusernameChanging(value);
					this.SendPropertyChanging();
					this._username = value;
					this.SendPropertyChanged("username");
					this.OnusernameChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_commentID", AutoSync=AutoSync.OnInsert, DbType="Int NOT NULL IDENTITY", IsPrimaryKey=true, IsDbGenerated=true)]
		public int commentID
		{
			get
			{
				return this._commentID;
			}
			set
			{
				if ((this._commentID != value))
				{
					this.OncommentIDChanging(value);
					this.SendPropertyChanging();
					this._commentID = value;
					this.SendPropertyChanged("commentID");
					this.OncommentIDChanged();
				}
			}
		}
		
		public event PropertyChangingEventHandler PropertyChanging;
		
		public event PropertyChangedEventHandler PropertyChanged;
		
		protected virtual void SendPropertyChanging()
		{
			if ((this.PropertyChanging != null))
			{
				this.PropertyChanging(this, emptyChangingEventArgs);
			}
		}
		
		protected virtual void SendPropertyChanged(String propertyName)
		{
			if ((this.PropertyChanged != null))
			{
				this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
			}
		}
	}
}
#pragma warning restore 1591
