import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue, useRecoilState } from "recoil";

const WishlistPage = () => {
  const [userStates, setUserStates] = useRecoilState(userState);
  return (
    <>
      <div className={styles.container}>
        <Header />
        {userStates.isLogin === false ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            <h1 className={styles.title}>찜 목록</h1>
            {}
            <div className={styles.wishlistBox}></div>
          </div>
        )}
        <Navbar />
      </div>
    </>
  );
};

export default WishlistPage;
