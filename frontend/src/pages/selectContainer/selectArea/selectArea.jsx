import styles from "./selectArea.module.css";
import { useState } from "react";
import { useHistory, Link } from "react-router-dom";
import areaList from "../../../assets/areaList.json";
import ShowAll from "./showAll/showAll";

const SelectArea = () => {
  const history = useHistory();
  const [isLoggedIn, setLoggedIn] = useState(true);
  const [area, setArea] = useState();
  const [showAll, setShowAll] = useState(false);

  //로그인 안 돼있을시 홈으로
  !isLoggedIn && history.push("/");

  const handleAreaClick = (e) => {
    setArea(e.target.id);
  };

  const showAllArea = () => {
    setShowAll(true);
  };

  return (
    <>
      {showAll ? (
        <ShowAll />
      ) : (
        <div className={styles.container}>
          <div className={styles.title}>인기 여행지</div>
          <ul className={styles.areaList}>
            <div className={styles.areaBox}>
              <li className={styles.area} onClick={showAllArea}>
                전체 보기
              </li>
            </div>
            {areaList.map((item, index) => {
              return (
                <div className={styles.areaBox}>
                  <li
                    className={styles.area}
                    id={item.eng}
                    key={index}
                    onClick={(e) => handleAreaClick(e)}
                  >
                    {item.kor}
                  </li>
                </div>
              );
            })}
            <div className={styles.areaBox}>
              <li className={styles.area} onClick={showAllArea}>
                전체 보기
              </li>
            </div>
          </ul>
        </div>
      )}
    </>
  );

  // return (
  //   <>
  //     <div className={styles.container}>
  //       {/* Navbar에 title props 전달 */}
  //       <Navbar title={"지역을 선택해주세요!"} />
  //       <div className={styles.body}>
  //         {/* 지역 클릭 전 */}
  //         {!area ? (
  //           <ul className={styles.areaList}>
  //             {areaList.map((item, index) => {
  //               return (
  //                 <li
  //                   className={styles.area}
  //                   id={item.eng}
  //                   key={index}
  //                   onClick={(e) => handleAreaClick(e)}
  //                 >
  //                   {item.kor}
  //                 </li>
  //               );
  //             })}
  //           </ul>
  //         ) : (
  //           // 지역 클릭 후
  //           <>
  //             <div className={styles.areaBox}>
  //               <h1 className={styles.areaTitle}>{area}</h1>
  //               <div className={styles.buttonBox}>
  //                 <button className={styles.hotplace}>
  //                   <Link to={`${area}/places`}>핫플레이스 보기</Link>
  //                 </button>
  //                 <button className={styles.route}>
  //                   <Link to={{ pathname: `${area}/routes` }}>
  //                     추천 루트 보기
  //                   </Link>
  //                 </button>
  //               </div>
  //             </div>
  //           </>
  //         )}
  //       </div>
  //     </div>
  //   </>
  // );
};

export default SelectArea;
