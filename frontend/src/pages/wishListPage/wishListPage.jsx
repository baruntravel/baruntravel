import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import WishListContainer from "../../components/wishListPage/wishListContainer/wishListContainer";
import PlaceContainer from "../../components/wishListPage/placeContainer/placeContainer";
import WishListPortalInput from "../../components/portal/wishListInputPortal/wishListPortalInput";
import { onReceiveWishList, onReceiveWishListPlaces, onAddNewMyWish } from "../../api/wishListAPI";

//TO-DO : 찜목록 제거하기

const WishListPage = () => {
  const { isLogin, name } = useRecoilValue(userState);
  const [folderToggle, setFolderToggle] = useState(false); // false : out(main), true : in
  const [portalToggle, setPortalToggle] = useState(false); // false : closed, true : open
  const [wishlistArray, setWishlistArray] = useState([]);
  const [places, setPlaces] = useState([]);
  const [title, setTitle] = useState();

  const folderIn = (title) => {
    setTitle(title);
    setPlaces(places);
    setFolderToggle(true);
  };
  const folderOut = () => setFolderToggle(false);
  const handlePortalOpen = () => setPortalToggle(!portalToggle);

  useEffect(() => {
    getMyWishList();
  }, [wishlistArray]);

  async function getMyWishList() {
    const wishlist = await onReceiveWishList();
    wishlist && setWishlistArray(wishlist);
  }

  async function getWishlistPlaces(id) {
    const places = await onReceiveWishListPlaces(id);
    console.log(places);
  }

  async function addNewWishList(wishlist) {
    setWishlistArray((wishlistArray) => [...wishlistArray, wishlist]);
    await onAddNewMyWish(wishlist);
  }

  return (
    <>
      <div className={styles.container}>
        {!folderToggle ? <Header title={"찜 목록"} /> : <Header title={title} onBackHandler={folderOut} />}

        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            {!folderToggle ? (
              <WishListContainer folderIn={folderIn} wishlistArray={wishlistArray} addNewWishList={addNewWishList} />
            ) : (
              <PlaceContainer places={places} />
            )}
          </div>
        )}
        <Navbar />
      </div>
    </>
  );
};

export default WishListPage;
