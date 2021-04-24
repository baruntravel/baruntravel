import React, { useCallback, useRef, useState } from "react";
import styles from "./hotplacePage.module.css";
import HotplaceMap from "../../components/map/hotplaceMap/hotplaceMap";
import useInput from "../../hooks/useInput";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Drawer } from "antd";
import ShoppingCart from "../../components/common/shoppingCart/shoppingCart";
import DeleteConfirm from "../../components/common/deleteConfirm/deleteConfirm";
import CategoryBar from "../../components/map/hotplaceMap/categoryBar/categoryBar";
import PlaceCard from "../../components/placeCard/placeCard";
import "react-responsive-carousel/lib/styles/carousel.min.css";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useRecoilValue } from "recoil";
import { userState } from "../../recoil/userState";
import PortalAuth from "../../containers/portalAuth/portalAuth";

const HotplacePage = () => {
  const placeListRef = useRef();
  const searchRef = useRef();
  const inputRef = useRef();
  const sliderRef = useRef();

  const userStates = useRecoilValue(userState);
  const [cartVisible, setCartVisible] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState(null);
  const [confirmPortal, setConfirmPortal] = useState(false);
  const [place, setPlace] = useState({});
  const [inputKeyword, handleInputKeyword] = useInput();
  const [searchPlace, setSearchPlace] = useState("");
  const [searchPlaces, setSearchPlaces] = useState([]);
  const [markerIndex, setMarkerIndex] = useState();
  const [shoppingItems, setShoppingItems] = useState([]);
  const [checkLogin, setCheckLogin] = useState(false);

  const setCartVisibleTrue = useCallback(() => {
    if (userStates.isLogin) {
      setCartVisible(true);
    } else {
      setCheckLogin(true);
    }
  }, [userStates]);
  const setCartVisibleFalse = useCallback(() => {
    setCartVisible(false);
  }, []);
  const setConfirmPortalTrue = useCallback(() => {
    setConfirmPortal(true);
  }, []);
  const setConfirmPortalFalse = useCallback(() => {
    setConfirmPortal(false);
  }, []);
  const handleDeleteItem = useCallback((id) => {
    setShoppingItems((prev) => {
      const updated = prev.filter((item) => item.id !== id);
      return updated;
    });
  }, []);
  const updateClickedPlace = useCallback((place) => {
    setPlace(place);
  }, []);
  const updateSearchPlaces = useCallback((places) => {
    if (places.length > 0) {
      placeListRef.current.style.display = "initial";
    }
    setSearchPlaces(places);
  }, []);
  const handleSubmit = useCallback(
    (e) => {
      e.preventDefault();
      if (inputKeyword === searchPlace) {
        setSearchPlace(inputKeyword + " ");
      } else {
        setSearchPlace(inputKeyword);
      }
    },
    [inputKeyword, searchPlace]
  );
  const clickedMarker = useCallback((index) => {
    sliderRef.current.slickGoTo(index);
  }, []);
  const deleteClickedItemId = useCallback((id) => {
    setDeleteItemId(id);
  }, []);
  const addShoppingCart = useCallback(
    (place) => {
      if (userStates.isLogin) {
        setShoppingItems((prev) => {
          const updated = [...prev, place];
          return updated;
        });
      } else {
        setCheckLogin(true);
      }
    },
    [userStates]
  );
  const updateShoppingCart = useCallback((items) => {
    setShoppingItems(items);
  }, []);
  const updateItemMemo = useCallback((item) => {
    setShoppingItems((prev) => {
      // const updated =
    });
  }, []);
  const portalAuthClose = useCallback(() => {
    setCheckLogin(false);
  }, []);
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className={styles.HotplacePage}>
      <div className={styles.searchContainer}>
        <form ref={searchRef} className={styles.form} onSubmit={handleSubmit}>
          <input
            ref={inputRef}
            className={styles.inputBar}
            placeholder="Search place..."
            onChange={handleInputKeyword}
            value={inputKeyword || ""}
          />
        </form>
        <div className={styles.toggle}>
          <ShoppingCartOutlined
            className={styles.cartIcon}
            onClick={setCartVisibleTrue}
          />
        </div>
      </div>
      <div className={styles.mapContainer}>
        <HotplaceMap
          searchRef={searchRef}
          inputRef={inputRef}
          updateClickedPlace={updateClickedPlace}
          updateSearchPlaces={updateSearchPlaces}
          place={place}
          markerIndex={markerIndex}
          clickedMarker={clickedMarker}
        />
      </div>
      <div ref={placeListRef} className={styles.carouselContainer}>
        <Slider
          ref={sliderRef}
          {...settings}
          afterChange={(index) => {
            updateClickedPlace(searchPlaces[index]);
            setMarkerIndex(index);
          }}
        >
          {searchPlaces.map((place, index) => (
            <div key={index} className={styles.placeCardContainer}>
              <PlaceCard
                place={place}
                onHandleDelete={handleDeleteItem}
                addShoppingCart={addShoppingCart}
                isLiked={
                  shoppingItems.filter((item) => item.id === place.id).length
                }
              />
            </div>
          ))}
        </Slider>
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
      {!checkLogin && (
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
          zIndex={1004}
        >
          <ShoppingCart
            deleteClickedItemId={deleteClickedItemId}
            setConfirmPortalTrue={setConfirmPortalTrue}
            updateShoppingCart={updateShoppingCart}
            items={shoppingItems}
          />
        </Drawer>
      )}
      {confirmPortal && (
        <DeleteConfirm
          deleteItemId={deleteItemId}
          onDeleteItem={handleDeleteItem}
          onClose={setConfirmPortalFalse}
        />
      )}
      {checkLogin && <PortalAuth onClose={portalAuthClose} />}
    </div>
  );
};

export default HotplacePage;
