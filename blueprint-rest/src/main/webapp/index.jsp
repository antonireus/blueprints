<%@ page
        contentType="text/html; charset=UTF-8"
        trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Swagger UI</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/swagger-ui.css"/>" >
</head>
<body>
<div id="swagger-ui"></div>

<script src="<c:url value="/swagger-ui-bundle.js" />"> </script>
<script src="<c:url value="/swagger-ui-standalone-preset.js" />"></script>
<script>
    window.onload = function() {
        // Build a system
        const ui = SwaggerUIBundle({
            url: "<c:url value="/resource/openapi.json" />",
            dom_id: '#swagger-ui',
            presets: [
                SwaggerUIBundle.presets.apis,
                SwaggerUIStandalonePreset
            ],
            plugins: [
                SwaggerUIBundle.plugins.DownloadUrl
            ],
            layout: "StandaloneLayout"
        });
        window.ui = ui
    }
</script>
</body>

</html>