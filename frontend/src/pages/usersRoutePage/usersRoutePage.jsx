import { useState, useEffect } from "react";
import styles from "./usersRoutePage.module.css";
import UsersRouteMap from "../../components/map/usersRouteMap/usersRouteMap";
import RouteList from "../../components/routeList/routeList";
import Navbar from "../../components/common/navbar/navbar";

import { useRecoilState } from "recoil";
import { userState } from "../../recoil/userState";
import { usersRouteItems } from "../../recoil/routeAtom";

import {
  getRoute,
  postRoute,
  postEmpty,
  getAuthorized,
} from "../../api/routeAPI";

const UsersRoutePage = () => {
  //Todo: 한글로 변경해야함

  useEffect(() => {
    const accessToken = getAuthorized();
    console.log(myState);
  }, []);

  const areaID = window.location.href.split("/")[3];

  const [clickedCardName, setClickedCardName] = useState("");
  const [routeItems, setRouteItems] = useRecoilState(usersRouteItems);
  const [myState, setMyState] = useRecoilState(userState);

  const [markers, setMarkers] = useState([]);

  const handleMarkers = (places) => setMarkers(places);
  const updateCardName = (name) => setClickedCardName(name);

  getRoute(areaID);
  // postRoute(areaID);
  // postEmpty();

  return (
    <div className={styles.wrapper}>
      <Navbar title={`${areaID} 추천 루트`} />
      <h1>{myState.name}님 안녕하세요 !</h1>

      <div className={styles.mainContainer}>
        <RouteList
          handleMarkers={handleMarkers}
          clickedCardName={clickedCardName}
          updateCardName={updateCardName}
          routeItems={routeItems}
          //usersRoutePage에서 쓰이는 경우 true
          usersRoutePage={true}
        />
        <div className={styles.mapContainer}>
          <UsersRouteMap markers={markers} />
        </div>
      </div>
    </div>
  );
};

export default UsersRoutePage;
