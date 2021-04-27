import styles from "./mainPage.module.css";
import { useHistory } from "react-router-dom";
import { useState } from "react";
import Navbar from "../../components/common/navbar/navbar";
import Banner from "./banner/banner";
import SelectArea from "./selectArea/selectArea";
import SelectRoute from "./selectRoute/selectRoute";

const MainPage = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);

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
        <SelectRoute />
      </div>
    </div>
  );
};

export default MainPage;
