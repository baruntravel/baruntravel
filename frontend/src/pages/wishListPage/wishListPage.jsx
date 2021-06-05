import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import { useState, useEffect, useCallback, useRef } from "react";
import CardContainer from "../../components/wishListPage/cardContainer/cardContainer";
import PlaceContainer from "../../components/wishListPage/placeContainer/placeContainer";
import WishListPortal from "../../components/portal/wishListPortal/wishListPortal";

//TODO : 나의 찜목록 가져오는 API 연결
const WishListPage = () => {
  const { isLogin, name } = useRecoilValue(userState);
  const [folderToggle, setFolderToggle] = useState(false); // false : out(main), true : in
  const [portalOpened, setPortalOpened] = useState(false);
  const [wishlistName, setWishlistName] = useState();

  const folderIn = () => setFolderToggle(true);
  const folderOut = () => setFolderToggle(false);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleWishlistName = (name) => setWishlistName(name);
  const places = useState([]);

  // useEffect(() => {}, [wishlistName]);

  return (
    <>
      <div className={styles.container}>
        <Header />
        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.row1}>
            <div className={styles.row1_row1}>
              <h1 className={styles.title} onClick={folderOut}>
                찜 목록
              </h1>
              {!folderToggle && (
                <button onClick={handlePortalOpen} className={styles.addButton}>
                  새로운 찜 목록 만들기
                </button>
              )}
            </div>
            <div className={styles.row2}>
              {
                // 카드 누르기 전, 찜목록 메인 화면
                !folderToggle ? (
                  <CardContainer folderIn={folderIn} />
                ) : (
                  // 카드 누르면, 장소들 펼쳐짐
                  <PlaceContainer folderOut={folderOut} places={places} />
                )
              }
            </div>
            <h1>{wishlistName}</h1>
          </div>
        )}

        {portalOpened && <WishListPortal onClose={handlePortalOpen} handleWishlistName={handleWishlistName} />}
        <Navbar />
      </div>
    </>
  );
};

export default WishListPage;
