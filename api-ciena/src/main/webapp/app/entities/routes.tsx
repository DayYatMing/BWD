import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';
import ApiAuth from './apiauth/apiauth';
import ApiDoc from './apidoc/apidoc';

/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();

  if (entitiesReducers && Object.keys(entitiesReducers).length > 0) {
    store.injectReducer('apiciena', combineReducers(entitiesReducers as ReducersMapObject));
  }

  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}

        <Route path="api/apiauth" element={<ApiAuth />} />
        <Route path="api/apidoc" element={<ApiDoc />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};
