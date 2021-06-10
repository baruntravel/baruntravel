import React, { useCallback, useEffect, useState } from "react";
import { getRouteDetail } from "../../api/routeAPI";
import { onAddCart, onReceiveCart } from "../../api/cartAPI";
import CartIcon from "../../components/common/cartIcon/cartIcon";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import ImageMap from "../../components/routeDetailPage/imageMap/imageMap";
import WishList from "../../components/routeMakerPage/wishList/wishList";
import styles from "./routeMakerPage.module.css";

const RouteMakerPage = (props) => {
  const [wishList, setWishList] = useState([]);
  const [routeDetail, setRouteDetail] = useState({});
  const [cartItems, setCartItems] = useState([]);

  const getCartList = useCallback(async () => {
    const items = await onReceiveCart();
    setCartItems(items);
  }, []);

  const onAddCartItem = useCallback(
    async (place) => {
      await onAddCart(place);
      getCartList();
    },
    [getCartList]
  );

  useEffect(() => {
    async function getRouteDetailInfo() {
      // 루트 상세페이지의 정보를 받아옴
      const route = await getRouteDetail(1);
      setRouteDetail(route);
      setWishList([route]);
    }

    // async function getCartList() {
    //   const items = await onReceiveCart();
    //   setCartItems(items);
    // }
    getCartList();
    getRouteDetailInfo();
  }, [getCartList]);

  return (
    <div className={styles.RouteMakerPage}>
      <Header />
      <div className={styles.cartContainer}>
        <CartIcon items={cartItems} onUpdateItems={getCartList} />
      </div>
      <div className={styles.mapContainer}>
        <ImageMap
          // places={cartItems}
          centerX={routeDetail.centerX}
          centerY={routeDetail.centerY}
        />
      </div>
      <div className={styles.wishListContainer}>
        <WishList wishList={wishList} onAddCart={onAddCartItem} />
      </div>
      <Navbar />
    </div>
  );
};

export default RouteMakerPage;
