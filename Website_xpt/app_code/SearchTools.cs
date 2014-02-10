using System;
using System.Collections.Generic;
using System.IO;
using System.Web;
using System.Web.UI;
using System.Text;
using System.Text.RegularExpressions;
using System.Drawing;
using System.Drawing.Imaging;
using System.Xml;

namespace edu.neu.ccis.rasala
{
    public class SearchTools
    {
        public const string highlightOpen = @"<span class='highlight'>";

        public const string highlightShut = @"</span>";

        public const Char nbsc = (char)160;

        public const Char shy = (char)173;


        /// <summary>
        /// Returns a list of Range objects representing
        /// the matches of the pattern in the content.
        /// 
        /// If isRegex is true,
        /// uses regular expression matching,
        /// otherwise uses plain text matching.
        /// 
        /// Matching begins at the start index.
        /// 
        /// Ignores case if ignoreCase is true.
        /// </summary>
        /// <param name="content">
        ///     The content to search</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="start">
        ///     The search start index</param>
        /// <param name="isRegex">
        ///     If true use regex matching</param>
        /// <param name="ignoreCase">
        ///     If true ignore case</param>
        public static List<Range> Search
            (string content,
             string pattern,
             int start,
             bool isRegex,
             bool ignoreCase)
        {
            if (isRegex)
                return RegexSearch
                    (content, pattern, start, ignoreCase);
            else
                return PlainSearch
                    (content, pattern, start, ignoreCase);
        }


        /// <summary>
        /// Returns a list of Range objects representing
        /// the matches of the pattern in the content
        /// using plain text matching.
        /// 
        /// Matching begins at the start index.
        /// 
        /// Ignores case if ignoreCase is true.
        /// </summary>
        /// <param name="content">
        ///     The content to search</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="start">
        ///     The search start index</param>
        /// <param name="ignoreCase">
        ///     If true ignore case</param>
        public static List<Range> PlainSearch
            (string content,
             string pattern,
             int start,
             bool ignoreCase)
        {
            List<Range> list = new List<Range>();

            if (StringTools.IsTrivial(content))
                return list;

            if (StringTools.IsTrivial(pattern))
                return list;

            StringComparison compare = ignoreCase
                ? StringComparison.OrdinalIgnoreCase
                : StringComparison.Ordinal;

            int contentLength = content.Length;

            int patternLength = pattern.Length;

            if (start < 0)
                start = 0;

            while (start < contentLength)
            {
                int index = content.IndexOf(pattern, start, compare);

                if (index < 0)
                    break;

                list.Add(new Range(index, patternLength));

                start = index + patternLength;
            }

            return list;
        }


        /// <summary>
        /// Returns a list of Range objects representing
        /// the matches of the pattern in the content
        /// using regular expression matching.
        /// 
        /// Matching begins at the start index.
        /// 
        /// Ignores case if ignoreCase is true.
        /// </summary>
        /// <param name="content">
        ///     The content to search</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="start">
        ///     The search start index</param>
        /// <param name="ignoreCase">
        ///     If true ignore case</param>
        public static List<Range> RegexSearch
            (string content,
             string pattern,
             int start,
             bool ignoreCase)
        {
            List<Range> list = new List<Range>();

            if (StringTools.IsTrivial(content))
                return list;

            if (StringTools.IsTrivial(pattern))
                return list;

            RegexOptions options = RegexOptions.Multiline;

            if (ignoreCase)
                options |= RegexOptions.IgnoreCase;

            Regex regex = new Regex(pattern, options);

            if (start < 0)
                start = 0;

            if (start < content.Length)
            {
                Match match = regex.Match(content, start);

                while (match.Success)
                {
                    list.Add(new Range(match.Index, match.Length));

                    match = match.NextMatch();
                }
            }

            return list;
        }


