import React from "react";
import PlaceNameBox from "./placeNameBox/placeNameBox.jsx";
import styles from "./routeListCard.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";
import CartModal from "../../../pages/usersRoutePage/cartModal/cartModal.js";

const RouteListCard = ({
  item,
  handleMarkers,
  clickedCardName,
  updateCardName,
  usersRoutePage,
}) => {
  const handleClick = () => {
    handleMarkers(item.places);
    updateCardName(item.routeName);
  };
  return (
    <div className={styles.routeListCard} onClick={handleClick}>
      <div className={styles.routeNameBox}>
        <span className={styles.routeName}>{item.routeName}</span>
        {usersRoutePage?
        // usersRoutePage에서는 장바구니 표시 on
        <CartModal/>:null}
      </div>
      <div className={styles.routeList}>
      {clickedCardName === item.routeName &&
        item.places.map((v, index) => <PlaceNameBox key={index} item={v} usersRoutePage={usersRoutePage}/>)}
      </div>
    </div>
  );
};

export default RouteListCard;
