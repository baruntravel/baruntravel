import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import WishListContainer from "../../components/wishListPage/wishListContainer/wishListContainer";
import PlaceContainer from "../../components/wishListPage/placeContainer/placeContainer";
import WishListPortalInput from "../../components/portal/wishListInputPortal/wishListPortalInput";

//TODO : 나의 찜목록 가져오는 API 연결
const WishListPage = () => {
  const { isLogin, name } = useRecoilValue(userState);
  const [folderToggle, setFolderToggle] = useState(false); // false : out(main), true : in
  const [portalOpened, setPortalOpened] = useState(false);
  const [wishlistName, setWishlistName] = useState();
  const [title, setTitle] = useState();

  const folderIn = (title) => {
    setTitle(title);
    setFolderToggle(true);
  };
  const folderOut = () => setFolderToggle(false);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleWishlistName = (name) => setWishlistName(name);
  const places = useState([]);

  // useEffect(() => {}, [wishlistName]);

  return (
    <>
      <div className={styles.container}>
        {!folderToggle ? (
          <Header title={"찜 목록"} />
        ) : (
          <Header title={title} onBackHandler={folderOut} />
        )}

        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            {!folderToggle ? (
              <WishListContainer folderIn={folderIn} />
            ) : (
              <PlaceContainer />
            )}
            <h1>{wishlistName}</h1>
          </div>
        )}

        {portalOpened && (
          <WishListPortalInput
            onClose={handlePortalOpen}
            handleWishlistName={handleWishlistName}
          />
        )}
        <Navbar />
      </div>
    </>
  );
};

export default WishListPage;
