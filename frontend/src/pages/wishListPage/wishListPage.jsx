import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import { useState, useEffect, useCallback, useRef } from "react";
import { useHistory } from "react-router-dom";
import CardContainer from "../../components/wishListPage/cardContainer/cardContainer";
import PlaceContainer from "../../components/wishListPage/placeContainer/placeContainer";
import WishListPortal from "../../components/portal/wishListPortal/wishListPortal";

//TODO : 나의 찜목록 가져오는 API 연결
const WishListPage = () => {
  const { isLogin, name } = useRecoilValue(userState);
  const [folderToggle, setFolderToggle] = useState(false); // false : out(main), true : in
  const [portalOpened, setPortalOpened] = useState(false);
  const [wishlistName, setWishlistName] = useState();
  const [title, setTitle] = useState();

  const folderIn = () => setFolderToggle(true);
  const folderOut = () => setFolderToggle(false);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleWishlistName = (name) => setWishlistName(name);
  const places = useState([]);

  // useEffect(() => {}, [wishlistName]);

  return (
    <>
      <div className={styles.container}>
        {!folderToggle ? <Header title={"찜목록"} /> : <Header title={"찜목록detail"} onBackHandler={folderOut} />}

        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            {!folderToggle ? <CardContainer folderIn={folderIn} /> : <PlaceContainer />}
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
