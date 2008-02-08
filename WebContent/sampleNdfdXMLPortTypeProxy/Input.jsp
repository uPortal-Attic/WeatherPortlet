<%@page contentType="text/html;charset=UTF-8"%>
<HTML>
<HEAD>
<TITLE>Inputs</TITLE>
</HEAD>
<BODY>
<H1>Inputs</H1>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

boolean valid = true;

if(methodID != -1) methodID = Integer.parseInt(method);
switch (methodID){ 
case 2:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 5:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">endpoint:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="endpoint8" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 10:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 13:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">latitude:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="latitude16" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">longitude:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="longitude18" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">startTime:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="startTime22" SIZE=20></TD>
<%
java.text.DateFormat dateFormatstartTime22 = java.text.DateFormat.getDateInstance();
java.util.GregorianCalendar gcExampstartTime22  = new java.util.GregorianCalendar();
java.util.Date datestartTime22 = gcExampstartTime22.getTime();
String tempResultstartTime22 = dateFormatstartTime22.format(datestartTime22);
%>
<TD ALIGN="left">
</TR>
<TR>
<TD> </TD>
<TD ALIGN="left"> eg. <%= tempResultstartTime22 %> </TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">endTime:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="endTime24" SIZE=20></TD>
<%
java.text.DateFormat dateFormatendTime24 = java.text.DateFormat.getDateInstance();
java.util.GregorianCalendar gcExampendTime24  = new java.util.GregorianCalendar();
java.util.Date dateendTime24 = gcExampendTime24.getTime();
String tempResultendTime24 = dateFormatendTime24.format(dateendTime24);
%>
<TD ALIGN="left">
</TR>
<TR>
<TD> </TD>
<TD ALIGN="left"> eg. <%= tempResultendTime24 %> </TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">weatherParameters:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">appt:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="appt28" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">pxtstmwinds:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="pxtstmwinds30" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">wgust:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="wgust32" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">dew:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="dew34" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">pop12:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="pop1236" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ptotsvrtstm:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ptotsvrtstm38" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">snow:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="snow40" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">maxt:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="maxt42" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">mint:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="mint44" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">waveh:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="waveh46" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">incw50:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="incw5048" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">wspd:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="wspd50" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cumw50:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cumw5052" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">wdir:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="wdir54" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">pxhail:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="pxhail56" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">pxtotsvrtstm:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="pxtotsvrtstm58" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ptstmwinds:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ptstmwinds60" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">sky:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="sky62" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">qpf:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="qpf64" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">pxtornado:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="pxtornado66" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">incw34:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="incw3468" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">incw64:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="incw6470" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cumw34:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cumw3472" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">rh:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="rh74" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">ptornado:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="ptornado76" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">cumw64:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cumw6478" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">phail:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="phail80" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">temp:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="temp82" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">conhazo:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="conhazo84" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">icons:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="icons86" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">wx:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="wx88" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 90:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">latitude:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="latitude93" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">longitude:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="longitude95" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">startDate:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="startDate97" SIZE=20></TD>
<%
java.text.DateFormat dateFormatstartDate97 = java.text.DateFormat.getDateInstance();
java.util.GregorianCalendar gcExampstartDate97  = new java.util.GregorianCalendar();
java.util.Date datestartDate97 = gcExampstartDate97.getTime();
String tempResultstartDate97 = dateFormatstartDate97.format(datestartDate97);
%>
<TD ALIGN="left">
</TR>
<TR>
<TD> </TD>
<TD ALIGN="left"> eg. <%= tempResultstartDate97 %> </TD>
</TR>
</TABLE>
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">numDays:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="numDays99" SIZE=20></TD>
</TR>
</TABLE>
<TABLE>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1111111111:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">URLString:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="url1111111111" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1111111112:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
}
if (valid) {
%>
Select a method to test.
<%
    return;
}
%>

</BODY>
</HTML>
