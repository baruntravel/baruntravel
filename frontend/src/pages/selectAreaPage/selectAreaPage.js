import styles from "./selectAreaPage.module.css";
import { useState } from "react";
import { useHistory, Link } from "react-router-dom";
import areaList from "../../assets/areaList.json";
import Logo from "../../components/logo/logo";
import Navbar from "../../components/navbar/navbar";

const SelectAreaPage = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);
  const [area, setArea] = useState();

  //로그인 안 돼있을시 홈으로
  !isLoggedIn && history.push("/");

  const handleAreaClick = (e) => {
    setArea(e.target.id);
  };

  return (
    <>
      <div className={styles.container}>
        {/* Navbar에 title props 전달 */}
        <Navbar title={"지역을 선택해주세요!"} />
        <div className={styles.body}>
          {/* 지역 클릭 전 */}
          {!area ? (
            <ul className={styles.areaList}>
              {areaList.map((item) => {
                return (
                  <li
                    className={styles.area}
                    id={item.eng}
                    onClick={(e) => handleAreaClick(e)}
                  >
                    {item.kor}
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
                    <Link to={{ pathname: `${area}/routes` }}>
                      추천 루트 보기
                    </Link>
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

export default SelectAreaPage;
