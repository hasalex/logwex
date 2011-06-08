<html>
<head>
    <title>LogWEx Examples</title>
    <script type="text/javascript">
        <!--//
        function changeAction(path) {
            document.main_form.action = path + ".log";
        }

        function changePattern(expression) {
            document.main_form.pattern.value += " " + expression;
        }
        //-->
    </script>
</head>
<body>
<h1>LogWEx Examples</h1>

<form name="main_form" action="doit.log" method="POST">
    <p>Servlet Path <input value="doit" onchange="changeAction(this.value)"></p>

    <p>Pattern Layout <input name="pattern" value="%p - %m"  style="width:500px">   <select onchange="changePattern(this.value)">
        <option value=""></option>
        <option value="%X{request.servletPath}">%X{request.servletPath}</option>
        <option value="%X{request.method}">%X{request.method}</option>
        <option value="%X{request.contextPath}">%X{request.contextPath}</option>
        <option value="%X{request.serverName}">%X{request.serverName}</option>
        <option value="%X{request.serverPort}">%X{request.serverPort}</option>
        <option value="%X{session.id}">%X{session.id}</option>
    </select> (%n will be automtically added at the end of the pattern)
    </p>
    <p><input type="submit"></p>
</form>
<p>Thread name : <%= Thread.currentThread().getName() %></p>
</body>
</html>
