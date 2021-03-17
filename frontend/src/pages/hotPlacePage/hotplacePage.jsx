import React, { useState } from "react";
import HotplaceMap from "../../components/map/hotplaceMap/hotplaceMap";
import PortalCart from "../../containers/portalCart/portalCart";
import styles from "./hotplacePage.module.css";

const HotplacePage = (props) => {
  const [cartPortal, setCartPortal] = useState(false);
  const [place, setPlace] = useState({});
  const handleCartPortal = () => {
    setCartPortal(!cartPortal);
  };
  const clickedPlace = (place) => {
    setPlace(place);
  };
  return (
    <div>
      <HotplaceMap
        handleCartPortal={handleCartPortal}
        clickedPlace={clickedPlace}
      />
      {cartPortal && (
        <PortalCart place={place} handleCartPortal={handleCartPortal} />
      )}
    </div>
  );
};

export default HotplacePage;
