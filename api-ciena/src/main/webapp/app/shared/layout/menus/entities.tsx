import React from 'react';

import EntitiesMenuItems from 'app/entities/menu';
import { NavDropdown } from './menu-components';
import { faFileCode } from '@fortawesome/free-solid-svg-icons';

export const EntitiesMenu = () => (
  <NavDropdown icon={faFileCode} name="APIs" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <EntitiesMenuItems />
  </NavDropdown>
);
