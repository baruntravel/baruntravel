import { useState, useEffect } from "react";
import styles from "./usersRoutePage.module.css";
import UsersRouteMap from "../../components/map/usersRouteMap/usersRouteMap";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/userState";
import { usersRouteItems } from "../../recoil/routeAtom";
import RouteCarousel from "./routeCarousel/routeCarousel";

// import { getRoute, postRoute, postEmpty } from "../../api/routeAPI";
const UsersRoutePage = () => {
  const [routeItems, setRouteItems] = useRecoilState(usersRouteItems);
  const [myState, setMyState] = useRecoilState(userState);
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    // setMarkers(routeItems);
  }, []);

  // const areaID = window.location.href.split("/")[3];
  // getRoute(areaID);
  // postRoute(areaID);
  // postEmpty();

  return (
    <div className={styles.wrapper}>
      <UsersRouteMap markers={markers} />
      <div className={styles.routeCarousel}>
        <RouteCarousel routeItems={routeItems} />
      </div>
    </div>
  );
};

export default UsersRoutePage;
