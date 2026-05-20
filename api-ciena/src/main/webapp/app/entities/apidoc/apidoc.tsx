import './apidoc.scss';

import React from 'react';

const ApiDoc = () => (
  <div>
    <iframe
      src="../swagger-ui/ciena.html"
      width="100%"
      height="800"
      title="Swagger UI"
      seamless
      style={{ border: 'none' }}
      data-cy="swagger-frame"
    />
  </div>
);

export default ApiDoc;
