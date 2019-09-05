<%@ page info="Chat" %>
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
            var pathname = "${pageContext.request.contextPath}";

            ws = new WebSocket("ws://" +host  + pathname + "/chat/" + username);
            //ws = new WebSocket("<c:url value="/chat/" />" + username);

            ws.onmessage = function(event) {
                var log = document.getElementById("log");
                console.log(event.data);
                /*
                var message = JSON.parse(event.data);
                log.innerHTML += message.from + " : " + message.content + "\n";
                */
                log.innerHTML += "bot: " + event.data + "\n";
                log.scrollTop = log.scrollHeight;
            };
        }

        function send() {
            var msgElement = document.getElementById("msg");
            var content = msgElement.value;
            /*
            var json = JSON.stringify({
                "content":content
            });*/

            //ws.send(json);
            ws.send(content);
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
            <button type="button" onclick="connect();" >Connect</button>
        </td>
    </tr>
    <tr>
        <td>
            <textarea readonly="true" rows="10" cols="80" id="log"></textarea>
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