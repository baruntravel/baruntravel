import React, { useCallback, useEffect, useState } from "react";
import { getRouteDetail } from "../../api/routeAPI";
import { onAddCart, onDeleteCart, onReceiveCart } from "../../api/cartAPI";
import CartIcon from "../../components/common/cartIcon/cartIcon";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import RoutePlacesMap from "../../components/routeMakerPage/routePlacesMap/routePlacesMap";
import WishList from "../../components/routeMakerPage/wishList/wishList";
import styles from "./routeMakerPage.module.css";
import { userState, userWishList } from "../../recoil/userState";
import { useRecoilState, useRecoilValue } from "recoil";

const RouteMakerPage = (props) => {
  const userStates = useRecoilValue(userState);
  const [wishList, setWishList] = useRecoilState(userWishList);
  const [cartItems, setCartItems] = useState([]);

  const getCartList = useCallback(async () => {
    if (userStates.isLogin) {
      const items = await onReceiveCart();
      if (items) {
        setCartItems(items);
      }
    }
  }, [userStates.isLogin]);

  const onAddCartItem = useCallback(
    async (place) => {
      await onAddCart(place);
      getCartList();
    },
    [getCartList]
  );

  const onDeleteCartItem = useCallback(
    async (placeId) => {
      await onDeleteCart(placeId);
      getCartList();
    },
    [getCartList]
  );

  useEffect(() => {
    getCartList();
  }, [getCartList]);

  return (
    <div className={styles.RouteMakerPage}>
      <Header />
      <div className={styles.cartContainer}>
        <CartIcon items={cartItems} onUpdateItems={getCartList} />
      </div>
      <div className={styles.mapContainer}>
        <RoutePlacesMap places={cartItems} />
      </div>
      <div className={styles.wishListContainer}>
        <WishList wishList={wishList} onAddCart={onAddCartItem} onDeleteCart={onDeleteCartItem} cartItems={cartItems} />
      </div>
      <Navbar />
    </div>
  );
};

export default RouteMakerPage;
