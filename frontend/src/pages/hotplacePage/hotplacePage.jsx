import React, { useCallback, useRef, useState } from "react";
import styles from "./hotplacePage.module.css";
import HotplaceMap from "../../components/map/hotplaceMap/hotplaceMap";
import PortalCart from "../../containers/portalCart/portalCart";
import useInput from "../../hooks/useInput";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Drawer } from "antd";
import ShoppingCart from "../../components/common/shoppingCart/shoppingCart";
import DeleteConfirm from "../../components/common/deleteConfirm/deleteConfirm";

const HotplacePage = () => {
  const [cartVisible, setCartVisible] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState(null);
  const [confirmPortal, setConfirmPortal] = useState(false);
  const setCartVisibleTrue = () => {
    setCartVisible(true);
  };
  const setCartVisibleFalse = () => {
    setCartVisible(false);
  };
  const setConfirmPortalTrue = () => {
    setConfirmPortal(true);
  };
  const setConfirmPortalFalse = () => {
    setConfirmPortal(false);
  };
  const handleDeleteItem = (id) => {
    //
    console.log("삭제");
  };
  {
    // const [cartPortal, setCartPortal] = useState(false);
    // const [place, setPlace] = useState({});
    // const [inputKeyword, handleInputKeyword] = useInput();
    // const [searchPlace, setSearchPlace] = useState("");
    // const placeListRef = useRef();
    // const handleCartPortalClose = useCallback(() => {
    //   setCartPortal(false);
    // }, []);
    // const handleCartPortalOpen = useCallback(() => {
    //   setCartPortal(true);
    // }, []);
    // const clickedPlace = useCallback((place) => {
    //   setPlace(place);
    // }, []);
    // const handleSubmit = useCallback(
    //   (e) => {
    //     e.preventDefault();
    //     if (inputKeyword === searchPlace) {
    //       setSearchPlace(inputKeyword + " ");
    //     } else {
    //       setSearchPlace(inputKeyword);
    //     }
    //   },
    //   [inputKeyword, searchPlace]
    // );
  }
  return (
    <div className={styles.HotplacePage}>
      <button onClick={setConfirmPortalTrue}>test</button>
      <button className={styles.cartOpenBtn} onClick={setCartVisibleTrue}>
        <ShoppingCartOutlined />
      </button>
      <Drawer
        title={`${"장소"}의 담은 목록`}
        placement="right"
        closable={true}
        onClose={setCartVisibleFalse}
        visible={cartVisible}
        width={window.innerWidth > 768 ? "36vw" : "80vw"}
        bodyStyle={{
          backgroundColor: "#ebecec",
          padding: 0,
        }}
      >
        <ShoppingCart setConfirmPortalTrue={setConfirmPortalTrue} />
      </Drawer>
      {confirmPortal && (
        <DeleteConfirm
          onDeleteItem={handleDeleteItem}
          onClose={setConfirmPortalFalse}
        />
      )}
      {/* <header className={styles.navbar}>navbar</header>
      <div className={styles.body}>
        <div className={styles.searchContainer}>
          <div className={styles.search}>
            <form className={styles.form} onSubmit={handleSubmit}>
              <input
                placeholder="Search place..."
                onChange={handleInputKeyword}
                value={inputKeyword || ""}
              />
              <button type="submit">검색</button>
            </form>
          </div>
          <div className={styles.searchList}>
            <ul ref={placeListRef} className={styles.placeList}></ul>
          </div>
        </div>
        <div className={styles.mapContainer}>
          <HotplaceMap
            placeListRef={placeListRef}
            handleCartPortalOpen={handleCartPortalOpen}
            clickedPlace={clickedPlace}
            searchPlace={searchPlace}
          />
        </div>
      </div>
      {cartPortal && (
        <PortalCart
          place={place}
          handleCartPortalClose={handleCartPortalClose}
        />
      )} */}
    </div>
  );
};

export default HotplacePage;
