import React from "react";
import PortalCartBody from "../../components/portalCartBody/portalCartBody";
import styles from "./portalCart.module.css";

const PortalCart = ({ place, handleCartPortal }) => {
  return (
    <div className={styles.PortalCart}>
      <div className={styles.cartList}>
        <PortalCartBody place={place} handleCartPortal={handleCartPortal} />
      </div>
    </div>
  );
};

export default PortalCart;
