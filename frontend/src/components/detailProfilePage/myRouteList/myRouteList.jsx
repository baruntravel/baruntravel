import React, { useEffect, useState } from "react";
<<<<<<< HEAD:frontend/src/components/detailProfile/detailProfileCondition/myRouteList/myRouteList.jsx
import { getMyRoute } from "../../../../api/routeAPI";
import Loading from "../../../common/loading/loading";
import RouteListCard from "../../../routeList/routeListCard/routeListCard";
=======
import { getMyRoute } from "../../../api/routeAPI";
>>>>>>> feature/212:frontend/src/components/detailProfilePage/myRouteList/myRouteList.jsx
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
<<<<<<< HEAD:frontend/src/components/detailProfile/detailProfileCondition/myRouteList/myRouteList.jsx
      {myRoute.length > 0 ? (
        myRoute.map((route, index) => (
          <RouteListCard key={index} routeName={route.name} />
        ))
=======
      {/* {myRoute.length > 0 ? (
        myRoute.map((route) => <RouteListCard routeName={route.name} />)
>>>>>>> feature/212:frontend/src/components/detailProfilePage/myRouteList/myRouteList.jsx
      ) : (
        <div>경로를 만들어봐요</div>
      )} */}
    </div>
  );
};

export default MyRouteList;
