import { useState } from "react";
import styles from "./usersRoutePage.module.css";
import UsersRouteMap from "../../components/map/usersRouteMap/usersRouteMap";
import { useRecoilState } from "recoil";
import { usersRouteItems } from "../../recoil/routeAtom";
import RouteList from "../../components/routeList/routeList";
import Navbar from "../../components/common/navbar/navbar";

const UsersRoutePage = () => {
  //Todo: 한글로 변경해야함
  const area = window.location.href.split("/")[3];

  const [clickedCardName, setClickedCardName] = useState("");
  const [routeItems, setRouteItems] = useRecoilState(usersRouteItems);
  const [markers, setMarkers] = useState([]);

  const handleMarkers = (places) => setMarkers(places);
  const updateCardName = (name) => setClickedCardName(name);

  return (
    <div className={styles.wrapper}>
      <Navbar title={`${area} 추천 루트`} />
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
