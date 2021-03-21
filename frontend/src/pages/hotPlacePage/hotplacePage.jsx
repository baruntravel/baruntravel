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
    <div className={styles.HotplacePage}>
      <header className={styles.navbar}>navbar</header>
      <div className={styles.map}>
        <HotplaceMap
          handleCartPortal={handleCartPortal}
          clickedPlace={clickedPlace}
        />
      </div>
      {cartPortal && (
        <PortalCart place={place} handleCartPortal={handleCartPortal} />
      )}
    </div>
  );
};

export default HotplacePage;
