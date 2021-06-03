import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import { useState, useEffect, useCallback } from "react";
import CardContainer from "../../components/wishListPage/cardContainer/cardContainer";
import PlaceContainer from "../../components/wishListPage/placeContainer/placeContainer";
import WishListPortal from "../../components/portal/wishListPortal/wishListPortal";

//TODO : 나의 찜목록 가져오는 API 연결
const WishListPage = () => {
  const { isLogin, name } = useRecoilValue(userState);
  const [folderOpened, setOpened] = useState(false);
  const [portalOpened, setPortalOpened] = useState(false);
  const [wishlistName, setWishlistName] = useState();

  const handleFolderOpen = () => setOpened(!folderOpened);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleWishlistName = (name) => setWishlistName(name);
  const places = useState([]);

  useEffect(() => {}, [wishlistName]);

  return (
    <>
      <div className={styles.container}>
        <Header />
        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            {!folderOpened && (
              <div className={styles.col1}>
                <h1 className={styles.title}>찜 목록</h1>
                <button onClick={handlePortalOpen} className={styles.addButton}>
                  새로운 찜 목록 만들기
                </button>
              </div>
            )}

            {
              // 카드 누르기 전, 찜목록 메인 화면
              !folderOpened ? (
                <CardContainer handleOpen={handleFolderOpen} />
              ) : (
                // 카드 누르면, 장소들 펼쳐짐
                <PlaceContainer handleOpen={handleFolderOpen} places={places} />
              )
            }
            <h1>{wishlistName}</h1>
            {/* 찜목록추가 테스트 */}
          </div>
        )}

        {portalOpened && <WishListPortal onClose={handlePortalOpen} handleWishlistName={handleWishlistName} />}
        <Navbar />
      </div>
    </>
  );
};

export default WishListPage;