        /// <summary>
        /// Return HTML markup for the given content with the
        /// given list of highlight ranges.
        /// 
        /// If the content is multi-line, then to render the
        /// markup properly it should be enclosed in pre tags.
        /// To permit the concatenation of several instances
        /// of markup, this method does not add such pre tags.
        /// 
        /// The list of highlight ranges may be null if there
        /// are no items to highlight.
        /// 
        /// If the list of highlight ranges is non-null, then
        /// the ranges must be in increasing order and must not
        /// overlap one another.  This precondition is critical.
        /// 
        /// Properly handles line breaks defined by a single
        /// carriage return, a single line feed, or the pair
        /// of carriage return and line feed.
        /// 
        /// Tabs will be replaced by spaces.  Tabsize controls
        /// the transformation of tabs to the proper number of
        /// spaces.
        /// 
        /// The bool showLineNumbers controls whether or not to
        /// show line numbers with each line.
        /// 
        /// The bool showAllLines controls whether to show all
        /// lines in the content or only to show the lines that
        /// have content to highlight.
        /// 
        /// HTML encodes all punctuation characters in the Latin-0
        /// code block with the corresponding Unicode embeddings
        /// of the form: &#..;
        /// 
        /// This helps to prevent script injection attacks.
        /// 
        /// Ignores all control characters other than tab and the
        /// line break characters as discussed above.
        /// </summary>
        /// <param name="content">
        ///     The content to highlight</param>
        /// <param name="highlightList">
        ///     The ranges to highlight</param>
        /// <param name="tabsize">
        ///     The tab size for the conversion of tabs to spaces</param>
        /// <param name="showLineNumbers">
        ///     If true show line numbers</param>
        /// <param name="showAllLines">
        ///     If true show all lines;
        ///     if false show only the lines with highlight.</param>
        public static string HighlightMarkup
            (string content,
             List<Range> highlightList,
             int tabsize,
             bool showLineNumbers,
             bool showAllLines)
        {
            // set up for content
            if (StringTools.IsTrivial(content))
                return "";

            // set up for highlight
            int highCount = highlightList == null
                ? 0
                : highlightList.Count;

            bool doHighlight = highCount > 0;

            if (!(doHighlight || showAllLines))
                return "";

            // further set up for content
            int length = content.Length;

            List<Range> lineRangeList = StringTools.LineRangeList(content);

            int lines = lineRangeList.Count;
            int lineIndex = 0;

            // further set up for highlight
            List<Range> overlap = null;

            int highIndex = 0;

            // start builder
            StringBuilder builder = new StringBuilder(2 * length);

            // process content
            while (lineIndex < lines)
            {
                Range line = lineRangeList[lineIndex];

                int start = line.start;
                int limit = line.limit;

                // construct the overlap highlight ranges
                // that intersect this line, if any

                overlap = null;

                if (highIndex < highCount)
                {
                    while ((highIndex < highCount) &&
                           (highlightList[highIndex].limit < start))
                    {
                        highIndex++;
                    }

                    while ((highIndex < highCount) &&
                           (highlightList[highIndex].start < limit))
                    {
                        Range? r = Range.Intersect(line, highlightList[highIndex]);

                        if (r.HasValue)
                        {
                            Range s = r.Value;

                            if (s.length > 0)
                            {
                                if (overlap == null)
                                    overlap = new List<Range>();

                                overlap.Add(s);
                            }

                            if (highlightList[highIndex].limit <= limit)
                            {
                                highIndex++;
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                // increment line index here since visible line
                // numbers will start with 1
                lineIndex++;

                // prepare to skip this loop cycle if there are
                // no overlaps and we do not show all lines
                int overlapCount = (overlap == null) ? 0 : overlap.Count;

                if ((overlapCount == 0) && (!showAllLines))
                    continue;

                // process this line and add revised content to
                // the builder
                if (showLineNumbers)
                    builder.Append(lineIndex.ToString("0000  "));

                // data for line traversal and tab replacement
                int index = start;
                int position = 0;

                // data for dealing with highlight
                int next = 0;
                int open = -1;
                int shut = -1;

                if (next < overlapCount)
                {
                    Range range = overlap[next];
                    open = range.start;
                    shut = range.limit;
                }

                while (index < limit)
                {
                    if (index == shut)
                    {
                        builder.Append(highlightShut);

                        next++;

                        if (next < overlapCount)
                        {
                            Range range = overlap[next];
                            open = range.start;
                            shut = range.limit;
                        }
                        else
                        {
                            open = -1;
                            shut = -1;
                        }
                    }

                    if (index == open)
                        builder.Append(highlightOpen);

                    Append(builder, content[index], tabsize, ref position);

                    index++;
                }

                if (limit == shut)
                    builder.Append(highlightShut);

                if (limit < length)
                    builder.Append('\n');
            }

            return builder.ToString();
        }


        /// <summary>
        /// Utility to add a character or its HTML replacement to
        /// a StringBuilder that is incrementally being composed.
        /// 
        /// Assumes carriage return and line feed are handled by
        /// the caller separately and are NOT passed to this
        /// method.
        /// 
        /// Converts tab to the proper number of spaces by keeping
        /// track of the current logical position on the line.
        /// 
        /// HTML encodes all punctuation characters in the Latin-0
        /// code block with the corresponding Unicode embeddings
        /// of the form: &#..;
        /// 
        /// Ignores all control characters other than tab.
        /// </summary>
        /// <param name="builder">
        ///     The builder to accumulate markup</param>
        /// <param name="c">
        ///     The character to handle</param>
        /// <param name="tabsize">
        ///     The tabsize for conversion of tabs to spaces</param>
        /// <param name="position">
        ///     The logical position on the line</param>
        public static void Append
            (StringBuilder builder, char c, int tabsize, ref int position)
        {
            // process tab control character
            if (c == '\t')
            {
                int spaces = tabsize - (position % tabsize);

                for (int i = 0; i < spaces; i++)
                    builder.Append(' ');

                position += spaces;

                return;
            }

            // ignore other control characters
            bool isControl =
                (c.CompareTo(' ') < 0)
                || ((c.CompareTo('~') > 0) && (c.CompareTo(nbsc) < 0))
                || (c == shy);

            if (isControl)
                return;

            // process punctuation or simply output character
            bool isPunctuation =
                ((c.CompareTo(' ') > 0) && (c.CompareTo('0') < 0))
                || ((c.CompareTo('9') > 0) && (c.CompareTo('A') < 0))
                || ((c.CompareTo('Z') > 0) && (c.CompareTo('a') < 0))
                || ((c.CompareTo('z') > 0) && (c.CompareTo('~') <= 0));

            if (isPunctuation)
            {
                int v = Convert.ToInt32(c);
                builder.Append("&#");
                builder.Append(v);
                builder.Append(';');
            }
            else
            {
                builder.Append(c);
            }

            position++;
        }


        /// <summary>
        /// Returns a list of Range objects representing
        /// the matches of the pattern in the tilde file
        /// path name.
        /// 
        /// If isRegex is true,
        /// uses regular expression matching,
        /// otherwise uses plain text matching.
        /// 
        /// Ignores case if ignoreCase is true.
        /// 
        /// Returns null if the page or tilde file path
        /// is null or if the file is not OK to serve.
        /// 
        /// Precondition: The page that calls this method must:
        /// 
        ///   Be able to guarantee that the file is OK to serve
        ///   in context.
        /// </summary>
        /// <param name="page">
        ///     The page object that uses this method</param>
        /// <param name="tildeFilePath">
        ///     The tilde file path</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="isRegex">
        ///     If true use regex matching</param>
        /// <param name="ignoreCase">
        ///     If true ignore case</param>
        public static List<Range> SearchFileName
            (string tildeFilePath,
             string pattern,
             bool isRegex,
             bool ignoreCase)
        {
            return Search(tildeFilePath, pattern, 0, isRegex, ignoreCase);
        }


        /// <summary>
        /// Returns a list of Range objects representing
        /// the matches of the pattern in the content of
        /// a text file with the given tilde file path.
        /// 
        /// If successful, returns a reference to the
        /// text file content via an out parameter.
        /// 
        /// If isRegex is true,
        /// uses regular expression matching,
        /// otherwise uses plain text matching.
        /// 
        /// Ignores case if ignoreCase is true.
        /// 
        /// Returns null if the file is not a text file.
        /// 
        /// Precondition: The page that calls this method must:
        /// 
        ///   Be able to guarantee that the file is OK to serve
        ///   in context.
        /// </summary>
        /// <param name="page">
        ///     The page object that uses this method</param>
        /// <param name="tildeFilePath">
        ///     The tilde file path</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="isRegex">
        ///     If true use regex matching</param>
        /// <param name="ignoreCase">
        ///     If true ignore case</param>
        /// <param name="content">
        ///     Out param: If the file is a text file
        ///     then returns a reference to its content;
        ///     otherwise returns null.</param>
        public static List<Range> SearchFileContent
            (Page page,
             string tildeFilePath,
             string pattern,
             bool isRegex,
             bool ignoreCase,
             out string content)
        {
            content = null;

            int category = FileTools.GetFileCategory(tildeFilePath);

            if (category != FileTools.TEXT)
                return null;

            string filePath = page.MapPath(tildeFilePath);
            content = FileTools.GetFileAsText(filePath);

            return Search(content, pattern, 0, isRegex, ignoreCase);
        }


        /// <summary>
        /// Returns the markup from the search 
        /// of a file given its tilde path.
        /// 
        /// Preconditions: The page that calls this method must:
        /// 
        ///   1. Have executed the call
        /// 
        ///        SourceTools.LoadCSSandJavascript(this);
        ///     
        ///      during the initial call to PageLoad. 
        /// 
        ///   2. Be able to guarantee that the file is OK to serve
        ///      in context.
        /// </summary>
        /// <param name="page">
        ///     The page calling this method</param>
        /// <param name="tildeFilePath">
        ///     The file to search</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="isRegex">
        ///     Is the pattern a regular expression?</param>
        /// <param name="ignoreCase">
        ///     Ignore case in the search?</param>
        /// <param name="statistics">
        ///     Include file statistics markup?</param>
        /// <param name="download">
        ///     Include download button markup?</param>
        /// <param name="showAllLines">
        ///     Show all lines in the file?</param>
        /// <param name="onlyPublic">
        ///     Whether or not to restrict to public directories</param>
        public static string SearchFileMarkup
            (Page page,
             string tildeFilePath,
             string pattern,
             bool isRegex,
             bool ignoreCase,
             bool statistics,
             bool download,
             bool showAllLines,
             bool onlyPublic)
        {
            string content = null;

            List<Range> nameMatches =
                SearchFileName(tildeFilePath, pattern, isRegex, ignoreCase);

            List<Range> contentMatches =
                SearchFileContent
                    (page, tildeFilePath, pattern,
                     isRegex, ignoreCase, out content);

            bool matchName = !StringTools.IsTrivial(nameMatches);

            bool matchContent = !StringTools.IsTrivial(contentMatches);

            if (!(matchName || matchContent))
                return "";

            StringBuilder builder = new StringBuilder();

            // markup for highlighted tilde file path
            // with possible file view link

            string markupText =
                matchName
                    ? HighlightMarkup(tildeFilePath, nameMatches, 4, false, true)
                    : tildeFilePath;

            long bytes;
            DateTime? created;
            DateTime? modified;

            builder.Append
                (SourceTools.StatisticsMarkup
                    (page, tildeFilePath, markupText, onlyPublic,
                     true, true, statistics, download, null,
                     out bytes, out created, out modified));

            // markup for highlighted content

            if (matchContent)
            {
                builder.Append(HTML_Tools.open_pre);

                builder.Append
                    (HighlightMarkup
                        (content, contentMatches, 4, true, showAllLines));
                
                builder.Append(HTML_Tools.shut_pre);

                builder.Append("\n");
            }

            return builder.ToString();
        }


        /// <summary>
        /// Returns the markup from the search 
        /// of a directory given its tilde path.
        /// 
        /// Preconditions: The page that calls this method must:
        /// 
        ///   1. Have executed the call
        /// 
        ///        SourceTools.LoadCSSandJavascript(this);
        ///     
        ///      during the initial call to PageLoad. 
        /// 
        ///   2. Be able to guarantee that the directory is OK to serve
        ///      in context.
        /// </summary>
        /// <param name="page">
        ///     The page calling this method</param>
        /// <param name="tildeDirectoryPath">
        ///     The directory to search</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="isRegex">
        ///     Is the pattern a regular expression?</param>
        /// <param name="ignoreCase">
        ///     Ignore case in the search?</param>
        /// <param name="statistics">
        ///     Include file statistics markup?</param>
        /// <param name="download">
        ///     Include download button markup?</param>
        /// <param name="onlyPublic">
        ///     Whether or not to restrict to public directories</param>
        public static string SearchDirectoryMarkup
            (Page page,
             string tildeDirectoryPath,
             string pattern,
             bool isRegex,
             bool ignoreCase,
             bool statistics,
             bool download,
             bool onlyPublic)
        {
            int n = tildeDirectoryPath.Length;

            if (tildeDirectoryPath[n - 1] != SourceTools.slash)
                tildeDirectoryPath = tildeDirectoryPath + SourceTools.slash;

            StringBuilder builder = new StringBuilder();

            string directoryPath = page.MapPath(tildeDirectoryPath);

            DirectoryInfo directory = new DirectoryInfo(directoryPath);

            FileInfo[] files = directory.GetFiles();

            int m = files.Length;

            for (int i = 0; i < m; i++)
            {
                string tildeFilePath = tildeDirectoryPath + files[i].Name;

                string markup = SearchFileMarkup(page, tildeFilePath, pattern,
                    isRegex, ignoreCase, statistics, download, false, onlyPublic);

                if (!StringTools.IsTrivial(markup))
                {
                    builder.Append(markup);
                    builder.Append("<hr />\n");
                }
            }

            return builder.ToString();
        }


        /// <summary>
        /// Returns the markup from the search 
        /// of either the public directories
        /// or all directories that start at
        /// tildeDirectoryPath.
        /// </summary>
        /// <param name="page">
        ///     The page calling this method</param>
        /// <param name="tildeDirectoryPath">
        ///     The root of the directory tree to search</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="isRegex">
        ///     Is the pattern a regular expression?</param>
        /// <param name="ignoreCase">
        ///     Ignore case in the search?</param>
        /// <param name="statistics">
        ///     Include file statistics markup?</param>
        /// <param name="download">
        ///     Include download button markup?</param>
        /// <param name="onlyPublic">
        ///     Whether or not to restrict to public directories</param>
        public static string SearchTreeMarkup
            (Page page,
             string tildeDirectoryPath,
             string pattern,
             bool isRegex,
             bool ignoreCase,
             bool statistics,
             bool download,
             bool onlyPublic)
        {
            StringBuilder builder = new StringBuilder();

            int n = tildeDirectoryPath.Length;

            if (tildeDirectoryPath[n - 1] != SourceTools.slash)
                tildeDirectoryPath = tildeDirectoryPath + SourceTools.slash;

            string directoryPath = page.MapPath(tildeDirectoryPath);

            string rootPath = FileTools.GetRoot(page);

            List<string> directoryList =
                SourceTools.MakeDirectoryList(directoryPath, onlyPublic);

            List<string> tildeDirectoryList =
                FileTools.GetTildePaths(rootPath, directoryList);

            foreach (string tdp in tildeDirectoryList)
            {
                string markup =
                    SearchDirectoryMarkup(page, tdp, pattern,
                        isRegex, ignoreCase, statistics, download, onlyPublic);

                if (!StringTools.IsTrivial(markup))
                    builder.Append(markup);
            }

            return builder.ToString();
        }


        /// <summary>
        /// Returns the markup from the search 
        /// of either the public directories
        /// or all directories.
        /// </summary>
        /// <param name="page">
        ///     The page calling this method</param>
        /// <param name="pattern">
        ///     The search pattern</param>
        /// <param name="isRegex">
        ///     Is the pattern a regular expression?</param>
        /// <param name="ignoreCase">
        ///     Ignore case in the search?</param>
        /// <param name="statistics">
        ///     Include file statistics markup?</param>
        /// <param name="download">
        ///     Include download button markup?</param>
        /// <param name="onlyPublic">
        ///     Whether or not to restrict to public directories</param>
        public static string SearchSiteMarkup
            (Page page,
             string pattern,
             bool isRegex,
             bool ignoreCase,
             bool statistics,
             bool download,
             bool onlyPublic)
        {
            StringBuilder builder = new StringBuilder();

            string rootPath = FileTools.GetRoot(page);

            List<string> directoryList =
                SourceTools.MakeDirectoryList(rootPath, onlyPublic);

            List<string> tildeDirectoryList =
                FileTools.GetTildePaths(rootPath, directoryList);

            foreach (string tildeDirectoryPath in tildeDirectoryList)
            {
                string markup =
                    SearchDirectoryMarkup(page, tildeDirectoryPath, pattern,
                        isRegex, ignoreCase, statistics, download, onlyPublic);

                if (!StringTools.IsTrivial(markup))
                    builder.Append(markup);
            }

            return builder.ToString();
        }

    }
}