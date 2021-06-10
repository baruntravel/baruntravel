import React, { useEffect, useState } from "react";
import { getRouteDetail } from "../../api/routeAPI";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import RoutePlacesMap from "../../components/routeMakerPage/routePlacesMap/routePlacesMap";
import WishList from "../../components/routeMakerPage/wishList/wishList";
import styles from "./routeMakerPage.module.css";

const RouteMakerPage = (props) => {
  const [routeDetail, setRouteDetail] = useState({});

  useEffect(() => {
    async function getRouteDetailInfo() {
      // 루트 상세페이지의 정보를 받아옴
      const route = await getRouteDetail(1);
      setRouteDetail(route);
    }
    getRouteDetailInfo();
  }, []);
  return (
    <div className={styles.RouteMakerPage}>
      <Header />
      <div className={styles.mapContainer}>
        <RoutePlacesMap places={routeDetail.places} centerX={routeDetail.centerX} centerY={routeDetail.centerY} />
      </div>
      <div className={styles.wishListContainer}>
        <WishList />
      </div>
      <Navbar />
    </div>
  );
};

export default RouteMakerPage;
