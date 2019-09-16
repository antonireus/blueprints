<%@ page info="Chat" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Chat</title>
    <script>
        var ws;
        var username;

        function connect() {
            username = document.getElementById("username").value;

            var host = document.location.host;
            ws = new WebSocket("ws://" + host + "${pageContext.request.contextPath}/chat/" + username);
            console.log("Connecting to: " + ws.url);

            ws.onmessage = function(event) {
                var log = document.getElementById("log");
                console.log(event.data);

                var message = JSON.parse(event.data);
                log.innerHTML += "bot: " + message.content + "\n";
                log.scrollTop = log.scrollHeight;
            };

            ws.onopen = function(event) {
                var button = document.getElementById("connectButton");
                button.disabled = true;
            };

            ws.onclose = function(event) {
                var button = document.getElementById("connectButton");
                button.disabled = false;
            };
        }

        function send() {
            var msgElement = document.getElementById("msg");
            var content = msgElement.value;

            var json = JSON.stringify({
                "content": content
            });

            ws.send(json);
            var log = document.getElementById("log");
            log.innerHTML += username + ": " + content + "\n";
            msgElement.value = "";
        }
    </script>
</head>
<body>
<table>
    <tr>
        <td colspan="2">
            <input type="text" id="username" placeholder="Username"/>
            <button type="button" id="connectButton" onclick="connect();" >Connect</button>
        </td>
    </tr>
    <tr>
        <td>
            <textarea readonly="readonly" rows="10" cols="80" id="log"></textarea>
        </td>
    </tr>
    <tr>
        <td>
            <input type="text" size="51" id="msg" placeholder="Message"/>
            <button type="button" onclick="send();" >Send</button>
        </td>
    </tr>
</table>
</body>
</html>