import React, { useState } from "react";
import HotplaceMap from "../../components/hotplaceMap/hotplaceMap";
import PortalCart from "../../containers/portalCart/portalCart";
import styles from "./hotplacePage.module.css";

const HotplacePage = (props) => {
  const [cartPortal, setCartPortal] = useState(true);
  const handleCartPortal = () => {
    setCartPortal(!cartPortal);
  };
  return (
    <div>
      <HotplaceMap handleCartPortal={handleCartPortal} />
      {cartPortal && <PortalCart />}
    </div>
  );
};

export default HotplacePage;
