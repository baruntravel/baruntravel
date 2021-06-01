import React from "react";
import PortalCartBody from "../../components/portalCartBody/portalCartBody";
import styles from "./portalCart.module.css";

const PortalCart = ({ place, handleCartPortalClose }) => {
  return (
    <div className={styles.PortalCart}>
      <div className={styles.cartList}>
        <PortalCartBody
          place={place}
          handleCartPortalClose={handleCartPortalClose}
        />
      </div>
    </div>
  );
};

export default PortalCart;
