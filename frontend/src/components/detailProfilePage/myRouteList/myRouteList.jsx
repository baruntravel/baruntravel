import { Spin } from "antd";
import React, { useEffect, useState } from "react";
import { getMyRoute } from "../../../api/routeAPI";
import styles from "./myRouteList.module.css";

const MyRouteList = (props) => {
  const [loading, setLoading] = useState(true);
  const [myRoute, setMyRoute] = useState([]);
  useEffect(() => {
    async function getRoute() {
      const result = await getMyRoute();
      if (result) {
        setMyRoute(result);
      }
    }
    getRoute();
    setLoading(false);
  }, []);
  if (loading) {
    return (
      <div className={styles.loading}>
        <Spin size="large" />
      </div>
    );
  }
  return (
    <div className={styles.MyRouteList}>
      {/* {myRoute.length > 0 ? (
        myRoute.map((route) => <RouteListCard routeName={route.name} />)
      ) : (
        <div>경로를 만들어봐요</div>
      )} */}
    </div>
  );
};

export default MyRouteList;
