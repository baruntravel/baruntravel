import React from "react";
import styles from "./wishListPage.module.css";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import { useState, useEffect } from "react";
import WishListContainer from "../../components/wishListPage/wishListContainer/wishListContainer";
import PlaceContainer from "../../components/wishListPage/placeContainer/placeContainer";
import { onReceiveWishList, onReceiveWishListPlaces, onAddNewMyWish, onDeleteWishList } from "../../api/wishListAPI";
import { useRecoilState, useRecoilValue } from "recoil";
import { userState, userWishList } from "../../recoil/userState";

const WishListPage = () => {
  // const [userStates, setUserStates] = useRecoilState(userState);
  const { isLogin, name } = useRecoilValue(userState);
  const [userWishListItems, setUserWishListItems] = useRecoilState(userWishList);
  const [folderToggle, setFolderToggle] = useState(false); // false : out(main), true : in
  const [wishlistArray, setWishlistArray] = useState([]);
  const [places, setPlaces] = useState([]);
  const [title, setTitle] = useState();
  const folderIn = (id) => {
    const wishlist = wishlistArray.filter((i) => Number(id) === i.id)[0];
    setTitle(wishlist.name);
    getWishlistPlaces(id);
    setFolderToggle(true);
  };
  const folderOut = () => setFolderToggle(false);

  useEffect(() => {
    loadMyWishList();
  }, []);

  async function loadMyWishList() {
    const wishlist = await onReceiveWishList();
    setWishlistArray(wishlist);
    setUserWishListItems(wishlist);
  }

  async function getWishlistPlaces(id) {
    const { places } = await onReceiveWishListPlaces(id);
    setPlaces(places);
  }

  async function addNewWishList(name) {
    await onAddNewMyWish(name);
    loadMyWishList();
  }

  async function deleteWishList(id) {
    await onDeleteWishList(id);
    loadMyWishList();
  }

  return (
    <>
      <div className={styles.container}>
        {!folderToggle ? <Header title={`${name}님의 찜 목록`} /> : <Header title={title} onBackHandler={folderOut} />}

        {isLogin === undefined ? (
          <h1>로그인을 해주세요</h1>
        ) : (
          <div className={styles.body}>
            {!folderToggle ? (
              <WishListContainer
                folderIn={folderIn}
                wishlistArray={wishlistArray}
                addNewWishList={addNewWishList}
                deleteWishList={deleteWishList}
              />
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
