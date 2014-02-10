using System;
using System.Text;
using System.Collections.Generic;

namespace edu.neu.ccis.rasala
{
    public class StringTools
    {
        /// <summary>
        /// Returns true if the given string
        /// is null or of length 0.
        /// </summary>
        public static bool IsTrivial(string s)
        {
            return (s == null) || (s.Length == 0);
        }


        /// <summary>
        /// Returns true if the given list
        /// is null or has count 0.
        /// </summary>
        public static bool IsTrivial<T>(IList<T> list)
        {
            return (list == null) || (list.Count == 0);
        }


        /// <summary>
        /// Uses a <code>StringBuilder</code> to concatenate the strings
        /// in the string list and then returns the result.
        /// 
        /// If the given data is null, returns the empty string.
        /// 
        /// If the given data is non-null, then each non-null item in
        /// data will be concatenated.
        /// 
        /// Hence there is no need to worry about null data or null items.
        /// </summary>
        /// <param name="data">The data array of strings</param>
        /// <returns>The concatenation of the strings in data</returns>
        public static string Build(IList<string> data)
        {
            if (data == null)
                return "";

            StringBuilder builder = new StringBuilder();

            foreach (string s in data)
                if (s != null)
                    builder.Append(s);

            return builder.ToString();
        }


        /// <summary>
        /// Uses a <code>StringBuilder</code> to concatenate the strings
        /// in the string list with the given separator and then returns
        /// the result.
        /// 
        /// If the given data is null, returns the empty string.
        /// 
        /// If the given data is non-null, then each non-null item in
        /// data will be concatenated and then followed by the separator.
        /// 
        /// Hence there is no need to worry about null data or null items.
        /// </summary>
        /// <param name="data">The data array of strings</param>
        /// <param name="separator">The separator to add after each string</param>
        /// <returns>The concatenation of the strings in data</returns>
        public static string Build(IList<string> data, string separator)
        {
            if (data == null)
                return "";

            if (IsTrivial(separator))
                return Build(data);

            StringBuilder builder = new StringBuilder();

            foreach (string s in data)
                if (s != null)
                {
                    builder.Append(s);
                    builder.Append(separator);
                }

            return builder.ToString();
        }


        /// <summary>
        /// Returns a Range list consisting of the line ranges
        /// for the consecutive lines in the content.
        /// 
        /// The end of a line is signaled by:
        ///   1. A newline \n
        ///   2. A carriage return \r
        ///   3. The end of the content string
        /// 
        /// In these cases, the start of the next line is found
        /// as follows:
        ///   1. For \n, the next character after \n.
        ///   2. For \r:
        ///        If the next character is \n,
        ///          then after the \n
        ///          else after the \r.
        ///   3. No next line.
        /// 
        /// 
        /// Note that the regex pattern ^.*$ ends lines only at
        /// \n or at the end of the content string.  This means
        /// that any \r characters will be viewed as part of a
        /// line rather than as part of a line break.
        /// 
        /// This does not correspond to what will be seen by an
        /// end user if the content is displayed on an html web
        /// page using the pre tag.
        /// 
        /// This method is designed to properly handle both \r
        /// and \n and the pair \r\n as a single line break.
        /// </summary>
        /// <param name="content">The content to examine</param>
        public static List<Range> LineRangeList(string content)
        {
            List<Range> list = new List<Range>();

            if (StringTools.IsTrivial(content))
                return list;

            int start = 0;
            int length = content.Length;

            while (start < length)
            {
                int index = start;

                while ((index < length)
                       && (content[index] != '\n')
                       && (content[index] != '\r'))
                    index++;

                list.Add(new Range(start, index - start));

                start = index + 1;

                if (start >= length)
                    break;

                // check for \r\n pair
                if ((content[index] == '\r') && (content[start] == '\n'))
                    start++;
            }

            return list;
        }


        /// <summary>
        /// Returns a list of the lines in the given content.
        /// 
        /// If trim is false, all lines are returned as is.
        /// 
        /// If trim is true, then:
        ///   Trim each line.
        ///   Add a trimmed line to the list only if it is
        ///   non-empty.
        ///   
        /// Utilizes LineRangeList to define the line breaks.
        /// </summary>
        /// <param name="content"></param>
        /// <param name="trim"></param>
        /// <returns></returns>
        public static List<string> LineList(string content, bool trim)
        {
            List<string> list = new List<string>();

            List<Range> rangeList = LineRangeList(content);

            int n = rangeList.Count;

            for (int i = 0; i < n; i++)
            {
                Range range = rangeList[i];

                string item = content.Substring(range.start, range.length);

                if (trim)
                {
                    item = item.Trim();

                    if (item.Length > 0)
                        list.Add(item);
                }
                else
                {
                    list.Add(item);
                }
            }

            return list;
        }


