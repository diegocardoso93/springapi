window.onload = function() {
  //<editor-fold desc="Changeable Configuration Block">

  // the following lines will be replaced by docker/configurator, when it runs in a docker-container
  window.ui = SwaggerUIBundle({
    url: "http://localhost:8000/api-docs.json",  // Ensure this matches where the JSON is available
    dom_id: '#swagger-ui',
    requestInterceptor: function (request) {
      request.headers['Accept'] = 'application/json';
      return request;
    },
    deepLinking: true,
    presets: [
        SwaggerUIBundle.presets.apis,
        SwaggerUIStandalonePreset
    ],
    layout: "StandaloneLayout"
  });

  //</editor-fold>
};
