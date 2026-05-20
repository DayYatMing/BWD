import React from 'react';

const ApiAuth = () => {
  return (
    <>
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          padding: '20px',
          background: '#f9f9f9',
        }}
      >
        <div
          style={{
            maxWidth: '800px',
            width: '100%',
            padding: '20px',
            fontFamily: 'system-ui',
            background: 'white',
            borderRadius: '12px',
            boxShadow: '0 2px 10px rgba(0,0,0,0.08)',
          }}
        >
          <h2>API Authentication</h2>

          <p>Before calling protected endpoints, you must obtain an authentication token. This token must be included in every request.</p>

          <div style={{ marginTop: '20px' }}>
            <h3>Obtain a Token</h3>

            <div
              style={{
                padding: '12px',
                border: '1px solid #ddd',
                borderRadius: '8px',
                marginTop: '10px',
              }}
            >
              <strong>1. Request URL</strong>
              <br />
              <img src={'content/images/request_url.png'} alt="Request URL" style={{ width: '40%', marginTop: '10px' }} />
            </div>

            <div
              style={{
                padding: '12px',
                border: '1px solid #ddd',
                borderRadius: '8px',
                marginTop: '10px',
              }}
            >
              <strong>2. Request Body</strong> <br />
              <img src={'content/images/request_body.png'} alt="Request Body" style={{ width: '35%', marginTop: '10px' }} />
            </div>
          </div>

          <div style={{ marginTop: '20px' }}>
            <h3>Notes</h3>
            <ul>
              <li>Token expires after 24 hours.</li>
              <li>Expired tokens are rejected by the API.</li>
              <li>
                Use the token in the <code>Authorization</code> header.
              </li>
              <li>
                Auth type is <code>Bearer</code>.
              </li>
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default ApiAuth;
