import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { faCode, faTerminal } from '@fortawesome/free-solid-svg-icons';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
      <MenuItem icon={faTerminal} to="/api/apiauth">
        API Authentication
      </MenuItem>
      <MenuItem icon={faCode} to="/api/apidoc">
        API Documentation
      </MenuItem>
    </>
  );
};

export default EntitiesMenu;
