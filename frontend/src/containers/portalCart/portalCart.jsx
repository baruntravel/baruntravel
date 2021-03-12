import React from "react";
import PortalCartBody from "../../components/portalCartBody/portalCartBody";
import { myRouteCart } from "../../recoil/routeAtom";
import styles from "./portalCart.module.css";

const PortalCart = ({ myRouteList }) => {
  return (
    <div className={styles.PortalCart}>
      <div className={styles.cartList}>
        <PortalCartBody myRouteList={myRouteCart} />
      </div>
    </div>
  );
};

export default PortalCart;
