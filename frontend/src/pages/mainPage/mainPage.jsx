import styles from "./mainPage.module.css";
import { useState, useEffect } from "react";
import Navbar from "../../components/common/navbar/navbar";
import Banner from "../../components/mainPage/banner/banner";
import SelectRoute from "../../components/mainPage/selectRoute/selectRoute";
import SelectArea from "../../components/mainPage/selectArea/selectArea";

import { getFeaturedRoutes as fetchRoutes } from "../../api/routeAPI";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
const MainPage = () => {
  const userStates = useRecoilValue(userState);
  const [mainRoutes, setMainRoutes] = useState([]);
  //TODO : mainRoute(object) state에 places[] 넣기!
  //그리고 나서, SelectRoute로 routes props전달
  // const userStates = useRecoilValue(userState);
  //TODO : route 추천순으로 가져오기

  useEffect(() => {
    getRoute(1);
    getRoute(2);
    getRoute(3);
  }, []);

  async function getRoute(id) {
    let route = await fetchRoutes(id);
    setMainRoutes((mainRoutes) => [...mainRoutes, route]);
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <Navbar />
      </div>
      <div className={styles.body}>
        <h1>
          여행을 바르게,
          <br />
          ✈️ 바른여행길잡이
        </h1>

        <Banner />
        <SelectArea />
        <SelectRoute routes={mainRoutes} />
      </div>
    </div>
  );
};

export default MainPage;
