import styles from "./mainPage.module.css";
import { useState, useEffect } from "react";
import Header from "../../components/common/header/header";
import Banner from "../../components/mainPage/banner/banner";
import SelectRoute from "../../components/mainPage/selectRoute/selectRoute";
import SelectArea from "../../components/mainPage/selectArea/selectArea";

import { getFeaturedRoutes as fetchRoutes } from "../../api/routeAPI";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import Navbar from "../../components/common/navbar/navbar";
const MainPage = () => {
  const userStates = useRecoilValue(userState);
  const [mainRoutes, setMainRoutes] = useState([]);
  //TODO : mainRoute(object) state에 places[] 넣기!
  //그리고 나서, SelectRoute로 routes props전달
  // const userStates = useRecoilValue(userState);
  //TODO : route 추천순으로 가져오기

  useEffect(() => {
    // getRoute(1);
  }, []);

  async function getRoute(id) {
    let route = await fetchRoutes(id);
    setMainRoutes((mainRoutes) => [...mainRoutes, route]);
  }

  return (
    <div className={styles.container}>
      <Header />
      <div className={styles.body}>
        <Banner />
        <SelectArea />
        <SelectRoute routes={mainRoutes} />
      </div>
      <Navbar />
    </div>
  );
};

export default MainPage;
