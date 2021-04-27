import styles from "./mainPage.module.css";
import SelectArea from "./selectArea/selectArea";
import SelectRoute from "./selectRoute/selectRoute";
import { useHistory } from "react-router-dom";
import { useState } from "react";

const MainPage = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);

  //로그인 안 돼있을시 홈으로
  !isLoggedIn && history.push("/");

  return (
    <div className={styles.container}>
      <SelectArea />
      <SelectRoute />
    </div>
  );
};

export default MainPage;
