import React, { useEffect, useState } from "react";
import { getMyRoute } from "../../../../api/routeAPI";
import Loading from "../../../common/loading/loading";
import RouteListCard from "../../../routeList/routeListCard/routeListCard";
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
      <>
        <Loading />
      </>
    );
  }
  return (
    <div className={styles.MyRouteList}>
      {myRoute.length > 0 ? (
        myRoute.map((route, index) => (
          <RouteListCard key={index} routeName={route.name} />
        ))
      ) : (
        <div>경로를 만들어봐요</div>
      )}
    </div>
  );
};

export default MyRouteList;
