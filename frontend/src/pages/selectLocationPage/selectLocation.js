import styles from "./selectLocation.module.css";
import { useState } from "react";
import { useHistory, Link } from "react-router-dom";
import districtList from "../../assets/districtList.json";
import Logo from "../../components/logo/logo";

const SelectLocation = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);
  const [location, setLocation] = useState();

  //로그인 안돼있을시 홈으로
  !isLoggedIn && history.push("/");

  //
  const handleLocationClick = (e) => {
    setLocation(e.target.id);
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
          {!location ? (
            <ul className={styles.districtList}>
              {districtList.map((item) => {
                return (
                  <li
                    className={styles.district}
                    id={item}
                    onClick={(e) => handleLocationClick(e)}
                  >
                    {item}
                  </li>
                );
              })}
            </ul>
          ) : (
            // 지역 클릭 후
            <>
              <div className={styles.locationBox}>
                <h1 className={styles.locationTitle}>{location}</h1>
                <div className={styles.buttonBox}>
                  <button className={styles.hotplace}>
                    <Link to={`${location}/places`}>핫플레이스 보기</Link>
                  </button>
                  <button className={styles.route}>
                    <Link to={`${location}/routes`}>추천 루트 보기</Link>
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

export default SelectLocation;
