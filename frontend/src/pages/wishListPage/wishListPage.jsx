import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
// import img1 from "./images/img1.jpg";
// import img2 from "./images/img2.jpg";
// import img3 from "./images/img3.jpg";

const WishListPage = () => {
  const { isLogin, name } = useRecoilValue(userState);

  const DummyWishList = async () => {
    return <h1>TEST</h1>;
  };

  //TODO : 나의 찜목록 가져오는 API 연결
  // const WishListBox = () => {

  //   return <h1></h1>;
  // };

  return (
    <>
      <div className={styles.container}>
        <Header />
        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            <div className={styles.col1}>
              <h1 className={styles.title}>{name}님의 찜 목록</h1>
              <button className={styles.addButton}>새로운 찜 목록 만들기</button>
            </div>

            <div className={styles.cardsContainer}>
              <div className={styles.cardBox}>
                <div className={styles.imgBox}>
                  {/* <img src={img1}></img> */}
                  {/* <img src={img2}></img> */}
                  {/* <img src={img3}></img> */}
                </div>
                <div className={styles.wishListTitle}>제주도 갈만한 곳들</div>
              </div>

              <div className={styles.cardBox}>
                <div className={styles.imgBox}></div>
                <div className={styles.wishListTitle}>서울 핫플레이스</div>
              </div>
            </div>
          </div>
        )}
        <Navbar />
      </div>
    </>
  );
};

export default WishListPage;
