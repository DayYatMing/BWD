import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Alert, Col, Row } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="home-container">
      <div className="banner banner-page u-bg-gradient">
        <div className="banner-body">
          <div className="container">
            <h1 className="banner-heading h1">Welcome to BW Digital API</h1>
          </div>
        </div>
      </div>
      <br />
      {account?.login ? (
        <div className="home-message">
          <Alert color="success" fade={false}>
            You are logged in as user &quot;{account.login}&quot;.
          </Alert>
        </div>
      ) : (
        <div className="home-message">
          <Alert color="warning" fade={false}>
            <span>Click here to </span>
            <Link to="/login" className="alert-link">
              sign in
            </Link>
          </Alert>

          <Alert color="warning" fade={false}>
            You don&apos;t have an account yet?&nbsp; Please click here to&nbsp;
            <Link to="/contact" className="alert-link">
              Contact
            </Link>
          </Alert>
        </div>
      )}
    </div>
  );
};

export default Home;
