import styles from "./selectAreaPage.module.css";
import { useState } from "react";
import { useHistory, Link } from "react-router-dom";
import areaList from "../../assets/areaList.json";
import Logo from "../../components/logo/logo";

const Selectarea = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);
  const [area, setArea] = useState();

  //로그인 안돼있을시 홈으로
  !isLoggedIn && history.push("/");

  //
  const handleAreaClick = (e) => {
    setArea(e.target.id);
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.navbar}>
          <Logo />
          <h1 className={styles.title}>지역을 선택해주세요!</h1>
          <div className={styles.profileWrap}></div>
        </div>
        <div className={styles.body}>
          {/* 지역 클릭 전 */}
          {!area ? (
            <ul className={styles.areaList}>
              {areaList.map((item) => {
                return (
                  <li
                    className={styles.area}
                    id={item}
                    onClick={(e) => handleAreaClick(e)}
                  >
                    {item}
                  </li>
                );
              })}
            </ul>
          ) : (
            // 지역 클릭 후
            <>
              <div className={styles.areaBox}>
                <h1 className={styles.areaTitle}>{area}</h1>
                <div className={styles.buttonBox}>
                  <button className={styles.hotplace}>
                    <Link to={`${area}/places`}>핫플레이스 보기</Link>
                  </button>
                  <button className={styles.route}>
                    <Link to={`${area}/routes`}>추천 루트 보기</Link>
                  </button>
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default Selectarea;