        /*
         * This function may be used to parse a query string for
         * a complex embedded parameter but it may also be used
         * for other purposes.
         * 
         * 
         * Returns an embedded parameter in a source string that
         * is introduced by a string specified here as param.
         * 
         * Further the embedded parameter must be contained in a
         * pair of matching bounding characters such as:
         * 
         *     "  '  |
         * 
         * The bounding character may not appear in the embedded
         * parameter.
         * 
         * The bounding character is determined as the first
         * character that occurs after param.
         * 
         * The embedded parameter is the substring between that
         * first character and its next occurence in the source
         * string.
         * 
         * If unescape is true then the method Unescape below
         * will replace the source with its unescaped version
         * before finding the embedded parameter.
         * 
         * 
         * If an error occurs, returns an empty string.
         * 
         * 
         * For example, if source is
         * 
         *     http://www.abc.com/seek.htm?url="http://www.xyz.com?a=1&b=2"
         * 
         * then the call
         * 
         *     FindParameter(source, "url=", true)
         * 
         * returns:
         * 
         *     http://www.xyz.com?a=1&b=2
         *
         * As you can see, this method of specifying and finding a
         * parameter allows a full url to be specified as a query
         * parameter to another url.
         */
        public static string FindParameter
            (string source, string param, bool unescape)
        {
            if (IsTrivial(source) || IsTrivial(param))
                return "";

            if (unescape)
                source = Unescape(source);

            try
            {
                int a = source.IndexOf(param);

                if (a >= 0)
                {
                    source = source.Substring(a + param.Length);

                    if (source.Length > 0)
                    {
                        char c = source[0];
                        source = source.Substring(1);

                        a = source.IndexOf(c);

                        if (a >= 0)
                        {
                            return source.Substring(0, a);
                        }
                    }

                }
            }
            catch { }
        
            return "";
        }


        /*
         * This method will construct a new string
         * by traversing the source and replacing
         * triplets of the form
         * 
         *     %xy
         * 
         * where x and y are hex digit characters
         * with the corresponding character in the
         * range 0 ... 255.
         * 
         * If % occurs but is not followed by two
         * hex digit characters then it is passed
         * through as is.
         * 
         * 
         * This method should not be needed since
         * there is the method:
         * 
         *     Uri.UnescapeDataString
         * 
         * However, the Uri.UnescapeDataString method
         * throws an exception if % occurs but is not 
         * followed by two hex digit characters.
         * 
         * This exception is undocumented in the MSDN
         * documentation.
         */
        public static string Unescape(string source)
        {
            if (IsTrivial(source))
                return source;

            char percent = '%';

            int i = 0;
            int n = source.Length;

            StringBuilder builder = new StringBuilder();

            while (i < n)
            {
                char c = source[i];

                if ((c != percent) || ((i + 2) >= n))
                {
                    builder.Append(c);
                    i++;

                    continue;
                }

                char d = source[i + 1];
                char e = source[i + 2];

                int p = HexValue(d);
                int q = HexValue(e);

                if ((p < 0) || (q < 0))
                {
                    builder.Append(c);
                    i++;

                    continue;
                }

                char a = (char)(16 * p + q);

                builder.Append(a);
                i += 3;
            }

            return builder.ToString();
        }


        /*
         * Returns the integer value between 0 ... 15
         * of a hex digit character or else returns -1.
         */
        public static int HexValue(char c)
        {
            if (('0' <= c) && (c <= '9'))
                return (int)c - (int)'0';

            if (('A' <= c) && (c <= 'F'))
                return (int)c - (int)'A' + 10;

            if (('a' <= c) && (c <= 'f'))
                return (int)c - (int)'a' + 10;

            return -1;
        }


        /** Returns the text of an Enum. */
        public static string textual(Enum e)
        {
            return e.ToString();
        }


        /**
         * Returns the value of the underlying type
         * of an Enum entity converted to a string.
         */
        public static string numeric(Enum e)
        {
            Type undertype = Enum.GetUnderlyingType(e.GetType());

            return Convert.ChangeType(e, undertype).ToString();
        }

    }
}